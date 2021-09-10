package com.zaidzakir.serviantest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zaidzakir.serviantest.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_info.*

/**
 *Created by Zaid Zakir
 */

@AndroidEntryPoint
class UserInfoFragment : Fragment(R.layout.fragment_user_info) {

    lateinit var mainViewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
}