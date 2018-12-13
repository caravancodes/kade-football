package id.frogobox.footballapps.presenters

import com.google.gson.Gson
import id.frogobox.footballapps.helpers.coroutines.CoroutineContextProvider
import id.frogobox.footballapps.helpers.networks.ApiRepository
import id.frogobox.footballapps.helpers.networks.TheSportDBApi
import id.frogobox.footballapps.helpers.response.SearchMatchResponse
import id.frogobox.footballapps.views.interfaces.SearchMatchView
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
class SearchMatchPresenter (private val view: SearchMatchView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getSearchMatchesList(e: String?){
        GlobalScope.launch(context.main) {
            try {
                val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getEventsSearch(e)).await(), SearchMatchResponse::class.java)
                view.showSearchMatchList(data.event)
            } catch (e: Exception) {
                view.showSearchMatchListNull()
            }

        }
    }
}