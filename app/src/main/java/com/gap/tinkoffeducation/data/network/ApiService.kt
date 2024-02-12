package com.gap.tinkoffeducation.data.network

import com.gap.tinkoffeducation.data.network.model.FilmsDto
import com.gap.tinkoffeducation.data.network.model.FilmsListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/api/v2.2/films/{filmId}")
    suspend fun getFilmsDetails(
        @Path(FILMID) id: Int
    ): Response<FilmsDto>

    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getListFilms(
        @Query(PAGE) page: Int
    ): Response<FilmsListDto>


    companion object {
        private const val PAGE = "page"
        private const val FILMID = "filmId"
    }
}