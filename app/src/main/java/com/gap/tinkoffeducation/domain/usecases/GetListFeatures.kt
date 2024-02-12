package com.gap.tinkoffeducation.domain.usecases

import com.gap.tinkoffeducation.domain.FilmsRepository
import com.gap.tinkoffeducation.domain.entity.Features

class GetListFeatures(
    private val repository: FilmsRepository
) {
    suspend operator fun invoke(): List<Features> {
        return repository.getListFeatures()
    }
}