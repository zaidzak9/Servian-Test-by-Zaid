package com.zaidzakir.serviantest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.zaidzakir.serviantest.MainCoroutineRule
import com.zaidzakir.serviantest.data.models.albums.AlbumDataItem
import com.zaidzakir.serviantest.data.models.users.UsersMainDataItem
import com.zaidzakir.serviantest.getOrAwaitValueTest
import com.zaidzakir.serviantest.repository.FakeRepositoryTest
import com.zaidzakir.serviantest.util.Status
import com.zaidzakir.serviantest.viewmodel.MainViewModel
import org.junit.Before
import org.junit.Rule

import org.junit.Test

/**
 * Created by Zaid Zakir
 */
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel =  MainViewModel(FakeRepositoryTest())
    }

    @Test
    fun `insert user info with correct fields, return true`(){
        val userInfo = UsersMainDataItem("zaid@zaid.com",1,"zaid","1234567","zaidun","www.user.com")
        viewModel.insertUserIntoDB(userInfo)

        val value = viewModel.usersDB.getOrAwaitValueTest()

        assertThat(value.contentIfHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `insert album item with correct fields, return true`(){
        val album = AlbumDataItem(1,1,"url","title","urlmain")
        viewModel.insertAlbumIntoDB(album)

        val value = viewModel.albumsDB.getOrAwaitValueTest()

        assertThat(value.contentIfHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}