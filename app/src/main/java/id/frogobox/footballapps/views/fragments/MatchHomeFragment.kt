package id.frogobox.footballapps.views.fragments


import android.content.Intent
import android.os.Bundle
import android.view.*
import id.frogobox.footballapps.R
import id.frogobox.footballapps.views.activities.SearchMatchActivity
import id.frogobox.footballapps.views.adapters.FragmentViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_match_home.view.*

class MatchHomeFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_match_home, container, false)

        val pagerAdapter = FragmentViewPagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(MatchLastFragment(), resources.getString(R.string.tab_match_last))
        pagerAdapter.addFragment(MatchNextFragment(), resources.getString(R.string.tab_match_next))
        rootView.viewpager_match.adapter = pagerAdapter
        rootView.tablayout_match.setupWithViewPager(rootView.viewpager_match)
        setHasOptionsMenu(true)

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_search -> {
                val intent = Intent(context, SearchMatchActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
