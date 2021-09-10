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
import id.frogobox.footballapps.R
import id.frogobox.footballapps.R.array.league
import id.frogobox.footballapps.models.Team
import id.frogobox.footballapps.utils.TestContextProvider
import id.frogobox.footballapps.utils.invisible
import id.frogobox.footballapps.utils.visible
import kotlinx.android.synthetic.main.fragment_team_home.*
import kotlinx.coroutines.DelicateCoroutinesApi
import org.jetbrains.anko.runOnUiThread


class TeamHomeFragment : androidx.fragment.app.Fragment(), TeamCallback<Team> {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var leagueNameAPI: String
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_home, container, false)
    }

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mLayoutManager = GridLayoutManager(requireContext(), 2)
        setHasOptionsMenu(true)

        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val connected: Boolean = activeNetwork?.isConnected == true

        if (connected) {
            presenter = TeamPresenter(this, TestContextProvider())
            val spinnerItems = resources.getStringArray(league)

            val spinnerAdapter = ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                spinnerItems
            )
            spinner_team.adapter = spinnerAdapter
            spinner_team.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    leagueNameAPI = spinner_team.selectedItem.toString()
                    presenter.getTeamList(leagueNameAPI)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
        }
        adapter = TeamAdapter(teams) {
            val intent = Intent(requireContext(), TeamDetailActivity::class.java)
            intent.putExtra(TeamDetailActivity.STRING_EXTRA_TEAM, it)
            startActivity(intent)
        }

        recyclerView_team.adapter = adapter
        recyclerView_team.layoutManager = mLayoutManager

        swipeRefresh_team.setOnRefreshListener {
            progressBar_team.invisible()
            if (connected) {
                presenter.getTeamList(leagueNameAPI)
            } else {
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_search -> {
                val intent = Intent(context, TeamSearchActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        requireActivity().runOnUiThread {
            progressBar_team.visible()
        }
    }

    override fun hideLoading() {
        requireActivity().runOnUiThread {
            progressBar_team.invisible()
        }
    }

    override fun onResult(data: List<Team>) {
        requireActivity().runOnUiThread {
            adapter.notifyDataSetChanged()
            swipeRefresh_team.isRefreshing = false
            teams.clear()
            teams.addAll(data)
        }
    }

    override fun onFailed(message: String) {
        requireContext().runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

}