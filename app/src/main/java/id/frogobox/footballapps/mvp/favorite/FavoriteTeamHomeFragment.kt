package id.frogobox.footballapps.mvp.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.frogobox.footballapps.R
import id.frogobox.footballapps.utils.PagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite_home.*

class FavoriteTeamHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = PagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(
            FavoriteTeamFragment(),
            resources.getString(R.string.tab_favorite_team)
        )
        viewpager_favorite.adapter = pagerAdapter
        tablayout_favorite.setupWithViewPager(viewpager_favorite)
    }

}
