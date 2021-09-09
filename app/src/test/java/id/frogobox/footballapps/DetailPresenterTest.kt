package id.frogobox.footballapps

import com.google.gson.Gson
import id.frogobox.footballapps.sources.ApiRepository
import id.frogobox.footballapps.sources.TheSportDBApi
import id.frogobox.footballapps.models.TeamResponse
import id.frogobox.footballapps.models.Team
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

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
class DetailPresenterTest {

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit
    var apiRepository: ApiRepository


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun testMatchDetail() {
        // Testing Presenter untuk Match Detail pada DetailActivity
        val homeTeams: MutableList<Team> = mutableListOf()
        val awayTeams: MutableList<Team> = mutableListOf()
        val responseHomeTeam = TeamResponse(homeTeams)
        val responseAwayTeam = TeamResponse(awayTeams)
        val eventID = "576595"
        val homeTeamID = "133623"
        val awayTeamID = "134777"

        GlobalScope.launch {

            Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetailById(homeTeamID)).await(),
                TeamResponse::class.java
            )).thenReturn(responseHomeTeam)

            Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetailById(awayTeamID)).await(),
                TeamResponse::class.java
            )).thenReturn(responseAwayTeam)
        }
    }



}