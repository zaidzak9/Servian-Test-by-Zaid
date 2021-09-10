package com.zaidzakir.serviantest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaidzakir.serviantest.data.localDB.ServianDatabase
import com.zaidzakir.serviantest.data.models.albums.AlbumData
import com.zaidzakir.serviantest.data.models.albums.AlbumDataItem
import com.zaidzakir.serviantest.data.models.users.UsersMainData
import com.zaidzakir.serviantest.data.models.users.UsersMainDataItem
import com.zaidzakir.serviantest.data.remote.UserApi
import com.zaidzakir.serviantest.repository.DefaultRepository
import com.zaidzakir.serviantest.repository.MainRepository
import com.zaidzakir.serviantest.util.Events
import com.zaidzakir.serviantest.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Zaid Zakir
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    val getLocalUserInfo = repository.getLocalUserInfo()

    val getLocalAlbumList = repository.getLocalAlbumInfo()

    private val _users = MutableLiveData<Events<Resource<UsersMainData>>>()
    val users:LiveData<Events<Resource<UsersMainData>>> = _users

    private val _albums = MutableLiveData<Events<Resource<AlbumData>>>()
    val albums:LiveData<Events<Resource<AlbumData>>> = _albums

    fun insertUserIntoDB(usersMainDataItem: UsersMainDataItem) = viewModelScope.launch {
        repository.insertUserInfo(usersMainDataItem)
    }

    fun insertAlbumIntoDB(albumDataItem: AlbumDataItem) = viewModelScope.launch {
        repository.insertAlbumInfo(albumDataItem)
    }

    fun getUserInfoApi() {
        _users.value = Events(Resource.Loading(null))
        viewModelScope.launch {
            val response = repository.getRemoteUserInfo()
            _users.value = Events(response)
        }
    }

    fun getAlbumListApi(albumId:String) {
        if (albumId.isNullOrEmpty()){
            return
        }
        _albums.value = Events(Resource.Loading(null))
        viewModelScope.launch {
            val response = repository.getRemoteAlbumInfo(albumId)
            _albums.value = Events(response)
        }
    }

}