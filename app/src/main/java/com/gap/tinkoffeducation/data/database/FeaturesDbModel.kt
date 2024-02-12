package com.gap.tinkoffeducation.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "features")
data class FeaturesDbModel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val year: String,
    val poster: String,
    val genres: String,
    val countries: String,
    val description: String?
)
