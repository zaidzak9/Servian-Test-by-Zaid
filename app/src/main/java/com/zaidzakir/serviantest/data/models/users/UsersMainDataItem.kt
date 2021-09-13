package com.zaidzakir.serviantest.data.models.users

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user_item")
data class UsersMainDataItem(
    val email: String?,
    @PrimaryKey
    val id: Int?,
    val name: String?,
    val phone: String?,
    val username: String?,
    val website: String?
)