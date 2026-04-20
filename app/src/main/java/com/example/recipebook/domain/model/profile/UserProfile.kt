package com.example.recipebook.domain.model.profile

data class UserProfile(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val nickName: String = "",
    val photoUrl: String? = null,
    val region: String = "",
    val dateOfBirth: Long = 0L,
    val gender: String = ""
)