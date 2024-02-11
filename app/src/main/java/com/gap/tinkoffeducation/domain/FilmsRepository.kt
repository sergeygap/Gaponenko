package com.gap.tinkoffeducation.domain

import com.gap.tinkoffeducation.domain.entity.Films

interface FilmsRepository {
    suspend fun getFilmsDetails(id: Int): Films
    suspend fun getListFilms(page: Int): List<Films>

}