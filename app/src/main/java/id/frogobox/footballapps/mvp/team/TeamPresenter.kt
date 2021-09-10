package id.frogobox.footballapps.mvp.team

import com.google.gson.Gson
import id.frogobox.footballapps.models.Team
import id.frogobox.footballapps.models.TeamResponse
import id.frogobox.footballapps.sources.ApiRepository
import id.frogobox.footballapps.sources.ApiUrl
import id.frogobox.footballapps.utils.CoroutineContextProvider
import id.frogobox.footballapps.utils.TestContextProvider
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * FootBallApps
 * Copyright (C) 03/12/2018.
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
class TeamPresenter(
    private val callback: TeamCallback<Team>,
    private val coroutine: CoroutineContextProvider = CoroutineContextProvider()
) {
    
    suspend inline fun <reified T> createApi(url: String): T {
        return Gson().fromJson(ApiRepository.doRequest(url), T::class.java)
    }

    @DelicateCoroutinesApi
    fun getTeamList(leagueName: String) {
        try {
            GlobalScope.launch(coroutine.main) {
                val data = createApi<TeamResponse>(ApiUrl.urlTeamsByLeagueName(leagueName))
                callback.hideLoading()
                callback.onResult(data.teams)
            }
        } catch (e: Exception) {
            callback.showLoading()
            callback.onFailed(e.localizedMessage)
        }
    }

    @DelicateCoroutinesApi
    fun getTeamDetailById(teamID: String?) {
        try {
            GlobalScope.launch(coroutine.main) {
                val dataTeam = createApi<TeamResponse>(ApiUrl.urlTeamDetailById(teamID))
                callback.hideLoading()
                callback.onResult(dataTeam.teams)
            }
        } catch (e: Exception) {
            callback.showLoading()
            callback.onFailed(e.localizedMessage)
        }
    }

    @DelicateCoroutinesApi
    fun getSearchTeamsList(e: String?) {
        GlobalScope.launch(coroutine.main) {
            try {
                val data = createApi<TeamResponse>(ApiUrl.urlTeamsSearch(e))
                callback.hideLoading()
                callback.onResult(data.teams)
            } catch (e: Exception) {
                callback.showLoading()
                callback.onFailed(e.localizedMessage)
            }

        }
    }

}