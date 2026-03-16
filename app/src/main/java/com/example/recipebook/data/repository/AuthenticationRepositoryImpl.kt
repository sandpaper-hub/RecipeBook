package com.example.recipebook.data.repository

import com.example.recipebook.data.mapper.mapFailure
import com.example.recipebook.data.mapper.toAuthException
import com.example.recipebook.domain.model.profile.UserProfile
import com.example.recipebook.domain.repository.AuthenticationRepository
import com.example.recipebook.data.util.StringConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

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
    ): Result<UserProfile> = runCatching {
        val firebaseUser = auth.createUserWithEmailAndPassword(email, password)
            .await()
            .user
            ?: error("User null")

        val uri = storage.reference
            .child(StringConstants.DEFAULT_PROFILE_IMAGE_PATH)
            .downloadUrl
            .await()

        val profileUpdates = userProfileChangeRequest {
            displayName = name
            photoUri = uri
        }
        firebaseUser.updateProfile(profileUpdates).await()
        UserProfile(
            uid = firebaseUser.uid,
            fullName = name,
            nickName = nickName,
            email = firebaseUser.email ?: email,
            photoUrl = uri.toString()
        )
    }.mapFailure { it.toAuthException() }


    override suspend fun signIn(
        email: String,
        password: String
    ): Result<Unit> = runCatching {
        auth.signInWithEmailAndPassword(email, password)
            .await()
        Unit
    }.mapFailure { it.toAuthException() }

    override suspend fun createUserDocumentIfNeeded(userProfile: UserProfile): Result<Unit> =
        runCatching {
            val docRef = usersCollection.document(userProfile.uid)
            val snapshot = docRef.get().await()

            if (!snapshot.exists()) {
                val data = mapOf(
                    "uid" to userProfile.uid,
                    "fullName" to userProfile.fullName,
                    "email" to userProfile.email,
                    "nickName" to userProfile.nickName,
                    "photoUrl" to userProfile.photoUrl,
                    "createdAt" to userProfile.createdAt
                )
                docRef.set(data).await()
            }
        }

    override fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun logOut() {
        auth.signOut()
    }

    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}