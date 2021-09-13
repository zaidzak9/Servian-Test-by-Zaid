package com.zaidzakir.serviantest.repository

import androidx.lifecycle.LiveData
import com.zaidzakir.serviantest.data.localDB.UserDAO
import com.zaidzakir.serviantest.data.models.albums.AlbumData
import com.zaidzakir.serviantest.data.models.albums.AlbumDataItem
import com.zaidzakir.serviantest.data.models.users.UsersMainData
import com.zaidzakir.serviantest.data.models.users.UsersMainDataItem
import com.zaidzakir.serviantest.data.remote.UserApi
import com.zaidzakir.serviantest.util.Resource
import java.lang.Exception
import javax.inject.Inject

/**
 *Created by Zaid Zakir
 */
class DefaultRepository @Inject constructor(
    private val userApi: UserApi,
    private val userDAO: UserDAO
):MainRepository {

    override suspend fun getRemoteUserInfo(): Resource<UsersMainData> {
        return try {
            val response = userApi.getUsers()
            if (response.isSuccessful){
                response.body()?.let {userResponse ->
                    return@let Resource.success(userResponse)
                }?: Resource.error("An unknown error occurred",null)
            }else{
                Resource.error("An unknown error occurred",null)
            }
        }catch (e: Exception){
            return Resource.error("Something went wrong! $e",null)
        }
    }

    override suspend fun getRemoteAlbumInfo(albumId:String): Resource<AlbumData> {
        return try {
            val response = userApi.getAlbums(albumId)
            if (response.isSuccessful){
                response.body()?.let {albumResponse ->
                    return@let Resource.success(albumResponse)
                }?: Resource.error("An unknown error occurred",null)
            }else{
                Resource.error("An unknown error occurred",null)
            }
        }catch (e:Exception){
            return Resource.error("Something went wrong! $e",null)
        }
    }

    override fun getLocalUserInfo(): LiveData<List<UsersMainDataItem>> {
       return userDAO.observeAllUserData()
    }

    override fun getLocalAlbumInfo(): LiveData<List<AlbumDataItem>> {
       return userDAO.observeAllAlbumData()
    }

    override suspend fun insertAlbumInfo(albumDataItem: AlbumDataItem) {
        userDAO.insertAlbumInfo(albumDataItem)
    }

    override suspend fun insertUserInfo(usersMainDataItem: UsersMainDataItem) {
        userDAO.insertUserInfo(usersMainDataItem)
    }
}