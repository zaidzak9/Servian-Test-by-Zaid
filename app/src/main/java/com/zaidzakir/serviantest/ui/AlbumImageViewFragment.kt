package com.zaidzakir.serviantest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zaidzakir.serviantest.R
import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Zaid Zakir
 */

@AndroidEntryPoint
class AlbumImageViewFragment : Fragment(R.layout.fragment_album_view_image) {

    lateinit var mainViewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
}