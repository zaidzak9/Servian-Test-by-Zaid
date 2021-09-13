package com.zaidzakir.serviantest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zaidzakir.serviantest.R
import com.zaidzakir.serviantest.data.models.albums.AlbumData
import com.zaidzakir.serviantest.databinding.FragmentAlbumListBinding
import com.zaidzakir.serviantest.util.Status
import com.zaidzakir.serviantest.util.adapters.AlbumListAdapter
import com.zaidzakir.serviantest.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_album_list.*

/**
 *Created by Zaid Zakir
 */

@AndroidEntryPoint
class AlbumListFragment :Fragment() {
    private var id:String?=null
    val albumListAdapter = AlbumListAdapter()
    lateinit var mainViewModel: MainViewModel
    val args:AlbumListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.albumListRecyclerView.adapter = albumListAdapter
        binding.albumListRecyclerView.layoutManager = LinearLayoutManager(activity)
        id = args.ID
        activity?.title = getString(R.string.fragment_album_list_header) + ": $id"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id?.let { getAlbumListFromLiveData(it,albumListAdapter) }
    }

    private fun getAlbumListFromLiveData(id: String, albumListAdapter: AlbumListAdapter) {
        mainViewModel.getAlbumListApi(id)
        mainViewModel.albums.observe(viewLifecycleOwner, {
            it?.contentIfHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        albumFragmentProgressBar.visibility = View.GONE
                        albumListAdapter.submitList(result.data)
                        saveAlbumItemsToDB(result.data)
                    }
                    Status.ERROR -> {
                        albumFragmentProgressBar.visibility = View.GONE
                        mainViewModel.getLocalAlbumList.observe(
                            viewLifecycleOwner,
                            { albumInfoLocal ->
                                if (albumInfoLocal.isNotEmpty()) {
                                    //in case of no internet or api error load data from DB
                                    albumListAdapter.submitList(albumInfoLocal)

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