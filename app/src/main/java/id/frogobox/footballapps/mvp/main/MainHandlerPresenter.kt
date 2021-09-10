package id.frogobox.footballapps.mvp.main

import android.os.Bundle


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
class MainHandlerPresenter : MainHandlerCallback<MainCallback> {
    private var mCallback: MainCallback? = null

    override fun onAttach(callback: MainCallback) {
        mCallback = callback
    }

    override fun onDetach() {
        mCallback = null
    }

    fun showFragment(mFragment: androidx.fragment.app.Fragment, savedInstanceState: Bundle?) {
        mCallback?.onShowFragment(mFragment, savedInstanceState)
    }
}