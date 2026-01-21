package com.thejohonsondev.ui.utils

import androidx.compose.ui.graphics.ImageBitmap
import kotlin.io.encoding.Base64

expect fun ByteArray.toComposeImageBitmap(): ImageBitmap

fun String.base64ImageToImageBitmap(): ImageBitmap? {
    return try {
        var base64String = this
        if (base64String.isBlank()) return null

        if (base64String.contains(",")) {
            base64String = base64String.substringAfterLast(",")
        }

        val imageBytes = Base64.Mime.decode(base64String)

        imageBytes.toComposeImageBitmap()
    } catch (e: Exception) {
         e.printStackTrace() // Uncomment to see the specific error in Logcat
        null
    }
}