package com.example.recipebook.data.repository

import com.example.recipebook.data.util.ImageCompressorImpl
import com.example.recipebook.domain.model.collection.UserCollection
import com.example.recipebook.domain.repository.CollectionsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
    private val auth: FirebaseAuth,
    private val imageCompressorImpl: ImageCompressorImpl
) : CollectionsRepository {

    private val userId get() = auth.currentUser!!.uid

    override suspend fun createDocument(): String {
        val document = firestore
            .collection("users")
            .document(userId)
            .collection("collections")
            .document()
        return document.id
    }

    override suspend fun createCollection(
        collectionId: String,
        userCollection: UserCollection
    ): Result<Unit> {
        return suspendCancellableCoroutine { continuation ->
            firestore
                .collection("users")
                .document(userId)
                .collection("collections")
                .document(collectionId)
                .set(userCollection)
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

    override suspend fun uploadCollectionImage(
        collectionId: String,
        imageSource: String
    ): String {
        val ref = firebaseStorage.reference
            .child("collections")
            .child(collectionId)
            .child("cover")
            .child("collection_cover.jpg")

        ref.putBytes(imageCompressorImpl.compress(imageSource)).await()
        return ref.downloadUrl.await().toString()
    }
}