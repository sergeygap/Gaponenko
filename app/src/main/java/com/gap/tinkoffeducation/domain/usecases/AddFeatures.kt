package com.gap.tinkoffeducation.domain.usecases

import com.gap.tinkoffeducation.domain.FilmsRepository
import com.gap.tinkoffeducation.domain.entity.Features

class AddFeatures(
    private val repository: FilmsRepository
) {
    suspend operator fun invoke(features: Features) {
        return repository.addFeatures(features)
    }
}