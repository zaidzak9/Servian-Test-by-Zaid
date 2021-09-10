package com.zaidzakir.serviantest.util.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zaidzakir.serviantest.R
import com.zaidzakir.serviantest.data.models.users.UsersMainData

/**
 *Created by Zaid Zakir
 */
class UserInfoAdapter : RecyclerView.Adapter<UserInfoAdapter.UserInfoViewHolder>() {

    class UserInfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<UsersMainData>() {
        override fun areItemsTheSame(oldItem: UsersMainData, newItem: UsersMainData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UsersMainData, newItem: UsersMainData): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoViewHolder {
        return UserInfoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_user_info,
                parent,
                false
            )
        )
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}