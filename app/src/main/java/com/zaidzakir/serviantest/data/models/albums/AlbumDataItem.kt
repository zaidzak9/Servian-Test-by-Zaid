package com.zaidzakir.serviantest.data.models.albums

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "album_item")
data class AlbumDataItem(
    @PrimaryKey
    val albumId: Int?,
    val id: Int?,
    val thumbnailUrl: String?,
    val title: String?,
    val url: String?
):Serializable