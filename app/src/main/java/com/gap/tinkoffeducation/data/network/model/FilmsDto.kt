package com.gap.tinkoffeducation.data.network.model

import com.google.gson.annotations.SerializedName

data class FilmsDto(
    @SerializedName("filmId")
    val id: Int,
    @SerializedName("nameRu")
    val name: String,
    val year: Int,
    @SerializedName("posterUrl")
    val poster: String,
    val genres: List<GenreDto>,
    val countries: List<CountryDto>,
    val description: String? = null
)