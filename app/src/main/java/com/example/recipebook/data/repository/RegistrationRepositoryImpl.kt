package com.example.recipebook.data.repository

import com.example.recipebook.domain.repository.RegistrationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : RegistrationRepository {

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

}