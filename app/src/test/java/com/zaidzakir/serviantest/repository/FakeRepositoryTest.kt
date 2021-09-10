package com.zaidzakir.serviantest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zaidzakir.serviantest.data.models.albums.AlbumData
import com.zaidzakir.serviantest.data.models.albums.AlbumDataItem
import com.zaidzakir.serviantest.data.models.users.UsersMainData
import com.zaidzakir.serviantest.data.models.users.UsersMainDataItem
import com.zaidzakir.serviantest.util.Resource

/**
 *Created by Zaid Zakir
 */
class FakeRepositoryTest : MainRepository {

    private val userInfoItems = mutableListOf<UsersMainDataItem>()
    private val albumListItems = mutableListOf<AlbumDataItem>()

    private val observeUserInfoItems = MutableLiveData<List<UsersMainDataItem>>(userInfoItems)
    private val observeAlbumListItems = MutableLiveData<List<AlbumDataItem>>(albumListItems)

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value:Boolean){
        shouldReturnNetworkError = value
    }

    override suspend fun getRemoteUserInfo(): Resource<UsersMainData> {
        return if (shouldReturnNetworkError){
            Resource.Error("Error",null)
        }else{
            Resource.Success(UsersMainData())
        }
    }

    override suspend fun getRemoteAlbumInfo(albumId: String): Resource<AlbumData> {
        return if (shouldReturnNetworkError){
            Resource.Error("Error",null)
        }else{
            Resource.Success(AlbumData())
        }
    }

    override fun getLocalUserInfo(): LiveData<List<UsersMainDataItem>> {
        return observeUserInfoItems
    }

    override fun getLocalAlbumInfo(): LiveData<List<AlbumDataItem>> {
        return observeAlbumListItems
    }

    override suspend fun insertAlbumInfo(albumDataItem: AlbumDataItem) {
        albumListItems.add(albumDataItem)
        refreshLiveData()
    }

    override suspend fun insertUserInfo(usersMainDataItem: UsersMainDataItem) {
        userInfoItems.add(usersMainDataItem)
        refreshLiveData()
    }

    private fun refreshLiveData(){
        observeAlbumListItems.postValue(albumListItems)
        observeUserInfoItems.postValue(userInfoItems)
    }


}