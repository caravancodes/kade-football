package id.frogobox.footballapps

import id.frogobox.footballapps.helpers.networks.ApiRepository
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * FootBallApps
 * Copyright (C) 06/12/2018.
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
class ApiRepositoryTest {

    private val apiRepository = mock(ApiRepository::class.java)

    @Test
    fun testDoRequestEventNextLeague() {
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }

    @Test
    fun testDoRequestEventLastLeague() {
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }

    @Test
    fun testDoRequestEventDetail() {
        val url = "https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id=441613"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }

    @Test
    fun testDoRequestTeamDetail() {
        val url = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=441613"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }

}