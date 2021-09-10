package com.zaidzakir.serviantest.util.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zaidzakir.serviantest.R
import com.zaidzakir.serviantest.data.models.users.UsersMainData
import com.zaidzakir.serviantest.data.models.users.UsersMainDataItem
import kotlinx.android.synthetic.main.adapter_user_info.view.*

/**
 *Created by Zaid Zakir
 */
class UserInfoAdapter : RecyclerView.Adapter<UserInfoAdapter.UserInfoViewHolder>() {

    class UserInfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<UsersMainDataItem>() {
        override fun areItemsTheSame(oldItem: UsersMainDataItem, newItem: UsersMainDataItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UsersMainDataItem, newItem: UsersMainDataItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

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
        val userInfo = differ.currentList[position]
        holder.itemView.apply {
            tv_id.text = userInfo.id.toString()
            tv_name.text = userInfo.name
            tv_email.text = userInfo.email
            tv_phone.text = userInfo.phone
            setOnClickListener {
                onItemClickListener?.let {
                    it(userInfo.id.toString())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}