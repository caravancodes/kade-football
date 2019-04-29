package id.frogobox.footballapps.presenters

import android.content.Context
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.widget.ProgressBar
import id.frogobox.footballapps.helpers.utils.invisible
import id.frogobox.footballapps.models.databases.database
import id.frogobox.footballapps.models.dataclass.FavoriteMatch
import id.frogobox.footballapps.views.adapters.FavoriteMatchRecyclerViewAdapter
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
class FavoriteListMatchPresenter (private val context: Context?,
                                  private var favorites: MutableList<FavoriteMatch>,
                                  private val progressBar: ProgressBar,
                                  private val swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout,
                                  private var adapter: FavoriteMatchRecyclerViewAdapter
){

    fun showFavorite(){
        progressBar.invisible()
        favorites.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

}