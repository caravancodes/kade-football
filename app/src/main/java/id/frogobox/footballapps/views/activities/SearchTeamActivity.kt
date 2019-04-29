package id.frogobox.footballapps.views.activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.google.gson.Gson
import id.frogobox.footballapps.R
import id.frogobox.footballapps.helpers.coroutines.TestContextProvider
import id.frogobox.footballapps.helpers.networks.ApiRepository
import id.frogobox.footballapps.helpers.utils.invisible
import id.frogobox.footballapps.helpers.utils.visible
import id.frogobox.footballapps.models.dataclass.Team
import id.frogobox.footballapps.presenters.SearchTeamPresenter
import id.frogobox.footballapps.views.adapters.TeamRecyclerViewAdapter
import id.frogobox.footballapps.views.interfaces.SearchTeamView
import kotlinx.android.synthetic.main.activity_team_search.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class SearchTeamActivity : AppCompatActivity(), SearchTeamView {

    private var team: MutableList<Team> = mutableListOf()
    private lateinit var presenter: SearchTeamPresenter
    private lateinit var adapter: TeamRecyclerViewAdapter
    // -----------------------------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_search)
        // -------------------------------------------------------------------------------------------------------------
        recyclerview_searchteam.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 3)
        textview_searchteam_null.invisible()
        // -------------------------------------------------------------------------------------------------------------
        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchTeamPresenter(this, request, gson, TestContextProvider())
        // -------------------------------------------------------------------------------------------------------------
        edittext_searchteam.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (edittext_searchteam.text.isNotEmpty()){
                    swiperefresh_searchteam.isRefreshing = true
                    presenter.getSearchTeamsList(edittext_searchteam.text.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        adapter = TeamRecyclerViewAdapter(this, team){
            startActivity<DetailTeamActivity>(DetailTeamActivity.STRING_EXTRA_TEAM to it)
        }
        recyclerview_searchteam.adapter = adapter

        // -------------------------------------------------------------------------------------------------------------
        cardview_searchteam_back.onClick {
            onBackPressed()
        }
        // -------------------------------------------------------------------------------------------------------------
        cardview_searchteam_remove.onClick {
            edittext_searchteam.text.clear()
            recyclerview_searchteam.invisible()
            textview_searchteam_null.visible()
        }
        // -------------------------------------------------------------------------------------------------------------
        swiperefresh_searchteam.onRefresh {
            swiperefresh_searchteam.isRefreshing = false
        }
        // -------------------------------------------------------------------------------------------------------------
        checkConnection()
    }

    private fun checkConnection() {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true
        if (!isConnected){
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
            swiperefresh_searchteam.isRefreshing = false
        }
    }

    override fun showSearchTeamList(data: List<Team>) {
        runOnUiThread {
            team.clear()
            team.addAll(data)
            adapter.notifyDataSetChanged()
            recyclerview_searchteam.visible()
            textview_searchteam_null.invisible()
            swiperefresh_searchteam.isRefreshing = false
        }
    }

    override fun showSearchTeamListNull() {
        runOnUiThread {
            recyclerview_searchteam.invisible()
            textview_searchteam_null.visible()
            swiperefresh_searchteam.isRefreshing = false
        }
    }
}