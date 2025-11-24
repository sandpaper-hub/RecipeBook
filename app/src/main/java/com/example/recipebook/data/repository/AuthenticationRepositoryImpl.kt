package com.example.recipebook.data.repository

import com.example.recipebook.domain.model.UserProfile
import com.example.recipebook.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthenticationRepository {

    override suspend fun register(
        name: String,
        email: String,
        password: String
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
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }

                    firebaseUser.updateProfile(profileUpdates)
                        .addOnCompleteListener { updateTask ->
                            if (!continuation.isActive) return@addOnCompleteListener
                            if (!updateTask.isSuccessful) {
                                val error =
                                    updateTask.exception ?: Exception("Profile update error")
                                continuation.resume(Result.failure(error))
                            }
                            val domainUser = UserProfile(
                                uid = firebaseUser.uid,
                                fullName = name,
                                email = firebaseUser.email ?: email
                            )

                            continuation.resume(Result.success(domainUser))
                        }
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

    override fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}