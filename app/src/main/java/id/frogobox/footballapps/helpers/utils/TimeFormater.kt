package id.frogobox.footballapps.helpers.utils

import java.text.SimpleDateFormat
import java.util.*

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
private var tz: TimeZone = TimeZone.getTimeZone("WIB")

fun String?.formatTimeToMatch(
    inputFormat: String = "HH:mm:ss",
    outputFormat: String? = "HH:mm"): String {

    val timeFormat = SimpleDateFormat(inputFormat, Locale.ENGLISH)
    timeFormat.timeZone = tz
    val time = timeFormat.parse(this)

    val returnFormat = SimpleDateFormat(outputFormat, Locale.ENGLISH)
    return returnFormat.format(time)
}

fun Date?.formatDateToMatch(): String? {
    return SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(this)
}