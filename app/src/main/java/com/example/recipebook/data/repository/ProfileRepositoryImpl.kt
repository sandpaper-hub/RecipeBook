package com.example.recipebook.data.repository

import com.example.recipebook.domain.repository.ProfileRepository
import com.example.recipebook.domain.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    firestore: FirebaseFirestore
) : ProfileRepository {

    private val usersCollection = firestore.collection("users")

    override suspend fun createUserDocumentIfNeeded(userProfile: UserProfile): Result<Unit> =
        suspendCancellableCoroutine { continuation ->

            val docRef = usersCollection.document(userProfile.uid)

            docRef.get()
                .addOnSuccessListener { snapshot ->
                    if (!continuation.isActive) return@addOnSuccessListener

                    if (snapshot.exists()) {
                        continuation.resume(Result.success(Unit))
                    } else {
                        val data = mapOf(
                            "uid" to userProfile.uid,
                            "fullName" to userProfile.fullName,
                            "email" to userProfile.email,
                            "nickname" to userProfile.nickName,
                            "photoUrl" to userProfile.photoUrl,
                            "createdAt" to userProfile.createdAt,
                            "lastLoginAt" to userProfile.lastLoginAt
                        )

                        docRef.set(data)
                            .addOnSuccessListener {
                                if (continuation.isActive) {
                                    continuation.resume(Result.success(Unit))
                                }
                            }
                            .addOnFailureListener { exception ->
                                if (continuation.isActive) {
                                    continuation.resume(Result.failure(exception = exception))
                                }
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    if (continuation.isActive) continuation.resume(Result.failure(exception))
                }
        }

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

    override suspend fun updateUserData(data: Map<String, Any>): Result<Unit> {
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
                        continuation.resume(Result.failure(exception = exception))
                    }
                }
        }
    }

    private fun getCurrentUserUidOrNull(): String? =
        firebaseAuth.currentUser?.uid
}
