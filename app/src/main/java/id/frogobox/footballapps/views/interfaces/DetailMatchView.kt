package id.frogobox.footballapps.views.interfaces

import id.frogobox.footballapps.models.dataclass.Match
import id.frogobox.footballapps.models.dataclass.Team

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
interface DetailMatchView {
    fun showLoading()
    fun hideLoading()
    fun showData(dataMatch: List<Match>, dataHomeTeam: List<Team>, dataAwayTeam: List<Team>)
}