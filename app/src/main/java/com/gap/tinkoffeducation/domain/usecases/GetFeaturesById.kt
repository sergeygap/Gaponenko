package com.gap.tinkoffeducation.domain.usecases

import com.gap.tinkoffeducation.domain.FilmsRepository
import com.gap.tinkoffeducation.domain.entity.Features

class GetFeaturesById(
    private val repository: FilmsRepository
) {
    suspend operator fun invoke(id: Long): Features {
        return repository.getFeaturesById(id)
    }
}