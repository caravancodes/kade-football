package id.frogobox.footballapps.mvp.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.frogobox.footballapps.R
import id.frogobox.footballapps.utils.BundleHelper
import kotlinx.android.synthetic.main.fragment_team_overview.*
import kotlinx.android.synthetic.main.fragment_team_overview.view.*

class TeamOverviewFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textview_team_overview.text = BundleHelper.teamOverviewHelper
    }

}