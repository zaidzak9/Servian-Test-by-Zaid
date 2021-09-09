package com.zaidzakir.serviantest.data.localDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaidzakir.serviantest.data.models.albums.AlbumDataItem
import com.zaidzakir.serviantest.data.models.users.UsersMainDataItem

/**
 *Created by Zaid Zakir
 */
@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userItem: UsersMainDataItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbumInfo(albumDataItem: AlbumDataItem)

    @Query("SELECT * FROM user_item")
    fun observeAllUserData(): LiveData<List<UsersMainDataItem>>

    @Query("SELECT * FROM album_item")
    fun observeAllAlbumData(): LiveData<List<AlbumDataItem>>

}