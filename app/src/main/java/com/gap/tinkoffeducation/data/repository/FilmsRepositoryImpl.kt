package com.gap.tinkoffeducation.data.repository

import android.app.Application
import com.gap.tinkoffeducation.data.mapper.Mapper
import com.gap.tinkoffeducation.data.network.ApiFactory
import com.gap.tinkoffeducation.domain.FilmsRepository
import com.gap.tinkoffeducation.domain.entity.Films
import android.util.Log
import retrofit2.HttpException
import java.io.IOException

class FilmsRepositoryImpl(
    application: Application
) : FilmsRepository {
    private val apiService = ApiFactory.apiService
    private val mapper = Mapper()

    override suspend fun getFilmsDetails(id: Int): Films {
        try {
            val response = apiService.getFilmsDetails(id)

            if (response.isSuccessful) {
                val filmsDetailsDto = response.body()
                if (filmsDetailsDto != null) {
                    return mapper.mapDtoToEntity(filmsDetailsDto)
                } else {
                    Log.e("FilmsRepository", "Empty response body")
                }
            } else {
                Log.e("FilmsRepository", "HTTP error: ${response.code()} ${response.message()}")
            }
        } catch (e: IOException) {
            Log.e("FilmsRepository", "IOException: ${e.message}", e)
        } catch (e: HttpException) {
            Log.e("FilmsRepository", "HttpException: ${e.code()}, ${e.message()}", e)
        } catch (e: Exception) {
            Log.e("FilmsRepository", "Error: ${e.message}", e)
        }

        return Films(null, "", "", "", "", "", "")
    }

    override suspend fun getListFilms(page: Int): List<Films> {
        try {
            val response = apiService.getListFilms(page)

            if (response.isSuccessful) {
                val filmsListDto = response.body()
                if (filmsListDto != null) {
                    return filmsListDto.films.map { mapper.mapDtoToEntity(it) }
                } else {
                    Log.e("FilmsRepository", "Empty response body")
                }
            } else {
                Log.e("FilmsRepository", "HTTP error: ${response.code()} ${response.message()}")
            }
        } catch (e: IOException) {
            Log.e("FilmsRepository", "IOException: ${e.message}", e)
        } catch (e: HttpException) {
            Log.e("FilmsRepository", "HttpException: ${e.code()}, ${e.message()}", e)
        } catch (e: Exception) {
            Log.e("FilmsRepository", "Error: ${e.message}", e)
        }

        return emptyList()
    }
}

