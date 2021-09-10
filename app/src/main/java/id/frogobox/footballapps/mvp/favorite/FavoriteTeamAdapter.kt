package id.frogobox.footballapps.mvp.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.frogobox.footballapps.R
import id.frogobox.footballapps.models.FavoriteTeam
import kotlinx.android.synthetic.main.content_list_team.view.*

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * FootBallApps
 * Copyright (C) 04/12/2018.
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
class FavoriteTeamAdapter(
    private val teams: List<FavoriteTeam>,
    private var listener: (FavoriteTeam) -> Unit
) : RecyclerView.Adapter<FavoriteTeamAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.content_list_team, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int = teams.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageFavoriteTeam = view.imageview_team_list_badge
        private val textFavoriteTeam = view.textview_team_list_name

        fun bindItem(dataFavoriteTeam: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
            textFavoriteTeam.text = dataFavoriteTeam.teamName
            Picasso.get().load(dataFavoriteTeam.teamBadge).into(imageFavoriteTeam)
            itemView.setOnClickListener {
                listener(dataFavoriteTeam)
            }
        }
    }
}