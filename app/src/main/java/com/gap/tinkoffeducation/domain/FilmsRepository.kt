package com.gap.tinkoffeducation.domain

import com.gap.tinkoffeducation.domain.entity.Features
import com.gap.tinkoffeducation.domain.entity.Films

interface FilmsRepository {
    suspend fun getFilmsDetails(id: Int): Films
    suspend fun getListFilms(page: Int): List<Films>

    suspend fun getListFeatures(): List<Features>
    suspend fun getFeaturesById(id: Int): Features
    suspend fun addFeatures(features: Features)
    suspend fun deleteFeatures(features: Features)
    suspend fun checkId(id: Int): Boolean
}