package com.zaidzakir.serviantest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.zaidzakir.serviantest.R
import com.zaidzakir.serviantest.util.ImageViewerHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_album_view_image.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *Created by Zaid Zakir
 */

@AndroidEntryPoint
class AlbumImageViewFragment : Fragment(R.layout.fragment_album_view_image) {
    lateinit var mainViewModel: MainViewModel
    val args:AlbumImageViewFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        activity?.title = getString(R.string.fragment_album_image_viewer_header)
        val album = args.Album
        album?.albumId.let {
            album_id_tv.text = "Album ID : ${it.toString()}"
        }
        album?.id.let {
            photo_id_tv.text = "Photo ID : ${it.toString()}"
        }
        CoroutineScope(Dispatchers.IO).launch{
            val bitmap = ImageViewerHelper.downloadBitmap(album?.url.toString())
            withContext(Dispatchers.Main){
                album_Full_ImageView.setImageBitmap(bitmap)
            }
        }
        album?.title.let {
            album_image_desc_tv.text = it.toString()
        }
    }
}