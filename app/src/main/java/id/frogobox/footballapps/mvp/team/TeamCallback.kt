package id.frogobox.footballapps.mvp.team

/*
 * Created by faisalamir on 10/09/21
 * caravan-kade-football
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * -----------------------------------------
 * Copyright (C) 2021 FrogoBox Inc.      
 * All rights reserved
 *
 */
interface TeamCallback<T> {
    fun onResult(data: List<T>)
    fun onFailed(message: String)
    fun showLoading()
    fun hideLoading()
}