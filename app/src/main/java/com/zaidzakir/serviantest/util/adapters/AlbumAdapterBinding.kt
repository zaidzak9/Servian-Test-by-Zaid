package com.zaidzakir.serviantest.util.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.zaidzakir.serviantest.R
import com.zaidzakir.serviantest.util.ImageViewerHelper
import kotlinx.android.synthetic.main.adapter_album_list.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *Created by Zaid Zakir
 */

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        CoroutineScope(Dispatchers.IO).launch{
            val bitmap = ImageViewerHelper.downloadBitmap(imageUrl.toString())
            withContext(Dispatchers.Main){
                if (bitmap == null){
                    view.setImageResource(R.drawable.ic_servian_logo)
                }else{
                    view.setImageBitmap(bitmap)
                }
            }
        }
    }
}