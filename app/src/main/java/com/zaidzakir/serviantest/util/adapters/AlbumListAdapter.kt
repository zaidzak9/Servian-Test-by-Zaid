package com.zaidzakir.serviantest.util.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zaidzakir.serviantest.R
import com.zaidzakir.serviantest.data.models.albums.AlbumDataItem
import com.zaidzakir.serviantest.data.models.users.UsersMainDataItem
import com.zaidzakir.serviantest.util.Constants
import com.zaidzakir.serviantest.databinding.AdapterAlbumListBinding
import kotlinx.android.synthetic.main.adapter_album_list.view.*

/**
 *Created by Zaid Zakir
 */
class AlbumListAdapter : ListAdapter<AlbumDataItem, RecyclerView.ViewHolder>(AlbumInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlbuminfoViewHolder(
            AdapterAlbumListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val albumInfo = getItem(position)
        (holder as AlbuminfoViewHolder).bind(albumInfo)
    }

    class AlbuminfoViewHolder(
        private val binding: AdapterAlbumListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { itemView ->
                binding.albumInfo?.let { albumInfo ->
                    navigateToImageViewer(albumInfo,itemView)
                }
            }
        }

        private fun navigateToImageViewer(
            albumInfo: AlbumDataItem,
            view: View
        ) {
            val bundle = Bundle().apply {
                putSerializable(Constants.ALBUM, albumInfo)
            }
            view.findNavController().navigate(R.id.action_albumListFragment_to_albumImageViewFragment,bundle)
        }

        fun bind(item: AlbumDataItem) {
            binding.apply {
                albumInfo = item
                executePendingBindings()
            }
        }
    }
}

private class AlbumInfoDiffCallback : DiffUtil.ItemCallback<AlbumDataItem>() {

    override fun areItemsTheSame(oldItem: AlbumDataItem, newItem: AlbumDataItem): Boolean {
        return oldItem.thumbnailUrl == newItem.thumbnailUrl
    }

    override fun areContentsTheSame(oldItem: AlbumDataItem, newItem: AlbumDataItem): Boolean {
        return oldItem == newItem
    }
}
