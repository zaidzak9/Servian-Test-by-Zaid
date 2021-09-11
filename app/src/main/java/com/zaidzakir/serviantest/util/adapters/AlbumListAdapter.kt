package com.zaidzakir.serviantest.util.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zaidzakir.serviantest.R
import com.zaidzakir.serviantest.data.models.albums.AlbumDataItem
import com.zaidzakir.serviantest.util.ImageViewerHelper
import kotlinx.android.synthetic.main.adapter_album_list.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *Created by Zaid Zakir
 */
class AlbumListAdapter : RecyclerView.Adapter<AlbumListAdapter.AlbumListViewHolder>() {
    class AlbumListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<AlbumDataItem>() {
        override fun areItemsTheSame(oldItem: AlbumDataItem, newItem: AlbumDataItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AlbumDataItem, newItem: AlbumDataItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListViewHolder {
        return AlbumListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_album_list,
                parent,
                false
            )
        )
    }

    private var onItemClickListener: ((AlbumDataItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (AlbumDataItem) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: AlbumListViewHolder, position: Int) {
        val albumInfo = differ.currentList[position]
        holder.itemView.apply {
            tv_image_name.text = albumInfo.title
            CoroutineScope(Dispatchers.IO).launch{
                val bitmap = ImageViewerHelper.downloadBitmap(albumInfo.thumbnailUrl.toString())
                withContext(Dispatchers.Main){
                    if (bitmap == null){
                        iv_thumbnail.setImageResource(R.drawable.ic_servian_logo)
                    }else{
                        iv_thumbnail.setImageBitmap(bitmap)
                    }
                }
            }
            setOnClickListener {
                onItemClickListener?.let {
                    it(albumInfo)
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}