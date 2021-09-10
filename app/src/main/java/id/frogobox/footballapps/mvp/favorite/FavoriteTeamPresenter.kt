package id.frogobox.footballapps.mvp.favorite

import android.content.Context
import android.widget.ProgressBar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.frogobox.footballapps.models.FavoriteTeam
import id.frogobox.footballapps.sources.database
import id.frogobox.footballapps.utils.invisible
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

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
class FavoriteTeamPresenter(
    private val context: Context?,
    private var favorites: MutableList<FavoriteTeam>,
    private val progressBar: ProgressBar,
    private val swipeRefresh: SwipeRefreshLayout,
    private var adapter: FavoriteTeamAdapter
) {

    fun showFavorite() {
        progressBar.invisible()
        favorites.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

}