package com.zaidzakir.serviantest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.zaidzakir.serviantest.R
import com.zaidzakir.serviantest.data.models.users.UsersMainDataItem
import com.zaidzakir.serviantest.getOrAwaitValue
import com.zaidzakir.serviantest.launchFragmentInHiltContainer
import com.zaidzakir.serviantest.reposiotry.FakeRepositoryAndroidTest
import com.zaidzakir.serviantest.util.Constants
import com.zaidzakir.serviantest.util.Constants.ID
import com.zaidzakir.serviantest.util.adapters.UserInfoAdapter
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
class UserInfoFragmentTest{
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun clickUserInfo_gotoNextScreen(){
        val navController = Mockito.mock(NavController::class.java)
        val arrayList = ArrayList<UsersMainDataItem>()
        val testViewModel = MainViewModel(FakeRepositoryAndroidTest())
        val userInfoItem = UsersMainDataItem(
            "zaid@email.com",
            0, "zaid", "12345678",
            "zaidzak", "www.zaid.com"
        )
        arrayList.add(userInfoItem)
        launchFragmentInHiltContainer<UserInfoFragment>{
            Navigation.setViewNavController(requireView(),navController)
            userInfoAdapter.differ.submitList(arrayList)
            mainViewModel = testViewModel
        }
        onView(withId(R.id.userInfo_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<UserInfoAdapter.UserInfoViewHolder>(
                0,
                ViewActions.click()
            )
        )
        verify(navController).navigate(R.id.action_userInfoFragment_to_albumListFragment)
    }
}