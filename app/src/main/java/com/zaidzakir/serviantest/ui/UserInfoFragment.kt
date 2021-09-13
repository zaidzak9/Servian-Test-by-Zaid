package com.zaidzakir.serviantest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zaidzakir.serviantest.R
import com.zaidzakir.serviantest.data.models.users.UsersMainData
import com.zaidzakir.serviantest.databinding.FragmentUserInfoBinding
import com.zaidzakir.serviantest.util.Status
import com.zaidzakir.serviantest.util.adapters.UserInfoAdapter
import com.zaidzakir.serviantest.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_info.*

/**
 *Created by Zaid Zakir
 */

@AndroidEntryPoint
class UserInfoFragment : Fragment() {
    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        context ?: return binding.root
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val userInfoAdapter = UserInfoAdapter()
        binding.userInfoRecyclerView.adapter = userInfoAdapter
        binding.userInfoRecyclerView.layoutManager = LinearLayoutManager(activity)
        activity?.title = getString(R.string.fragment_user_info_header)
        getUserInfoFromLiveData(userInfoAdapter)
        return binding.root
    }
    private fun getUserInfoFromLiveData(userInfoAdapter: UserInfoAdapter) {
        mainViewModel.getUserInfoApi()
        mainViewModel.users.observe(viewLifecycleOwner, {
            it?.contentIfHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        userInfoAdapter.submitList(result.data)
                        saveUserInfoToDB(result.data)
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        mainViewModel.getLocalUserInfo.observe(
                            viewLifecycleOwner,
                            { userInfoLocal ->
                                if (userInfoLocal.isNotEmpty()) {
                                    //in case of no internet or api error load data from DB
                                    userInfoAdapter.submitList(userInfoLocal)
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