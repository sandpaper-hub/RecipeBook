package com.example.recipebook.domain.util

interface ImageCompressor {
    suspend fun compress(uriString: String?): ByteArray
}