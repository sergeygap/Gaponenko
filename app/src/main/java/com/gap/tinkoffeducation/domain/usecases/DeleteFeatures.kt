package com.gap.tinkoffeducation.domain.usecases

import com.gap.tinkoffeducation.domain.FilmsRepository
import com.gap.tinkoffeducation.domain.entity.Features

class DeleteFeatures(
    private val repository: FilmsRepository
) {
    suspend operator fun invoke(features: Features) {
       repository.deleteFeatures(features)
    }
}