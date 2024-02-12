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

class FavouriteAdapter(private val context: Context) :
    ListAdapter<Films, FilmsViewHolder>(FilmsDiffCallback) {

    var onFilmsClickListener: OnFilmsClickListener? = null
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
        val film = getItem(position)
        setUpItem(holder, film)
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
                tvType.text = genres.split(",")[0].replaceFirstChar { it.uppercase() }
                    .trimEnd()
                tvYear.text = year
                Glide.with(context)
                    .load(poster)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
                    .into(ivPoster)
                root.setOnClickListener {
                    onFilmsClickListener?.let {
                        it.onFilmsClick(id)
                    }
                }
                root.setOnLongClickListener {

                    true
                }
            }
        }
    }


    interface OnFilmsClickListener {
        fun onFilmsClick(id: Int)
    }


    interface OnReachEndListener {
        fun onReachEnd()
    }
}