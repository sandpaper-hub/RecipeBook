package com.example.recipebook.navigation.mainHomeGraph.collectionDetailGraph

sealed class CollectionDetailRoutes(val route: String) {

    val fullRoute: String
        get() = "$route/{${CollectionDetailDestination.COLLECTION_ID_ARG}}"

    fun createRoute(collectionId: String) =
        "$route/$collectionId"

    data object CollectionDetail :
        CollectionDetailRoutes(CollectionDetailDestination.COLLECTION_DETAIL)
}

object CollectionDetailDestination {
    const val COLLECTION_DETAIL = "collection_detail"
    const val COLLECTION_ID_ARG = "collectionId"
}
