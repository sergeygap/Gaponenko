package com.gap.tinkoffeducation.domain.usecases

import com.gap.tinkoffeducation.domain.FilmsRepository
import com.gap.tinkoffeducation.domain.entity.Films

class GetFilmsDetails(
    private val repository: FilmsRepository
) {
   suspend operator fun invoke(id: Int): Films {
        return repository.getFilmsDetails(id)
    }
}