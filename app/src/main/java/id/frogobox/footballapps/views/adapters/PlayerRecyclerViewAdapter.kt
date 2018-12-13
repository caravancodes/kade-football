package id.frogobox.footballapps.views.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import id.frogobox.footballapps.R
import id.frogobox.footballapps.models.dataclass.Player
import kotlinx.android.synthetic.main.content_list_player.view.*

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
class PlayerRecyclerViewAdapter (private val context: Context?, private val players: List<Player>, private var listener :
    (Player) -> Unit) : RecyclerView.Adapter<PlayerRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.content_list_player, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

    override fun getItemCount(): Int = players.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val imagePlayer = view.imageview_player_list_badge
        private val textPlayerName = view.textview_player_list_name
        private val textPlayerPosition = view.textview_player_list_position

        fun bindItem(dataPlayer: Player, listener: (Player) -> Unit) {
            textPlayerName.text = dataPlayer.playerName
            textPlayerPosition.text = dataPlayer.playerPosition
            Picasso.get().load(dataPlayer.playerPhoto).into(imagePlayer)
            itemView.setOnClickListener {
                listener(dataPlayer)
            }
        }
    }
}