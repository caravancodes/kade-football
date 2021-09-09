package id.frogobox.footballapps.mvp.favorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.frogobox.footballapps.R
import id.frogobox.footballapps.utils.PagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite_home.view.*

class FavoriteHomeFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_favorite_home, container, false)

        val pagerAdapter = PagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(FavoriteTeamFragment(), resources.getString(R.string.tab_favorite_team))
        rootView.viewpager_favorite.adapter = pagerAdapter
        rootView.tablayout_favorite.setupWithViewPager(rootView.viewpager_favorite)

        return rootView
    }

}
