package com.example.recipebook.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.recipebook.domain.util.ImageCompressor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ImageCompressorImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ImageCompressor {
    override suspend fun compress(uriString: String?): ByteArray = withContext(Dispatchers.IO) {
        val uri = Uri.parse(uriString)
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Cannot open inputStream for uri: $uri")

        val originalBitmap = inputStream.use { stream ->
            BitmapFactory.decodeStream(stream)
                ?: throw IllegalArgumentException("Cannot decode bitmap for uri: $uri")
        }

        val targetWidth = 512
        val targetHeight = (originalBitmap.height * targetWidth) / originalBitmap.width
        val scaledBitmap = Bitmap.createScaledBitmap(
            originalBitmap,
            targetWidth,
            targetHeight,
            true
        )

        val outputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        outputStream.toByteArray()
    }
}