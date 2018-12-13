package id.frogobox.footballapps.models.dataclass

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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
@Parcelize
data class FavoriteMatch(
    val id: Long?,
    var eventId: String?,
    var dateMatch: String?,
    var timeMatch: String?,
    var homeTeamId: String?,
    var homeTeam: String?,
    var homeScore: String?,
    var awayTeamId: String?,
    var awayTeam: String?,
    var awayScore: String?) : Parcelable {

    companion object {
        const val TABLE_FAVORITE_MATCH: String = "TABLE_FAVORITE_MATCH"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val DATE_MATCH: String = "DATE_MATCH"
        const val TIME_MATCH: String = "TIME_MATCH"
        const val HOME_ID: String = "HOME_ID"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_ID: String = "AWAY_ID"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val AWAY_SCORE: String = "AWAY_SCORE"
    }

}