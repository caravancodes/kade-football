package id.frogobox.footballapps.presenters

import android.os.Bundle
import androidx.fragment.app.Fragment
import id.frogobox.footballapps.views.interfaces.HandlerMainView
import id.frogobox.footballapps.views.interfaces.MainView


/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * FootBallApps
 * Copyright (C) 13/12/2018.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Line     : bullbee117
 * Phone    : 081357108568
 * Majors   : D3 Teknik Informatika 2016
 * Campus   : Telkom University
 * -----------------------------------------
 * id.amirisback.frogobox
 */
class MainPresenter : HandlerMainView<MainView> {
    private var mView: MainView? = null

    override fun onAttach(view: MainView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun showFragment(mFragment: androidx.fragment.app.Fragment, savedInstanceState: Bundle?) {
        mView?.onShowFragment(mFragment, savedInstanceState)
    }
}