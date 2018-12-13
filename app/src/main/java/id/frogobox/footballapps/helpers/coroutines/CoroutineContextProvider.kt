package id.frogobox.footballapps.helpers.coroutines

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * FootBallMatchSchedule
 * Copyright (C) 26/11/2018.
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
open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { Dispatchers.Main }
}