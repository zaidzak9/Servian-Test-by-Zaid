package com.zaidzakir.serviantest.ui

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zaidzakir.serviantest.R
import com.zaidzakir.serviantest.data.models.users.UsersMainData
import com.zaidzakir.serviantest.util.Constants.ID
import com.zaidzakir.serviantest.util.Status
import com.zaidzakir.serviantest.util.adapters.UserInfoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_info.*

/**
 *Created by Zaid Zakir
 */

@AndroidEntryPoint
class UserInfoFragment : Fragment(R.layout.fragment_user_info) {
    lateinit var userInfoAdapter: UserInfoAdapter
    lateinit var mainViewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.fragment_user_info_header)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recyclerView()
        getUserInfoFromLiveData()

        userInfoAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(ID, it)
            }
            findNavController().navigate(
                R.id.action_userInfoFragment_to_albumListFragment,
                bundle
            )
        }
    }

    private fun getUserInfoFromLiveData() {
        mainViewModel.getUserInfoApi()
        mainViewModel.users.observe(viewLifecycleOwner, {
            it?.contentIfHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        userInfoAdapter.differ.submitList(result.data)
                        saveUserInfoToDB(result.data)
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        mainViewModel.getLocalUserInfo.observe(
                            viewLifecycleOwner,
                            { userInfoLocal ->
                                if (userInfoLocal.isNotEmpty()) {
                                    //in case of no internet or api error load data from DB
                                    userInfoAdapter.differ.submitList(userInfoLocal)
                                } else {
                                    view?.let {
                                        Snackbar.make(
                                            it,
                                            result.error.toString(),
                                            Snackbar.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            })
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun recyclerView() {
        userInfoAdapter = UserInfoAdapter()

        userInfo_recycler_view.apply {
            adapter = userInfoAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun saveUserInfoToDB(data: UsersMainData?) {
        //on first time load always save copy of data in DB if DB is empty
        mainViewModel.getLocalUserInfo.observe(viewLifecycleOwner, { userInfoLocal ->
            if (userInfoLocal.isEmpty()) {
                data?.let {
                    for (info in data) {
                        mainViewModel.insertUserIntoDB(info)
                    }
                }
            }
        })
    }
}