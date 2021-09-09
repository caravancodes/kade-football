package id.frogobox.footballapps.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import id.frogobox.footballapps.R
import id.frogobox.footballapps.models.dataclass.FavoriteMatch
import id.frogobox.footballapps.presenters.FavoriteListMatchPresenter
import id.frogobox.footballapps.views.activities.DetailMatchActivity
import id.frogobox.footballapps.views.adapters.FavoriteMatchRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_favorite_match.view.*

class FavoriteMatchFragment : androidx.fragment.app.Fragment() {

    private var favorites: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var presenter: FavoriteListMatchPresenter
    private lateinit var adapter: FavoriteMatchRecyclerViewAdapter
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_favorite_match, container, false)
        val mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        val divider = androidx.recyclerview.widget.DividerItemDecoration(context, mLayoutManager.orientation)

        progressBar = rootView.progressBar_FavMatch
        recyclerView = rootView.recyclerView_FavMatch
        swipeRefresh = rootView.swipeRefresh_FavMatch

        adapter = FavoriteMatchRecyclerViewAdapter(context, favorites){
            val intent = Intent(requireContext(), DetailMatchActivity::class.java)
            intent.putExtra(DetailMatchActivity.STRING_EXTRA_FAVORITE, it)
            startActivity(intent)
        }

        presenter = FavoriteListMatchPresenter(context, favorites, progressBar,swipeRefresh, adapter)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = mLayoutManager
        recyclerView.addItemDecoration(divider)

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