package id.frogobox.footballapps.mvp.favorite

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.frogobox.footballapps.models.FavoriteTeam
import id.frogobox.footballapps.models.Team
import id.frogobox.footballapps.sources.database
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
class FavoriteTeamDetailPresenter(
    private val context: Context,
    private val teamData: MutableList<Team>,
    private val swipeRefresh_teamDetail: SwipeRefreshLayout
) {

    var isFavorite = false

    fun addToFavorite() {
        try {
            context.database.use {
                insert(
                    FavoriteTeam.TABLE_FAVORITE_TEAM,
                    FavoriteTeam.TEAM_ID to teamData.first().teamId.toString(),
                    FavoriteTeam.TEAM_NAME to teamData.first().teamName.toString(),
                    FavoriteTeam.TEAM_BADGE to teamData.first().teamBadge.toString(),
                    FavoriteTeam.TEAM_FORMEDYEAR to teamData.first().teamFormedYear.toString(),
                    FavoriteTeam.TEAM_STADIUM to teamData.first().teamStadium.toString(),
                    FavoriteTeam.TEAM_DESCRIPTION to teamData.first().teamDescription.toString()
                )
            }
            swipeRefresh_teamDetail.snackbar("Added to favorite").show()
        } catch (e: SQLiteException) {
            swipeRefresh_teamDetail.snackbar(e.localizedMessage).show()
        }
    }

    fun removeFromFavorite(id: String?) {
        try {
            context.database.use {
                delete(
                    FavoriteTeam.TABLE_FAVORITE_TEAM,
                    "(" + FavoriteTeam.TEAM_ID + "=" + id + ")"
                )
            }
            swipeRefresh_teamDetail.snackbar("Removed to favorite").show()
        } catch (e: SQLiteConstraintException) {
            swipeRefresh_teamDetail.snackbar(e.localizedMessage).show()
        }
    }

    fun favoriteState(id: String?) {
        context.database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                .whereArgs(FavoriteTeam.TEAM_ID + "=" + id)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) {
                isFavorite = true
            }
        }
    }


}
