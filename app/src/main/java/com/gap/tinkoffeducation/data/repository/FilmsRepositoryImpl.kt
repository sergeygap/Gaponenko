package com.gap.tinkoffeducation.data.repository

import android.app.Application
import com.gap.tinkoffeducation.data.mapper.Mapper
import com.gap.tinkoffeducation.data.network.ApiFactory
import com.gap.tinkoffeducation.domain.FilmsRepository
import com.gap.tinkoffeducation.domain.entity.Films

class FilmsRepositoryImpl(
    application: Application
): FilmsRepository {
    private val apiService = ApiFactory.apiService
    private val mapper = Mapper()
    override suspend fun getFilmsDetails(id: Int): Films {
        return mapper.mapDtoToEntity(apiService.getFilmsDetails(id))
    }

    override suspend fun getListFilms(page: Int): List<Films> {
        return apiService.getListFilms(page).films.map {
            mapper.mapDtoToEntity(it)
        }
    }
}