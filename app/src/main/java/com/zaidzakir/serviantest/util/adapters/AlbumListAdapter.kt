package com.zaidzakir.serviantest.util.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zaidzakir.serviantest.R
import com.zaidzakir.serviantest.data.models.albums.AlbumData

/**
 *Created by Zaid Zakir
 */
class AlbumListAdapter : RecyclerView.Adapter<AlbumListAdapter.AlbumListViewHolder>() {
    class AlbumListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<AlbumData>() {
        override fun areItemsTheSame(oldItem: AlbumData, newItem: AlbumData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AlbumData, newItem: AlbumData): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListViewHolder {
        return AlbumListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_album_list,
                parent,
                false
            )
        )
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: AlbumListViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}