package id.frogobox.footballapps.mvp.team

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import id.frogobox.footballapps.R
import id.frogobox.footballapps.R.drawable.ic_add_to_favorites
import id.frogobox.footballapps.R.drawable.ic_added_to_favorites
import id.frogobox.footballapps.models.FavoriteTeam
import id.frogobox.footballapps.models.Team
import id.frogobox.footballapps.mvp.favorite.FavoriteTeamDetailPresenter
import id.frogobox.footballapps.utils.BundleHelper.teamIdHelper
import id.frogobox.footballapps.utils.BundleHelper.teamOverviewHelper
import id.frogobox.footballapps.utils.PagerAdapter
import id.frogobox.footballapps.utils.TestContextProvider
import id.frogobox.footballapps.utils.invisible
import id.frogobox.footballapps.utils.visible
import kotlinx.android.synthetic.main.activity_team_detail.*

class TeamDetailActivity : AppCompatActivity(), TeamCallback<Team> {

    private var teams: MutableList<Team> = mutableListOf()

    private lateinit var presenter: TeamPresenter
    private lateinit var crudPresenterDetail: FavoriteTeamDetailPresenter
    private var activeNetwork: NetworkInfo? = null
    private var id: String? = null
    private var menuItem: Menu? = null


    companion object {
        const val STRING_EXTRA_TEAM = "string_extra_team"
        const val STRING_EXTRA_FAVORITE = "string_extra_fav"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        val pagerAdapter = PagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(
            TeamOverviewFragment(),
            resources.getString(R.string.tab_team_overview)
        )
        viewpager_team.adapter = pagerAdapter
        tablayout_team.setupWithViewPager(viewpager_team)

        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        activeNetwork = connectivityManager.activeNetworkInfo
        crudPresenterDetail = FavoriteTeamDetailPresenter(this, teams, swipeRefresh_teamDetail)
        showDetailData()

        swipeRefresh_teamDetail.setOnRefreshListener {
            progressBar_teamDetail.invisible()
            showDetailData()
        }

    }

    private fun showDetailData() {
        val connected: Boolean = activeNetwork?.isConnected == true
        if (intent.hasExtra(STRING_EXTRA_TEAM)) {

            val dataTeam = intent.getParcelableExtra<Team>(STRING_EXTRA_TEAM)!!
            val teamID = dataTeam.teamId
            val teamOverView = dataTeam.teamDescription
            teamIdHelper = teamID.toString()
            teamOverviewHelper = teamOverView.toString()
            id = teamID
            crudPresenterDetail.favoriteState(teamID)

            if (connected) {
                presenter = TeamPresenter(this, TestContextProvider())
                presenter.getTeamDetailById(teamID)
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
            }

        } else if (intent.hasExtra(STRING_EXTRA_FAVORITE)) {

            val dataTeamFavorite = intent.getParcelableExtra<FavoriteTeam>(STRING_EXTRA_FAVORITE)!!
            val teamFavoriteID = dataTeamFavorite.teamId
            val teamFavoriteOverView = dataTeamFavorite.teamDescription

            teamIdHelper = teamFavoriteID.toString()
            teamOverviewHelper = teamFavoriteOverView.toString()

            id = teamFavoriteID
            crudPresenterDetail.favoriteState(teamFavoriteID)

            if (connected) {
                presenter = TeamPresenter(this, TestContextProvider())
                presenter.getTeamDetailById(teamFavoriteID)
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
            }

        }
    }


    private fun initData() {
        for (i in teams.indices) {
            textview_detail_team_name.text = teams[i].teamName
            textview_detail_team_year.text = teams[i].teamFormedYear
            textview_detail_team_stadium.text = teams[i].teamStadium
            Picasso.get().load(teams[i].teamBadge).into(imageview_detail_team_badge)

        }
    }

    private fun setFavorite() {
        if (crudPresenterDetail.isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
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

            R.id.add_to_favorite -> {
                if (crudPresenterDetail.isFavorite) {
                    crudPresenterDetail.removeFromFavorite(id)
                } else {
                    crudPresenterDetail.addToFavorite()
                }

                crudPresenterDetail.isFavorite = !crudPresenterDetail.isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        progressBar_teamDetail.visible()
        supportActionBar?.hide()
    }

    override fun hideLoading() {
        runOnUiThread {
            progressBar_teamDetail.invisible()
        }
    }

    override fun onResult(data: List<Team>) {
        runOnUiThread {
            swipeRefresh_teamDetail.isRefreshing = false
            teams.clear()
            teams.addAll(data)
            initData()
            supportActionBar?.show()
        }
    }

    override fun onFailed(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
