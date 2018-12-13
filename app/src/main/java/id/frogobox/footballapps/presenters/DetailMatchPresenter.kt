package id.frogobox.footballapps.presenters

import com.google.gson.Gson
import id.frogobox.footballapps.helpers.coroutines.CoroutineContextProvider
import id.frogobox.footballapps.helpers.networks.ApiRepository
import id.frogobox.footballapps.helpers.networks.TheSportDBApi
import id.frogobox.footballapps.helpers.response.MatchResponse
import id.frogobox.footballapps.helpers.response.TeamResponse
import id.frogobox.footballapps.views.interfaces.DetailMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * FootBallApps
 * Copyright (C) 04/12/2018.
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
class DetailMatchPresenter (private val view: DetailMatchView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getMatchDetail(eventID: String?, homeTeamID: String?, awayTeamID: String?) {

        try {
            GlobalScope.launch(context.main) {
                val dataMatch = gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getEventDetailById(eventID)).await(),
                    MatchResponse::class.java
                )

                val dataHomeTeam = gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getTeamDetailById(homeTeamID)).await(),
                    TeamResponse::class.java
                )

                val dataAwayTeam = gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getTeamDetailById(awayTeamID)).await(),
                    TeamResponse::class.java
                )

                view.hideLoading()
                view.showData(dataMatch.events, dataHomeTeam.teams, dataAwayTeam.teams)
            }
        } catch (e: Exception) {
            view.showLoading()
        }
    }
}