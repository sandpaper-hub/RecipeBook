package com.example.recipebook.data.repository

import com.example.recipebook.domain.interactor.profile.FirestoreRepository
import com.example.recipebook.domain.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class FirestoreRepositoryImpl @Inject constructor(
    firestore: FirebaseFirestore
) : FirestoreRepository {

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

    override suspend fun getUserProfile(uid: String): Result<UserProfile> =
        suspendCancellableCoroutine { continuation ->
            usersCollection.document(uid)
                .get()
                .addOnSuccessListener { snapshot ->
                    val user = snapshot.toObject(UserProfile::class.java)
                    if (user != null) {
                        if (continuation.isActive) continuation.resume(Result.success(user))
                    } else {
                        if (continuation.isActive)
                            continuation.resume(Result.failure(Exception("User not found")))
                    }
                }
                .addOnFailureListener { error ->
                    if (continuation.isActive) continuation.resume(Result.failure(error))
                }
        }
}
