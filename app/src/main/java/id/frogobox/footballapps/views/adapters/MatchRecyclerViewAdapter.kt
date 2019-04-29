package id.frogobox.footballapps.views.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.frogobox.footballapps.R
import id.frogobox.footballapps.helpers.utils.formatDateToMatch
import id.frogobox.footballapps.helpers.utils.formatTimeToMatch
import id.frogobox.footballapps.models.dataclass.Match
import kotlinx.android.synthetic.main.content_list_match.view.*

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
class MatchRecyclerViewAdapter (private val context: Context?, private val matches: List<Match>, private var listener : (Match) -> Unit) : RecyclerView.Adapter<MatchRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.content_list_match, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(matches[position], listener)
    }

    override fun getItemCount(): Int = matches.size

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view){

        private val matchDate = view.textview_match_list_date
        private val matchTime = view.textview_match_list_time
        private val matchHomeTeam = view.textview_match_list_home_team
        private val matchHomeScore= view.textview_match_list_home_score
        private val matchAwayTeam= view.textview_match_list_away_team
        private val matchAwayScore = view.textview_match_list_away_score

        fun bindItem(dataMatch: Match, listener: (Match) -> Unit) {
            matchDate.text = dataMatch.dateMatch?.formatDateToMatch()
            matchTime.text = dataMatch.timeMatch?.formatTimeToMatch()
            matchHomeTeam.text = dataMatch.homeTeam
            matchHomeScore.text = dataMatch.homeScore
            matchAwayTeam.text = dataMatch.awayTeam
            matchAwayScore.text = dataMatch.awayScore
            itemView.setOnClickListener {
                listener(dataMatch)
            }
        }

    }
}



