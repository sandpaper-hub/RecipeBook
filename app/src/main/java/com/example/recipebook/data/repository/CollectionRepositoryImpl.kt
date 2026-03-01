package com.example.recipebook.data.repository

import com.example.recipebook.data.dto.CollectionDto
import com.example.recipebook.data.mapper.toDomain
import com.example.recipebook.data.mapper.toDto
import com.example.recipebook.data.util.ImageCompressorImpl
import com.example.recipebook.domain.model.collection.UserCollection
import com.example.recipebook.domain.repository.CollectionsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    override fun observeUserCollections(userId: String): Flow<List<UserCollection>> =
        callbackFlow {
            val listener = firestore
                .collection("users")
                .document(userId)
                .collection("collections")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val collections = snapshot
                        ?.documents
                        ?.map { doc ->
                            val collection = doc.toObject(CollectionDto::class.java)?.toDomain()
                            collection!!.copy(id = doc.id)
                        }
                        ?: emptyList()
                    trySend(collections)
                }
            awaitClose {
                listener.remove()
            }
        }

    override fun observeCollectionDetail(
        userId: String,
        collectionId: String
    ): Flow<UserCollection?> = callbackFlow {
        val listener = firestore
            .collection("users")
            .document(userId)
            .collection("collections")
            .document(collectionId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot == null || !snapshot.exists()) {
                    trySend(null)
                    return@addSnapshotListener
                }

                val collection = snapshot
                    .toObject(CollectionDto::class.java)
                    ?.toDomain()

                trySend(collection)
            }
        awaitClose { listener.remove() }
    }

    override suspend fun createDocument(): String {
        val document = firestore
            .collection("users")
            .document(userId)
            .collection("collections")
            .document()
        return document.id
    }

    override suspend fun createCollection(
        userCollection: UserCollection
    ): Result<Unit> {
        return suspendCancellableCoroutine { continuation ->
            val collectionDto = userCollection.toDto()
            firestore
                .collection("users")
                .document(userId)
                .collection("collections")
                .document(collectionDto.id)
                .set(collectionDto)
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

    override suspend fun addRecipeToCollection(
        collectionId: String,
        recipeId: String
    ) {
        firestore.collection("users")
            .document(userId)
            .collection("collections")
            .document(collectionId)
            .update("recipeIds", FieldValue.arrayUnion(recipeId))
            .await()
    }

    override suspend fun removeRecipeFromCollection(
        collectionId: String,
        recipeId: String
    ) {
        firestore.collection("users")
            .document(userId)
            .collection("collections")
            .document(collectionId)
            .update("recipeIds", FieldValue.arrayRemove(recipeId))
            .await()
    }

    override suspend fun deleteCollection(collectionId: String) {
        firestore
            .collection("users")
            .document(userId)
            .collection("collections")
            .document(collectionId)
            .delete()
            .await()
    }

    override suspend fun getCollectionById(collectionId: String): UserCollection {
        return firestore
            .collection("users")
            .document(userId)
            .collection("collections")
            .document(collectionId)
            .get()
            .await()
            .toObject(CollectionDto::class.java)?.toDomain()
            ?: throw IllegalStateException("Collection not found")
    }
}