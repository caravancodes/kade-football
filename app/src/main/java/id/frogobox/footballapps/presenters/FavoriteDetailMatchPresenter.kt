package id.frogobox.footballapps.presenters

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import android.support.v4.widget.SwipeRefreshLayout
import id.frogobox.footballapps.helpers.utils.formatDateToMatch
import id.frogobox.footballapps.helpers.utils.formatTimeToMatch
import id.frogobox.footballapps.models.databases.database
import id.frogobox.footballapps.models.dataclass.FavoriteMatch
import id.frogobox.footballapps.models.dataclass.Match
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * FootBallApps
 * Copyright (C) 13/12/2018.
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
class FavoriteDetailMatchPresenter(private val context: Context,
                                   private val matchData: MutableList<Match>,
                                   private val swipeRefresh: SwipeRefreshLayout) {

    var isFavorite = false

    fun addToFavorite(){
        try {
            context.database.use {
                insert(
                    FavoriteMatch.TABLE_FAVORITE_MATCH,
                    FavoriteMatch.EVENT_ID to matchData.first().eventId,
                    FavoriteMatch.DATE_MATCH to matchData.first().dateMatch?.formatDateToMatch(),
                    FavoriteMatch.TIME_MATCH to matchData.first().timeMatch?.formatTimeToMatch(),
                    FavoriteMatch.HOME_ID to matchData.first().homeTeamId,
                    FavoriteMatch.HOME_TEAM to matchData.first().homeTeam,
                    FavoriteMatch.HOME_SCORE to matchData.first().homeScore,
                    FavoriteMatch.AWAY_ID to matchData.first().awayTeamId,
                    FavoriteMatch.AWAY_TEAM to matchData.first().awayTeam,
                    FavoriteMatch.AWAY_SCORE to matchData.first().awayScore
                )
            }
            swipeRefresh.snackbar("Added to favorite").show()
        } catch (e: SQLiteException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    fun removeFromFavorite(id: String?){
        try {
            context.database.use {
                delete(FavoriteMatch.TABLE_FAVORITE_MATCH, "(" + FavoriteMatch.EVENT_ID + "=" + id + ")")
            }
            swipeRefresh.snackbar("Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    fun favoriteState(id: String?){
        context.database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                .whereArgs(FavoriteMatch.EVENT_ID+"="+id)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

}

