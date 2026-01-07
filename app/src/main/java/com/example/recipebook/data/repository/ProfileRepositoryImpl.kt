package com.example.recipebook.data.repository

import com.example.recipebook.data.dto.RecipeDto
import com.example.recipebook.data.mapper.toDomain
import com.example.recipebook.domain.repository.ProfileRepository
import com.example.recipebook.domain.model.profile.UserProfile
import com.example.recipebook.domain.model.recipe.Recipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : ProfileRepository {

    private val usersCollection = firestore.collection("users")

    override fun observeUserProfile(): Flow<UserProfile> = callbackFlow {
        val uid = getCurrentUserUidOrNull()
        if (uid == null) {
            close(Exception("User isn't authenticated"))
            return@callbackFlow
        }

        val registration = usersCollection
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

    override fun observeUserRecipes(userId: String): Flow<List<Recipe>> = callbackFlow {
        val query = firestore.collection("recipes")
            .whereEqualTo("authorId", userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val recipes = snapshot
                ?.toObjects(RecipeDto::class.java)
                ?.map { it.toDomain() }
                ?: emptyList()
            trySend(recipes)
        }

        awaitClose {
            listener.remove()
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

    override suspend fun uploadUserAvatar(bytes: ByteArray): Result<String> {
        val uid = getCurrentUserUidOrNull()
            ?: return Result.failure(Exception("User isn't authenticated"))

        return suspendCancellableCoroutine { continuation ->
            val ref = firebaseStorage.reference.child("users_avatar/$uid/avatar.jpg")
            val uploadTask = ref.putBytes(bytes)

            uploadTask
                .addOnSuccessListener {
                    ref.downloadUrl
                        .addOnSuccessListener { uri ->
                            if (continuation.isActive) {
                                continuation.resume(Result.success(uri.toString()))
                            }
                        }
                        .addOnFailureListener { exception ->
                            if (continuation.isActive) {
                                continuation.resume(Result.failure(exception))
                            }
                        }
                }
                .addOnFailureListener { exception ->
                    if (continuation.isActive) {
                        continuation.resume(Result.failure(exception))
                    }
                }

            continuation.invokeOnCancellation {
                uploadTask.cancel()
            }
        }
    }

    override suspend fun updateUserData(data: Map<String, Any?>): Result<Unit> {
        val uid = getCurrentUserUidOrNull()
            ?: return Result.failure(Exception("User isn't authenticated"))
        return suspendCancellableCoroutine { continuation ->
            usersCollection
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
