package com.gap.tinkoffeducation.domain.entity

data class Films(
    val id: Int,
    val name: String,
    val year: Int,
    val poster: String,
    val genres: String,
    val countries: String,
    val description: String?
)
