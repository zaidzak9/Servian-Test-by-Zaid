package com.zaidzakir.serviantest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zaidzakir.serviantest.R
import com.zaidzakir.serviantest.data.models.albums.AlbumData
import com.zaidzakir.serviantest.data.models.users.UsersMainData
import com.zaidzakir.serviantest.util.Constants
import com.zaidzakir.serviantest.util.Status
import com.zaidzakir.serviantest.util.adapters.AlbumListAdapter
import com.zaidzakir.serviantest.util.adapters.UserInfoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_album_list.*
import kotlinx.android.synthetic.main.fragment_user_info.*

/**
 *Created by Zaid Zakir
 */

@AndroidEntryPoint
class AlbumListFragment :Fragment(R.layout.fragment_album_list) {
    private lateinit var albumListAdapter: AlbumListAdapter
    lateinit var mainViewModel: MainViewModel
    val args:AlbumListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recyclerView()
        val id = args.ID
        activity?.title = getString(R.string.fragment_album_list_header) + ": $id"
        getAlbumListFromLiveData(id)

        albumListAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(Constants.URL, it)
            }
            findNavController().navigate(
                R.id.action_albumListFragment_to_albumImageViewFragment,
                bundle
            )
        }
    }

    private fun getAlbumListFromLiveData(id: String) {
        mainViewModel.getAlbumListApi(id)
        mainViewModel.albums.observe(viewLifecycleOwner, {
            it?.contentIfHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        albumFragmentProgressBar.visibility = View.GONE
                        albumListAdapter.differ.submitList(result.data)
                        saveAlbumItemsToDB(result.data)
                    }
                    Status.ERROR -> {
                        albumFragmentProgressBar.visibility = View.GONE
                        mainViewModel.getLocalAlbumList.observe(
                            viewLifecycleOwner,
                            { albumInfoLocal ->
                                if (albumInfoLocal.isNotEmpty()) {
                                    //in case of no internet or api error load data from DB
                                    albumListAdapter.differ.submitList(albumInfoLocal)

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
                        albumFragmentProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun recyclerView() {
        albumListAdapter = AlbumListAdapter()
        albumList_recycler_view.apply {
            adapter = albumListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun saveAlbumItemsToDB(data: AlbumData?) {
        //on first time load always save copy of data in DB if DB is empty
        mainViewModel.getLocalAlbumList.observe(viewLifecycleOwner, { userAlbumLocal ->
            if (userAlbumLocal.isEmpty()) {
                data?.let {
                    for (info in data) {
                        mainViewModel.insertAlbumIntoDB(info)
                    }
                }
            }
        })
    }
}