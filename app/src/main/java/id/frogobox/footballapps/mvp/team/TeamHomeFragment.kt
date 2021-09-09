package id.frogobox.footballapps.mvp.team

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import id.frogobox.footballapps.R
import id.frogobox.footballapps.R.array.league
import id.frogobox.footballapps.models.Team
import id.frogobox.footballapps.mvp.detail.DetailTeamActivity
import id.frogobox.footballapps.mvp.search.SearchTeamActivity
import id.frogobox.footballapps.sources.ApiRepository
import id.frogobox.footballapps.utils.TestContextProvider
import id.frogobox.footballapps.utils.invisible
import id.frogobox.footballapps.utils.visible
import kotlinx.android.synthetic.main.fragment_team_home.view.*
import kotlinx.coroutines.DelicateCoroutinesApi


class TeamHomeFragment : androidx.fragment.app.Fragment(), TeamCallback {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var leagueNameAPI: String
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamViewAdapter
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    private lateinit var spinner: Spinner

    @DelicateCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_team_home, container, false)

        val request = ApiRepository()
        val gson = Gson()
        val mLayoutManager = GridLayoutManager(requireContext(), 2)
        setHasOptionsMenu(true)

        progressBar = rootView.progressBar_team
        recyclerView = rootView.recyclerView_team
        swipeRefresh = rootView.swipeRefresh_team
        spinner = rootView.spinner_team

        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val connected: Boolean = activeNetwork?.isConnected == true

        if (connected) {
            presenter = TeamPresenter(this, request, gson, TestContextProvider())
            val spinnerItems = resources.getStringArray(league)

            val spinnerAdapter = ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                spinnerItems
            )
            spinner.adapter = spinnerAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    leagueNameAPI = spinner.selectedItem.toString()
                    presenter.getTeamList(leagueNameAPI)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
        }
        adapter = TeamViewAdapter(context, teams) {
            val intent = Intent(requireContext(), DetailTeamActivity::class.java)
            intent.putExtra(DetailTeamActivity.STRING_EXTRA_TEAM, it)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = mLayoutManager

        swipeRefresh.setOnRefreshListener {
            progressBar.invisible()
            if (connected) {
                presenter.getTeamList(leagueNameAPI)
            } else {
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
            }
        }

        return rootView
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_search -> {
                val intent = Intent(context, SearchTeamActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        requireActivity().runOnUiThread {
            progressBar.visible()
        }
    }

    override fun hideLoading() {
        requireActivity().runOnUiThread {
            progressBar.invisible()
        }
    }

    override fun showData(dataTeam: List<Team>) {
        requireActivity().runOnUiThread {
            adapter.notifyDataSetChanged()
            swipeRefresh.isRefreshing = false
            teams.clear()
            teams.addAll(dataTeam)
        }
    }

}