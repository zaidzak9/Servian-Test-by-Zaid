package com.zaidzakir.serviantest.repository

import androidx.lifecycle.LiveData
import com.zaidzakir.serviantest.data.localDB.ServianDatabase
import com.zaidzakir.serviantest.data.models.albums.AlbumData
import com.zaidzakir.serviantest.data.models.albums.AlbumDataItem
import com.zaidzakir.serviantest.data.models.users.UsersMainData
import com.zaidzakir.serviantest.data.models.users.UsersMainDataItem
import com.zaidzakir.serviantest.data.remote.UserApi
import com.zaidzakir.serviantest.util.Resource
import javax.inject.Inject

/**
 *Created by Zaid Zakir
 */
class DefaultRepository @Inject constructor(
    val userApi: UserApi,
    val servianDatabase: ServianDatabase
):MainRepository {

    override suspend fun getRemoteUserInfo(): Resource<UsersMainData> {
        TODO("Not yet implemented")
    }

    override suspend fun getRemoteAlbumInfo(): Resource<AlbumData> {
        TODO("Not yet implemented")
    }

    override fun getLocalUserInfo(): LiveData<List<UsersMainDataItem>> {
        TODO("Not yet implemented")
    }

    override fun getLocalAlbumInfo(): LiveData<List<AlbumDataItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAlbumInfo(albumDataItem: AlbumDataItem) {
        TODO("Not yet implemented")
    }

    override suspend fun insertUserInfo(usersMainDataItem: UsersMainDataItem) {
        TODO("Not yet implemented")
    }
}