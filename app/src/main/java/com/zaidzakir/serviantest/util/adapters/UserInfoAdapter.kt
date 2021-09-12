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
import com.zaidzakir.serviantest.data.models.users.UsersMainDataItem
import com.zaidzakir.serviantest.databinding.AdapterUserInfoBinding
import com.zaidzakir.serviantest.util.Constants
import com.zaidzakir.serviantest.util.Constants.TAG
import kotlinx.android.synthetic.main.adapter_user_info.view.*

/**
 *Created by Zaid Zakir
 */
class UserInfoAdapter : ListAdapter<UsersMainDataItem, RecyclerView.ViewHolder>(UserInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserInfoViewHolder(
            AdapterUserInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val userInfo = getItem(position)
        (holder as UserInfoViewHolder).bind(userInfo)
    }

    class UserInfoViewHolder(
        private val binding: AdapterUserInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { itemView ->
                binding.userInfo?.let { userInfo ->
                    navigateToAlbumInfo(userInfo,itemView)
                }
            }
        }

        private fun navigateToAlbumInfo(
            userInfo: UsersMainDataItem,
            view: View
        ) {
            val bundle = Bundle().apply {
                putSerializable(Constants.ID, userInfo.id.toString())
            }
            view.findNavController().navigate(R.id.action_userInfoFragment_to_albumListFragment,bundle)
        }

        fun bind(item: UsersMainDataItem) {
            println("$TAG $item")
            binding.apply {
                userInfo = item
                executePendingBindings()
            }
        }
    }
}

private class UserInfoDiffCallback : DiffUtil.ItemCallback<UsersMainDataItem>() {

    override fun areItemsTheSame(oldItem: UsersMainDataItem, newItem: UsersMainDataItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: UsersMainDataItem, newItem: UsersMainDataItem): Boolean {
        return oldItem == newItem
    }
}
