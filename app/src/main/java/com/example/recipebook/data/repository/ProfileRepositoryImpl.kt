package com.example.recipebook.data.repository

import android.util.Log
import com.example.recipebook.domain.repository.ProfileRepository
import com.example.recipebook.domain.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : ProfileRepository {

    private val usersCollection = firestore.collection("users")

    override suspend fun getUserProfile(): Result<UserProfile> {
        val uid = getCurrentUserUidOrNull()
            ?: return Result.failure(Exception("User isn't authenticated"))
        return suspendCancellableCoroutine { continuation ->
            usersCollection.document(uid)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (!continuation.isActive) return@addOnSuccessListener
                    val user = snapshot.toObject(UserProfile::class.java)
                    if (user != null) {
                        Log.d("UI_STATE_PROFILE", user.nickName)
                        continuation.resume(Result.success(user))
                    } else {
                        continuation.resume(Result.failure(Exception("User not found")))
                    }
                }
                .addOnFailureListener { error ->
                    if (continuation.isActive) continuation.resume(Result.failure(error))
                }
        }
    }

    override suspend fun updateUserData(data: Map<String, String?>, bytes: ByteArray): Result<Unit> {
        val uid = getCurrentUserUidOrNull()
            ?: return Result.failure(Exception("User isn't authenticated"))
        return suspendCancellableCoroutine { continuation ->

            val ref = firebaseStorage.reference.child("users_avatar/$uid/avatar.jpg")
            val uploadTask = ref.putBytes(bytes)
            uploadTask
                .addOnSuccessListener {
                    ref.downloadUrl
                        .addOnSuccessListener { uri ->
                            val downloadUrl = uri.toString()

                            val finalData: Map<String, Any?> = data.toMutableMap().apply {
                                put("photoUrl", downloadUrl)
                            }

                            usersCollection
                                .document(uid)
                                .update(finalData)
                                .addOnSuccessListener {
                                    if (continuation.isActive) {
                                        continuation.resume(Result.success(Unit))
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    if (continuation.isActive) {
                                        continuation.resumeWithException(exception)
                                    }
                                }
                        }
                        .addOnFailureListener { exception ->
                            if (continuation.isActive) {
                                continuation.resumeWithException(exception)
                            }
                        }
                }
                .addOnFailureListener { exception ->
                    if (continuation.isActive) {
                        continuation.resumeWithException(exception)
                    }
                }

            continuation.invokeOnCancellation {
                uploadTask.cancel()
            }
        }
    }

    private fun getCurrentUserUidOrNull(): String? =
        firebaseAuth.currentUser?.uid
}
