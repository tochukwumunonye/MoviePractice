package com.tochukwu.moviepractice.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.tochukwu.moviepractice.databinding.ItemMovieBinding
import com.tochukwu.moviepractice.domain.model.Movie
import javax.inject.Inject

class MovieAdapter @Inject constructor(
    private  val glide: RequestManager
) : androidx.recyclerview.widget.ListAdapter<Movie, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        return MovieViewHolder.from(parent, glide)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }




    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val requestManager: RequestManager
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieItem: Movie) {
            binding.run {
                requestManager.load(movieItem.poster).transition(withCrossFade())
                    .into(ivMoviePoster)
                tvTitle.text = movieItem.title
                tvYear.text = movieItem.year
            }
        }

        companion object {
            fun from(parent: ViewGroup, glide: RequestManager): MovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
                return MovieViewHolder(binding, glide)
            }
        }
    }




    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> =
            object : DiffUtil.ItemCallback<Movie>() {
                override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                    oldItem.title == newItem.title
            }
    }
}

