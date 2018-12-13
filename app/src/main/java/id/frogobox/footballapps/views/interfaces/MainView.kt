package id.frogobox.footballapps.views.interfaces

import android.os.Bundle
import android.support.v4.app.Fragment

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
interface MainView : FragmentView {
    fun onShowFragment(mFragment: Fragment, savedInstanceState: Bundle?)
}