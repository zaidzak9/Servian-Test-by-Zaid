package com.zaidzakir.serviantest.util
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.zaidzakir.serviantest.util.Constants.TAG
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.IOException
import java.lang.Exception
import java.net.URL

/**
 *Created by Zaid Zakir
 */
object ImageViewerHelper {
    fun downloadBitmap(imageUrl:String):Bitmap?{
        return try {
            val connection = URL(imageUrl).openConnection()
            connection.connect()
            val inputStream = connection.getInputStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            bitmap
        }catch (e:Exception){
            //most of the thumbnails from the api are corrupted so im filling with this sample image
            Log.d(TAG,e.toString())
            downloadBitmap("https://image.shutterstock.com/image-vector/sample-stamp-grunge-texture-vector-600w-1389188327.jpg")
        }
    }
}