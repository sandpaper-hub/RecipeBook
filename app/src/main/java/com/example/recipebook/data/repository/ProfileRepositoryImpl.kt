package com.example.recipebook.data.repository

import com.example.recipebook.data.util.ImageCompressorImpl
import com.example.recipebook.domain.repository.ProfileRepository
import com.example.recipebook.domain.model.profile.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
    private val imageCompressorImpl: ImageCompressorImpl
) : ProfileRepository {

    override fun observeUserProfile(): Flow<UserProfile> = callbackFlow {
        val uid = getCurrentUserUidOrNull()
        if (uid == null) {
            close(Exception("User isn't authenticated"))
            return@callbackFlow
        }

        val registration = firestore
            .collection("users")
            .document(uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val user = snapshot?.toObject(UserProfile::class.java)
                if (user != null) {
                    trySend(user).isSuccess
                }
            }
        awaitClose {
            registration.remove()
        }
    }

    override fun currentUserUidFlow(): Flow<String?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser?.uid)
        }

        firebaseAuth.addAuthStateListener(listener)

        trySend(firebaseAuth.currentUser?.uid)

        awaitClose {
            firebaseAuth.removeAuthStateListener(listener)
        }
    }

    override suspend fun uploadUserAvatar(imageSource: String): String {
        val imageBytes = imageCompressorImpl.compress(imageSource)
        val ref = firebaseStorage.reference
            .child("users_avatar/${getCurrentUserUidOrNull()}/avatar.jpg")

        ref.putBytes(imageBytes).await()
        return ref.downloadUrl.await().toString()
    }

    override suspend fun updateUserData(data: Map<String, Any?>): Result<Unit> {
        val uid = getCurrentUserUidOrNull()
            ?: return Result.failure(Exception("User isn't authenticated"))
        return suspendCancellableCoroutine { continuation ->
            firestore.collection("users")
                .document(uid)
                .update(data)
                .addOnSuccessListener {
                    if (continuation.isActive) {
                        continuation.resume(Result.success(Unit))
                    }
                }
                .addOnFailureListener { exception ->
                    if (continuation.isActive) {
                        continuation.resume(Result.failure(exception))
                    }
                }
        }
    }

    private fun getCurrentUserUidOrNull(): String? =
        firebaseAuth.currentUser?.uid
}
