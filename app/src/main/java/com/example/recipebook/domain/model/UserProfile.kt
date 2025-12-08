package com.example.recipebook.domain.model

data class UserProfile(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val nickName: String = "",
    val photoUrl: String? = null,
    val createdAt: Long = 0L,
    val lastLoginAt: Long = 0L,
    val region: String = "",
    val dateOfBirth: String = "",
    val gender: String = ""
)