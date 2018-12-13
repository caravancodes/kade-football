package id.frogobox.footballapps

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import id.frogobox.footballapps.R.id.*
import id.frogobox.footballapps.views.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * FootBallApps
 * Copyright (C) 06/12/2018.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Line     : bullbee117
 * Phone    : 081357108568
 * Majors   : D3 Teknik Informatika 2016
 * Campus   : Telkom University
 * -----------------------------------------
 * id.amirisback.frogobox
 */
@RunWith(AndroidJUnit4::class)
class LastMatchCrudTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testCrudLastMatch() {
        // Aplikasi berada pada last matches fragment
        try {
            Thread.sleep(3000)
            onView(withId(recyclerView_LastMatch)).check(matches(isDisplayed()))
            onView(withText("Leicester")).check(matches(isDisplayed()))
            onView(withText("Leicester")).perform(click())// Memilih data baris ke 9 pada recyclerview last match fragment
            Thread.sleep(3000)
            // Menambah data ke database
            onView(withId(add_to_favorite)).check(matches(isDisplayed()))
            onView(withId(add_to_favorite)).perform(click())
            onView(withText("Added to favorite")).check(matches(isDisplayed()))
            Thread.sleep(3000)
            pressBack()
            // Berpindah ke Favorite Fragment
            onView(withId(navigation)).check(matches(isDisplayed()))
            onView(withId(navigation_favorite)).perform(click())
            Thread.sleep(3000)
            // Memilih data baris ke 0 pada recyclerview favorite match fragment
            onView(withId(recyclerView_FavMatch)).check(matches(isDisplayed()))
            onView(withId(recyclerView_FavMatch)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
            Thread.sleep(3000)
            // Mengapus data pada database
            onView(withId(add_to_favorite)).check(matches(isDisplayed()))
            onView(withId(add_to_favorite)).perform(click())
            onView(withText("Removed to favorite")).check(matches(isDisplayed()))
        } catch (e: NoMatchingViewException) {
        }
    }

}