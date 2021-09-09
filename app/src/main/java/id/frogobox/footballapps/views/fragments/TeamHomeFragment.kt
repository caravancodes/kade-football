package id.frogobox.footballapps.views.fragments

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.*
import android.widget.*
import com.google.gson.Gson
import id.frogobox.footballapps.R
import id.frogobox.footballapps.R.array.league
import id.frogobox.footballapps.helpers.coroutines.TestContextProvider
import id.frogobox.footballapps.helpers.networks.ApiRepository
import id.frogobox.footballapps.helpers.utils.invisible
import id.frogobox.footballapps.helpers.utils.visible
import id.frogobox.footballapps.models.dataclass.Team
import id.frogobox.footballapps.presenters.TeamPresenter
import id.frogobox.footballapps.views.activities.DetailTeamActivity
import id.frogobox.footballapps.views.activities.SearchTeamActivity
import id.frogobox.footballapps.views.adapters.TeamRecyclerViewAdapter
import id.frogobox.footballapps.views.interfaces.TeamView
import kotlinx.android.synthetic.main.fragment_team_home.view.*


class TeamHomeFragment : androidx.fragment.app.Fragment(), TeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var leagueNameAPI: String
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamRecyclerViewAdapter
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_team_home, container, false)

        val request = ApiRepository()
        val gson = Gson()
        val mLayoutManager = androidx.recyclerview.widget.GridLayoutManager(activity, 3)
        val divider = androidx.recyclerview.widget.DividerItemDecoration(context, mLayoutManager.orientation)
        setHasOptionsMenu(true)

        progressBar = rootView.progressBar_team
        recyclerView = rootView.recyclerView_team
        swipeRefresh = rootView.swipeRefresh_team
        spinner = rootView.spinner_team

        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val connected: Boolean = activeNetwork?.isConnected == true

        if (connected) {
            presenter = TeamPresenter(this, request, gson,TestContextProvider())
            val spinnerItems = resources.getStringArray(league)

            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, spinnerItems)
            spinner.adapter = spinnerAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    leagueNameAPI = spinner.selectedItem.toString()
                    presenter.getTeamList(leagueNameAPI)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
            
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
        }
        adapter = TeamRecyclerViewAdapter(context, teams) {
            val intent = Intent(requireContext(), DetailTeamActivity::class.java)
            intent.putExtra(DetailTeamActivity.STRING_EXTRA_TEAM, it)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = mLayoutManager
        recyclerView.addItemDecoration(divider)

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
        }}

}