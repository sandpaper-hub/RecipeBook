package com.example.recipebook.data.repository

import com.example.recipebook.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class FirebaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : FirebaseRepository {

    override fun register(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = task.result?.user
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }
                    firebaseUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                onSuccess()
                            }
                        }
                } else {
                    onError(task.exception?.message ?: "Error")
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