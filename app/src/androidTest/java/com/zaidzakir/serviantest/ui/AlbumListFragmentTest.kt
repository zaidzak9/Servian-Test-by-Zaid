package com.zaidzakir.serviantest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.zaidzakir.serviantest.R
import com.zaidzakir.serviantest.data.models.albums.AlbumDataItem
import com.zaidzakir.serviantest.launchFragmentInHiltContainer
import com.zaidzakir.serviantest.reposiotry.FakeRepositoryAndroidTest
import com.zaidzakir.serviantest.util.adapters.AlbumListAdapter
import com.zaidzakir.serviantest.viewmodel.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

/**
 * Created by Zaid Zakir
 */
@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class AlbumListFragmentTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun clickAlbumInfo_gotoNextScreen(){
        val navController = Mockito.mock(NavController::class.java)
        val arrayList = ArrayList<AlbumDataItem>()
        val testViewModel = MainViewModel(FakeRepositoryAndroidTest())
        val userInfoItem = AlbumDataItem(
            0,
            0, "url", "title",
            "url"
        )
        arrayList.add(userInfoItem)
        launchFragmentInHiltContainer<AlbumListFragment>{
            Navigation.setViewNavController(requireView(),navController)
            albumListAdapter.differ.submitList(arrayList)
            mainViewModel = testViewModel
        }
        onView(ViewMatchers.withId(R.id.albumList_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<AlbumListAdapter.AlbumListViewHolder>(
                0,
                ViewActions.click()
            )
        )
        verify(navController).navigate(R.id.action_albumListFragment_to_albumImageViewFragment)
    }
}