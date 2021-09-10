package id.frogobox.footballapps.mvp.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.frogobox.footballapps.R
import id.frogobox.footballapps.mvp.favorite.FavoriteTeamHomeFragment
import id.frogobox.footballapps.mvp.team.TeamHomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainCallback {
    var presenter: MainHandlerPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0f
        initPresenter()
        onAttachView(savedInstanceState)

    }

    private fun bottomNavigationHandler(savedInstanceState: Bundle?) {
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_team -> {
                    presenter?.showFragment(TeamHomeFragment(), savedInstanceState)
                }
                R.id.navigation_favorite -> {
                    presenter?.showFragment(FavoriteTeamHomeFragment(), savedInstanceState)
                }
            }
            true
        }
        navigation.selectedItemId = R.id.navigation_team
    }

    private fun initPresenter() {
        presenter = MainHandlerPresenter()
    }

    override fun onShowFragment(
        mFragment: androidx.fragment.app.Fragment,
        savedInstanceState: Bundle?
    ) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, mFragment, mFragment::class.java.simpleName)
                .commit()
        }
    }

    override fun onAttachView(savedInstanceState: Bundle?) {
        presenter?.onAttach(this)
        bottomNavigationHandler(savedInstanceState)
    }

    override fun onDetachView() {
        presenter?.onDetach()
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

}