package com.zaidzakir.serviantest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.zaidzakir.serviantest.R
import com.zaidzakir.serviantest.databinding.FragmentAlbumViewImageBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Zaid Zakir
 */

@AndroidEntryPoint
class AlbumImageViewFragment : Fragment() {
    val args:AlbumImageViewFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAlbumViewImageBinding.inflate(inflater, container, false)
        context ?: return binding.root
        activity?.title = getString(R.string.fragment_album_image_viewer_header)
        binding.args = args
        return binding.root
    }
}