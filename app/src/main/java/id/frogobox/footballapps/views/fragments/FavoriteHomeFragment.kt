package id.frogobox.footballapps.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.frogobox.footballapps.R
import id.frogobox.footballapps.views.adapters.FragmentViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite_home.view.*

class FavoriteHomeFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_favorite_home, container, false)
        // -----------------------------------------------------------------------------------------
        val pagerAdapter = FragmentViewPagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(FavoriteMatchFragment(), resources.getString(R.string.tab_favorite_match))
        pagerAdapter.addFragment(FavoriteTeamFragment(), resources.getString(R.string.tab_favorite_team))
        rootView.viewpager_favorite.adapter = pagerAdapter
        rootView.tablayout_favorite.setupWithViewPager(rootView.viewpager_favorite)
        // -----------------------------------------------------------------------------------------
        return rootView
    }

}
