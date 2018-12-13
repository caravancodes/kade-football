package id.frogobox.footballapps

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
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
class MainActivityTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testViewPagerBottomNavigationBehavior() {
        try {
            Thread.sleep(3000)
            onView(withId(navigation)).check(matches(isDisplayed()))
            onView(withId(navigation_match)).perform(click())
            Thread.sleep(3000)
            onView(withId(navigation_team)).perform(click())
            Thread.sleep(3000)
            onView(withId(navigation_favorite)).perform(click())
        } catch (e: NoMatchingViewException) {
        }
    }
}