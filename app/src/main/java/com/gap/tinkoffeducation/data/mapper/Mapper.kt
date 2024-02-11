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
            mapYear(dto.year),
            dto.poster,
            mapGenres(dto.genres),
            mapCountries(dto.countries),
            dto.description
        )
    }

    private fun mapGenres(list: List<GenreDto>): String {
        return list.first().genre.replaceFirstChar {it.uppercase()}.trimEnd()
    }
    private fun mapCountries(list: List<CountryDto>): String {
        return list.joinToString(", ") { it.country }
    }
    private fun mapYear(year: Int): String {
        return "($year)"
    }

}