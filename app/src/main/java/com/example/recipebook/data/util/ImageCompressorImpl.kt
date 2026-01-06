package com.example.recipebook.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import com.example.recipebook.domain.util.ImageCompressor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import androidx.core.net.toUri
import androidx.core.graphics.scale
import java.lang.IllegalArgumentException

class ImageCompressorImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ImageCompressor {
    override suspend fun compress(uriString: String): ByteArray = withContext(Dispatchers.IO) {
        val uri = uriString.toUri()

        val orientation = context.contentResolver
            .openInputStream(uri)
            ?.use { stream ->
                ExifInterface(stream).getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
            } ?: ExifInterface.ORIENTATION_NORMAL

        val originalBitmap = context.contentResolver
            .openInputStream(uri)
            ?.use { stream ->
                BitmapFactory.decodeStream(stream)
            }
            ?: throw IllegalArgumentException("Cannot decode bitmap")

        val rotateBitmap = originalBitmap.rotateIfNeeded(orientation)

        val targetWidth = 512
        val targetHeight = (rotateBitmap.height * targetWidth) / rotateBitmap.width
        val scaledBitmap = rotateBitmap.scale(targetWidth, targetHeight)

        val outputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        outputStream.toByteArray()
    }

    private fun Bitmap.rotateIfNeeded(orientation: Int): Bitmap {
        val matrix = Matrix()

        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            else -> return this
        }

        return Bitmap.createBitmap(
            this, 0, 0, width, height, matrix, true
        )
    }
}