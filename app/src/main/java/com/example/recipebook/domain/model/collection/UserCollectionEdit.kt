package com.example.recipebook.domain.model.collection

import com.example.recipebook.domain.model.ImageSourceType

data class UserCollectionEdit(
    val id:String = "",
    val name: String = "",
    val description: String = "",
    val imageSource: ImageSourceType = ImageSourceType.None,
)
