package id.frogobox.footballapps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import id.frogobox.footballapps.R.id.*
import id.frogobox.footballapps.mvp.main.MainActivity
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