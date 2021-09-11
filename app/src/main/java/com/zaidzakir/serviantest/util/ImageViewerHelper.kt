package com.zaidzakir.serviantest.util
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.zaidzakir.serviantest.util.Constants.BACKUP_IMG_URL
import com.zaidzakir.serviantest.util.Constants.TAG
import java.lang.Exception
import java.net.URL

/**
 *Created by Zaid Zakir
 * download bitmap will download users image using privided url.
 * if it fails it will use a url value from constants
 * if that fails as well servians logo will be used from drawables folder
 */
object ImageViewerHelper {
    fun downloadBitmap(imageUrl:String):Bitmap?{
        return try {
            bitmapCreator(imageUrl)
        }catch (e:Exception){
            //most of the thumbnails from the api are corrupted so im filling with this sample image
            Log.d(TAG,e.toString())
            downloadBackUpBitmap(BACKUP_IMG_URL)
        }
    }

    private fun downloadBackUpBitmap(imageUrl:String):Bitmap?{
        return try {
            bitmapCreator(imageUrl)
        }catch (e:Exception){
            Log.d(TAG,e.toString())
            null
        }
    }

    private fun bitmapCreator(imageUrl: String):Bitmap {
        val connection = URL(imageUrl).openConnection()
        connection.connect()
        val inputStream = connection.getInputStream()
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()
        return bitmap
    }
}