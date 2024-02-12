package com.gap.tinkoffeducation.data.repository

import android.app.Application
import android.util.Log
import com.gap.tinkoffeducation.data.database.AppDatabase
import com.gap.tinkoffeducation.data.mapper.Mapper
import com.gap.tinkoffeducation.data.network.ApiFactory
import com.gap.tinkoffeducation.domain.FilmsRepository
import com.gap.tinkoffeducation.domain.entity.Features
import com.gap.tinkoffeducation.domain.entity.Films
import retrofit2.HttpException
import java.io.IOException

class FilmsRepositoryImpl(
    application: Application
) : FilmsRepository {
    private val featuresDao = AppDatabase.getInstance(application).featuresDao()
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

        return Films(ERROR_ID, "", "", "", "", "", "")
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

    override suspend fun getListFeatures(): List<Features> {
        return featuresDao.getListFeatures().map {
            mapper.mapFeaturesDbModelToFavourites(it)
        }
    }

    override suspend fun getFeaturesById(id: Int): Features {
        return mapper.mapFeaturesDbModelToFavourites(featuresDao.getFeaturesById(id))
    }

    override suspend fun addFeatures(features: Features) {
        if (features.description == null) {
            Log.d(TAG, "addFeatures: description == null")
            val film = getFilmsDetails(features.id)
            if (film.id != ERROR_ID) {
                featuresDao.addFeatures(
                    mapper.mapEntityDbModelToDbModel(
                        Features(
                            features.id,
                            features.name,
                            features.year,
                            features.poster,
                            features.genres,
                            features.countries,
                            film.description
                        )
                    )
                )
            }
        } else
            featuresDao.addFeatures(mapper.mapEntityDbModelToDbModel(features))
    }

    override suspend fun deleteFeatures(features: Features) {
        featuresDao.deleteFeatures(mapper.mapEntityDbModelToDbModel(features))
    }

    override suspend fun checkId(id: Int): Boolean {
        return featuresDao.checkId(id)
    }


    companion object {
        private const val ERROR_ID = -2024
        const val TAG = "addFilm"
    }
}

