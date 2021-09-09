package id.frogobox.footballapps.mvp.team

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import id.frogobox.footballapps.R
import id.frogobox.footballapps.models.Team
import kotlinx.android.synthetic.main.content_list_team.view.*

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
class TeamViewAdapter (private val context: Context?, private val teams: List<Team>, private var listener :
    (Team) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<TeamViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.content_list_team_grid, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int = teams.size

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view){

        private val imageTeam = view.imageview_team_list_badge
        private val textTeam = view.textview_team_list_name

        fun bindItem(dataTeam: Team, listener: (Team) -> Unit) {
            textTeam.text = dataTeam.teamName
            Picasso.get().load(dataTeam.teamBadge).into(imageTeam)
            itemView.setOnClickListener {
                listener(dataTeam)
            }
        }
    }
}