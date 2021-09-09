package com.zaidzakir.serviantest.util
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.IOException
import java.net.URL

/**
 *Created by Zaid Zakir
 */
object ImageViewerHelper {
    // extension property to get bitmap from url
    val URL.toBitmap:Bitmap?
        get() {
            return try {
                BitmapFactory.decodeStream(openStream())
            }catch (e: IOException){null}
        }

//    val urlImage = URL("test")
//    // async task to get bitmap from url
//    val result: Deferred<Bitmap?> = lifecycleScope.async(Dispatchers.IO) {
//        urlImage.toBitmap
//    }
//
//    lifecycleScope.launch(Dispatchers.Main) {
//        // show bitmap on image view when available
//        binding.imageview.setImageBitmap(result.await())
//    }
}