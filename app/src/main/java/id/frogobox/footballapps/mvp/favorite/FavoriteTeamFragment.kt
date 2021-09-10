package id.frogobox.footballapps.mvp.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment

import id.frogobox.footballapps.R
import id.frogobox.footballapps.models.FavoriteTeam
import id.frogobox.footballapps.mvp.team.TeamDetailActivity
import kotlinx.android.synthetic.main.fragment_favorite_team.view.*

class FavoriteTeamFragment : Fragment() {

    private var favorites: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter: FavoriteTeamAdapter
    private lateinit var presenter: FavoriteTeamPresenter
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_favorite_team, container, false)
        val mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)

        progressBar = rootView.progressBar_teamFav
        recyclerView = rootView.recyclerView_teamFav
        swipeRefresh = rootView.swipeRefresh_teamFav

        adapter = FavoriteTeamAdapter(favorites) {
            val intent = Intent(requireContext(), TeamDetailActivity::class.java)
            intent.putExtra(TeamDetailActivity.STRING_EXTRA_FAVORITE, it)
            startActivity(intent)
        }

        presenter = FavoriteTeamPresenter(context, favorites, progressBar, swipeRefresh, adapter)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = mLayoutManager

        swipeRefresh.setOnRefreshListener {
            presenter.showFavorite()
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        presenter.showFavorite()
    }

}