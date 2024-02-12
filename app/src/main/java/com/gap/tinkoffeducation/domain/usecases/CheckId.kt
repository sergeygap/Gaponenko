package com.gap.tinkoffeducation.domain.usecases

import com.gap.tinkoffeducation.domain.FilmsRepository

class CheckId(
    private val repository: FilmsRepository
) {
    suspend operator fun invoke(id: Int): Boolean {
        return repository.checkId(id)
    }
}