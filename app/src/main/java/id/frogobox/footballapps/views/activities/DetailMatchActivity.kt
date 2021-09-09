package id.frogobox.footballapps.views.activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.frogobox.footballapps.R
import id.frogobox.footballapps.R.drawable.ic_add_to_favorites
import id.frogobox.footballapps.R.drawable.ic_added_to_favorites
import id.frogobox.footballapps.R.id.add_to_favorite
import id.frogobox.footballapps.R.menu.menu_favorite
import id.frogobox.footballapps.helpers.coroutines.TestContextProvider
import id.frogobox.footballapps.helpers.networks.ApiRepository
import id.frogobox.footballapps.helpers.utils.formatDateToMatch
import id.frogobox.footballapps.helpers.utils.formatTimeToMatch
import id.frogobox.footballapps.helpers.utils.invisible
import id.frogobox.footballapps.helpers.utils.visible
import id.frogobox.footballapps.models.dataclass.FavoriteMatch
import id.frogobox.footballapps.models.dataclass.Match
import id.frogobox.footballapps.models.dataclass.Team
import id.frogobox.footballapps.presenters.DetailMatchPresenter
import id.frogobox.footballapps.presenters.FavoriteDetailMatchPresenter
import id.frogobox.footballapps.views.interfaces.DetailMatchView
import kotlinx.android.synthetic.main.activity_match_detail.*

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {

    private var matches: MutableList<Match> = mutableListOf()
    private var homeTeams: MutableList<Team> = mutableListOf()
    private var awayTeams: MutableList<Team> = mutableListOf()

    private lateinit var presenter: DetailMatchPresenter
    private lateinit var crudPresenterDetail: FavoriteDetailMatchPresenter
    private var activeNetwork: NetworkInfo? = null

    private var id: String? = null
    private var menuItem: Menu? = null

    companion object {
        const val STRING_EXTRA_MATCH = "string_extra_match"
        const val STRING_EXTRA_FAVORITE = "string_extra_fav"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Event Detail"

        val request = ApiRepository()
        val gson = Gson()

        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        activeNetwork = connectivityManager.activeNetworkInfo
        crudPresenterDetail = FavoriteDetailMatchPresenter(this, matches, swipeRefresh)

        showDetailData(request, gson)

        swipeRefresh.setOnRefreshListener {
            progressBarDetail.invisible()
            showDetailData(request, gson)
        }


    }

    private fun showDetailData(request: ApiRepository, gson: Gson) {
        val connected: Boolean = activeNetwork?.isConnected == true
        if (intent.hasExtra(STRING_EXTRA_MATCH)){

            val dataMatch = intent.getParcelableExtra<Match>(STRING_EXTRA_MATCH)!!
            val eventMatchID = dataMatch.eventId
            val teamHomeMatchID = dataMatch.homeTeamId
            val teamAwayMatchID = dataMatch.awayTeamId
            id = eventMatchID
            crudPresenterDetail.favoriteState(eventMatchID)

            if (connected) {
                presenter = DetailMatchPresenter(
                    this, request, gson,
                    TestContextProvider()
                )
                presenter.getMatchDetail(eventMatchID, teamHomeMatchID, teamAwayMatchID)
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
            }

        } else {

            val dataFavorite = intent.getParcelableExtra<FavoriteMatch>(STRING_EXTRA_FAVORITE)!!
            val eventFavoriteID = dataFavorite.eventId
            val teamHomeFavoriteID = dataFavorite.homeTeamId
            val teamAwayFavoriteID = dataFavorite.awayTeamId

            id = eventFavoriteID
            crudPresenterDetail.favoriteState(eventFavoriteID)

            if (connected) {
                presenter = DetailMatchPresenter(this, request, gson,
                    TestContextProvider()
                )
                presenter.getMatchDetail(eventFavoriteID, teamHomeFavoriteID, teamAwayFavoriteID)
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun initData() {
        for (i in matches.indices) {

            textview_detail_match_date.text = matches[i].dateMatch?.formatDateToMatch()
            textview_detail_match_time.text = matches[i].timeMatch?.formatTimeToMatch()

            Picasso.get().load(homeTeams[i].teamBadge).into(textview_detail_match_home_image)
            textview_detail_match_home_team.text = matches[i].homeTeam
            textview_detail_match_home_score.text = matches[i].homeScore
            textview_detail_match_home_formation.text = matches[i].homeFormation
            textview_detail_match_home_goals.text = matches[i].homeGoals
            textview_detail_match_home_shots.text = matches[i].homeShots
            textview_detail_match_home_goalkeeper.text = matches[i].homeGoalKeeper
            textview_detail_match_home_defense.text = matches[i].homeDefense
            textview_detail_match_home_midfield.text = matches[i].homeMidfield
            textview_detail_match_home_forward.text = matches[i].homeForward
            textview_detail_match_home_substitute.text = matches[i].homeSubstitutes

            Picasso.get().load(awayTeams[i].teamBadge).into(textview_detail_match_away_image)
            textview_detail_match_away_team.text = matches[i].awayTeam
            textview_detail_match_away_score.text = matches[i].awayScore
            textview_detail_match_away_formation.text = matches[i].awayFormation
            textview_detail_match_away_goals.text = matches[i].awayGoals
            textview_detail_match_away_shots.text = matches[i].awayShots
            textview_detail_match_away_goalkeeper.text = matches[i].awayGoalKeeper
            textview_detail_match_away_defense.text = matches[i].awayDefense
            textview_detail_match_away_midfield.text = matches[i].awayMidfield
            textview_detail_match_away_forward.text = matches[i].awayForward
            textview_detail_match_away_substitute.text = matches[i].awaySubstitutes
        }
    }

    private fun setFavorite() {
        if (crudPresenterDetail.isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(menu_favorite, menu)
        menuItem = menu
        setFavorite()
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            add_to_favorite -> {
                if (crudPresenterDetail.isFavorite) { crudPresenterDetail.removeFromFavorite(id) }
                else { crudPresenterDetail.addToFavorite() }

                crudPresenterDetail.isFavorite = !crudPresenterDetail.isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        progressBarDetail.visible()
        layout_detail_match_container.invisible()
        supportActionBar?.hide()
    }

    override fun hideLoading() {
        runOnUiThread {
            progressBarDetail.invisible()
            layout_detail_match_container.visible()
        }
    }

    override fun showData(dataMatch: List<Match>, dataHomeTeam: List<Team>, dataAwayTeam: List<Team>) {
        runOnUiThread {
            homeTeams.clear()
            awayTeams.clear()
            matches.clear()
            swipeRefresh.isRefreshing = false

            homeTeams.addAll(dataHomeTeam)
            awayTeams.addAll(dataAwayTeam)
            matches.addAll(dataMatch)

            initData()
            supportActionBar?.show()
        }
    }
}
