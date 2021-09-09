package com.zaidzakir.serviantest.data.localDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zaidzakir.serviantest.data.models.albums.AlbumDataItem
import com.zaidzakir.serviantest.data.models.users.UsersMainDataItem

/**
 *Created by Zaid Zakir
 */
@Database(
    entities = [UsersMainDataItem::class,AlbumDataItem::class],
    version = 1
)
abstract class ServianDatabase: RoomDatabase() {
    abstract fun getUserDao():UserDAO
}