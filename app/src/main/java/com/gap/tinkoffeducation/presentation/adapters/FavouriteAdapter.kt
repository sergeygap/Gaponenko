package com.gap.tinkoffeducation.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.gap.tinkoffeducation.databinding.ItemFilmCardBinding
import com.gap.tinkoffeducation.domain.entity.Films
import com.squareup.picasso.Picasso

class FavouriteAdapter(private val context: Context) :
    ListAdapter<Films, FilmsViewHolder>(FilmsDiffCallback) {

    var onNewsClickListener: OnNewsClickListener? = null
    var onReachEndListener: OnReachEndListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val binding = ItemFilmCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FilmsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        val news = getItem(position)
        setUpItem(holder, news)
        pagination(position)
    }

    private fun pagination(position: Int) {
        if (position == currentList.size - 5 && onReachEndListener != null) {
            onReachEndListener?.let {
                it.onReachEnd()
            }
        }
    }

    private fun setUpItem(
        holder: FilmsViewHolder,
        film: Films
    ) {
        with(holder.binding) {
            with(film) {
                tvTitle.text = name
                tvType.text = genres
                tvYear.text = year
                Glide.with(context)
                    .load(poster)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
                    .into(ivPoster)
//                Picasso.get().load(poster).into(ivPoster)
                root.setOnClickListener {
                    onNewsClickListener?.let {
                        it.onNewsClick(id ?: 0)
                    }
                }
            }
        }
    }

    interface OnNewsClickListener {
        fun onNewsClick(id: Int)
    }

    interface OnReachEndListener {
        fun onReachEnd()
    }
}