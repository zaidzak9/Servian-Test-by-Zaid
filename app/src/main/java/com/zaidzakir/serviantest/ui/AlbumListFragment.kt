package com.zaidzakir.serviantest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zaidzakir.serviantest.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_album_list.*
import kotlinx.android.synthetic.main.fragment_user_info.*

/**
 *Created by Zaid Zakir
 */

@AndroidEntryPoint
class AlbumListFragment :Fragment(R.layout.fragment_album_list) {

    lateinit var mainViewModel: MainViewModel
    val args:AlbumListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.fragment_album_list_header)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val id = args.ID
    }
}