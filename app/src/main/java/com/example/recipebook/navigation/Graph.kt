package com.example.recipebook.navigation

object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
    const val MAIN_HOME = "main_home"
    const val SETTINGS = "settings_graph"
    const val COLLECTION = "collection_graph"
    const val RECIPE_DETAIL = "recipe_detail_graph"
}

sealed class Root(val route: String) {
    data object Splash : Root("splash")
}