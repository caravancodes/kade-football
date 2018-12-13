package id.frogobox.footballapps.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.squareup.picasso.Picasso
import id.frogobox.footballapps.R
import id.frogobox.footballapps.models.dataclass.Player
import kotlinx.android.synthetic.main.activity_player_detail.*

class DetailPlayerActivity : AppCompatActivity() {

    // ---------------------------------------------------------------------------------------------
    companion object {
        const val STRING_EXTRA_PLAYER = "string_extra_player"
    }
    // ---------------------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        // ---------------------------------------------------------------------------------------------
        if (intent.hasExtra(DetailPlayerActivity.STRING_EXTRA_PLAYER)) {
            // -------------------------------------------------------------------------------------
            val dataPlayer = intent.getParcelableExtra<Player>(DetailPlayerActivity.STRING_EXTRA_PLAYER)
            val playerID = dataPlayer.playerId
            val playerName = dataPlayer.playerName
            val playerPoster = dataPlayer.playerPoster
            val playerHeight = dataPlayer.playerHeight
            val playerWeight = dataPlayer.playerWeight
            val playerPosition = dataPlayer.playerPosition
            val playerDesc = dataPlayer.playerDes

            textview_detail_player_height.text = playerHeight
            textview_detail_player_weight.text = playerWeight
            textview_detail_player_position.text = playerPosition
            textview_detail_player_desc.text = playerDesc
            Picasso.get().load(playerPoster).into(imageview_detail_player_poster)
            supportActionBar?.title = playerName
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            // -------------------------------------------------------------------------------------
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

}