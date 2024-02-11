package com.gap.tinkoffeducation.domain.usecases

import com.gap.tinkoffeducation.domain.FilmsRepository
import com.gap.tinkoffeducation.domain.entity.Films

class GetListFilms(
    private val repository: FilmsRepository
) {
    suspend operator fun invoke(page: Int): List<Films> {
        return repository.getListFilms(page)
    }
}