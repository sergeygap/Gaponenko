package com.gap.tinkoffeducation.data.mapper

import com.gap.tinkoffeducation.data.network.model.CountryDto
import com.gap.tinkoffeducation.data.network.model.FilmsDto
import com.gap.tinkoffeducation.data.network.model.GenreDto
import com.gap.tinkoffeducation.domain.entity.Films

class Mapper {

    fun mapDtoToEntity(dto: FilmsDto): Films {
        return Films(
            dto.id,
            dto.name,
            dto.year,
            dto.poster,
            mapGenresAndCountries(dto.genres),
            mapGenresAndCountries(dto.countries),
            dto.description
        )
    }

    private fun mapGenresAndCountries(list: List<GenreDto>): String {
        return list.joinToString(", ") { it.genre }
    }
    private fun mapGenresAndCountries(list: List<CountryDto>): String {
        return list.joinToString(", ") { it.country }
    }

}