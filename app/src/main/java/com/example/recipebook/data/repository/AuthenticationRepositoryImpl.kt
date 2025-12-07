package com.example.recipebook.data.repository

import com.example.recipebook.domain.model.UserProfile
import com.example.recipebook.domain.repository.AuthenticationRepository
import com.example.recipebook.util.StringConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    firestore: FirebaseFirestore
) : AuthenticationRepository {

    private val usersCollection = firestore.collection("users")

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        nickName: String
    ): Result<UserProfile> =
        suspendCancellableCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (!continuation.isActive) return@addOnCompleteListener

                    if (!task.isSuccessful) {
                        val error = task.exception ?: Exception("Registration error")
                        continuation.resume(Result.failure(error))
                        return@addOnCompleteListener
                    }

                    val firebaseUser = task.result?.user
                    if (firebaseUser == null) {
                        continuation.resume(Result.failure(Exception("User is null after registration")))
                        return@addOnCompleteListener
                    }

                    val defaultAvatarRef =
                        storage.reference.child(StringConstants.DEFAULT_PROFILE_IMAGE_PATH)

                    defaultAvatarRef.downloadUrl
                        .addOnSuccessListener { uri ->
                            if (!continuation.isActive) {
                                return@addOnSuccessListener
                            }
                            val profileUpdates = userProfileChangeRequest {
                                displayName = name
                                photoUri = uri
                            }

                            firebaseUser.updateProfile(profileUpdates)
                                .addOnCompleteListener { updateTask ->
                                    if (!continuation.isActive) return@addOnCompleteListener
                                    if (!updateTask.isSuccessful) {
                                        val error =
                                            updateTask.exception
                                                ?: Exception("Profile update error")
                                        continuation.resume(Result.failure(error))
                                    }
                                    val domainUser = UserProfile(
                                        uid = firebaseUser.uid,
                                        fullName = name,
                                        nickName = nickName,
                                        email = firebaseUser.email ?: email,
                                        photoUrl = uri.toString()
                                    )

                                    continuation.resume(Result.success(domainUser))
                                }
                        }
                }
                .addOnFailureListener { exception ->
                    if (!continuation.isActive) return@addOnFailureListener
                    continuation.resume(Result.failure(exception = exception))
                }
        }


    override suspend fun signIn(
        email: String,
        password: String
    ): Result<Unit> =
        suspendCancellableCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (!continuation.isActive) return@addOnCompleteListener
                    if (task.isSuccessful) {
                        continuation.resume(Result.success(Unit))
                    } else {
                        val exception = task.exception ?: Exception("Unknown error")
                        continuation.resume(Result.failure(exception))
                    }
                }
        }

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
                            "nickName" to userProfile.nickName,
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

    override fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}