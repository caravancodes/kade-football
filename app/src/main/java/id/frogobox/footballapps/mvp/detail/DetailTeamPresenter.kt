package id.frogobox.footballapps.mvp.detail

import com.google.gson.Gson
import id.frogobox.footballapps.utils.CoroutineContextProvider
import id.frogobox.footballapps.sources.ApiRepository
import id.frogobox.footballapps.sources.TheSportDBApi
import id.frogobox.footballapps.models.TeamResponse
import id.frogobox.footballapps.mvp.team.TeamCallback
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * FootBallApps
 * Copyright (C) 05/12/2018.
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
class DetailTeamPresenter (private val callback: TeamCallback,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    @DelicateCoroutinesApi
    fun getTeamDetailById(teamID: String?) {
        try {
            GlobalScope.launch(context.main) {
                val dataTeam = gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getTeamDetailById(teamID)),
                    TeamResponse::class.java
                )
                callback.hideLoading()
                callback.showData(dataTeam.teams)
            }
        } catch (e: Exception) {
            callback.showLoading()
        }
    }
}