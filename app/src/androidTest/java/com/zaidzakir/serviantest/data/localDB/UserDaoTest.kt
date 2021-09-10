package com.zaidzakir.serviantest.data.localDB

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.zaidzakir.serviantest.data.models.albums.AlbumDataItem
import com.zaidzakir.serviantest.data.models.users.UsersMainDataItem
import com.zaidzakir.serviantest.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

/**
 *Created by Zaid Zakir
 */

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class UserDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: ServianDatabase

    private lateinit var dao: UserDAO

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.getUserDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertUserInfoTest() = runBlockingTest {
        val userInfo = UsersMainDataItem(
            "zaid@email.com",
            1, "zaid", "12345678",
            "zaidzak", "www.zaid.com"
        )
        dao.insertUserInfo(userInfo)
        val observeAllUserInfo = dao.observeAllUserData().getOrAwaitValue()
        assertThat(observeAllUserInfo).contains(userInfo)
    }

    @Test
    fun insertAlbumInfo() = runBlockingTest {
        val albumInfo = AlbumDataItem(1, 1, "url", "title", "urlmain")
        dao.insertAlbumInfo(albumInfo)
        val observeAllAlbumInfo = dao.observeAllAlbumData().getOrAwaitValue()
        assertThat(observeAllAlbumInfo).contains(albumInfo)
    }


}