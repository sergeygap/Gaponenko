package com.gap.tinkoffeducation.data.mapper

import com.gap.tinkoffeducation.data.database.FeaturesDbModel
import com.gap.tinkoffeducation.data.network.model.CountryDto
import com.gap.tinkoffeducation.data.network.model.FilmsDto
import com.gap.tinkoffeducation.data.network.model.GenreDto
import com.gap.tinkoffeducation.domain.entity.Features
import com.gap.tinkoffeducation.domain.entity.Films

class Mapper {

    fun mapDtoToEntity(dto: FilmsDto): Films {
        return Films(
            dto.id,
            dto.name,
            mapYear(dto.year),
            dto.poster,
            mapGenres(dto.genres),
            mapCountries(dto.countries),
            dto.description
        )
    }

    private fun mapGenres(list: List<GenreDto>): String {
        return list.joinToString(", ") { it.genre }
    }
    private fun mapCountries(list: List<CountryDto>): String {
        return list.joinToString(", ") { it.country }
    }
    private fun mapYear(year: Int): String {
        return "($year)"
    }


    fun mapFeaturesDbModelToFavourites(db: FeaturesDbModel): Features {
        with(db){
            return Features(id, name, year, poster, genres, countries, description)
        }
    }
    fun mapEntityDbModelToDbModel(entity: Features): FeaturesDbModel {
        with(entity){
            return FeaturesDbModel(id, name, year, poster, genres, countries, description)
        }
    }

}