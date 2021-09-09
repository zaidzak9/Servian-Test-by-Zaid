package com.zaidzakir.serviantest.data.remote

import com.zaidzakir.serviantest.data.models.albums.AlbumData
import com.zaidzakir.serviantest.data.models.users.UsersMainData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *Created by Zaid Zakir
 */
interface UserApi {

    @GET("/users")
    suspend fun getUsers(): Response<UsersMainData>

    @GET("/photos?")
    suspend fun getAlbums(
        @Query("albumId") searchQuery:String
    ): Response<AlbumData>
}