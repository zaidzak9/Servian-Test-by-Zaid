package com.zaidzakir.serviantest.repository

import androidx.lifecycle.LiveData
import com.zaidzakir.serviantest.data.models.albums.AlbumData
import com.zaidzakir.serviantest.data.models.albums.AlbumDataItem
import com.zaidzakir.serviantest.data.models.users.UsersMainData
import com.zaidzakir.serviantest.data.models.users.UsersMainDataItem
import com.zaidzakir.serviantest.util.Resource

/**
 *Created by Zaid Zakir
 */
interface MainRepository {

    suspend fun getRemoteUserInfo(): Resource<UsersMainData>

    suspend fun getRemoteAlbumInfo(albumId:String): Resource<AlbumData>

    fun getLocalUserInfo():LiveData<List<UsersMainDataItem>>

    fun getLocalAlbumInfo():LiveData<List<AlbumDataItem>>

    suspend fun insertAlbumInfo(albumDataItem: AlbumDataItem)

    suspend fun insertUserInfo(usersMainDataItem: UsersMainDataItem)
}