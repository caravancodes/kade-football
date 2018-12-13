package id.frogobox.footballapps.presenters

import com.google.gson.Gson
import id.frogobox.footballapps.helpers.coroutines.CoroutineContextProvider
import id.frogobox.footballapps.helpers.networks.ApiRepository
import id.frogobox.footballapps.helpers.networks.TheSportDBApi
import id.frogobox.footballapps.helpers.response.TeamResponse
import id.frogobox.footballapps.views.interfaces.SearchTeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
class SearchTeamPresenter (private val view: SearchTeamView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getSearchTeamsList(e: String?){
        GlobalScope.launch(context.main) {
            try {
                val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeamsSearch(e)).await(), TeamResponse::class.java)
                view.showSearchTeamList(data.teams)
            } catch (e: Exception) {
                view.showSearchTeamListNull()
            }

        }
    }
}