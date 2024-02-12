package com.gap.tinkoffeducation.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.gap.tinkoffeducation.domain.entity.Films

object FilmsDiffCallback: DiffUtil.ItemCallback<Films>() {
    override fun areItemsTheSame(oldItem: Films, newItem: Films): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Films, newItem: Films): Boolean {
        return oldItem == newItem
    }
}
