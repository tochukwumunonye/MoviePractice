package com.tochukwu.moviepractice.presentation.addMovies


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.tochukwu.moviepractice.databinding.ItemAddMovieBinding
import com.tochukwu.moviepractice.databinding.ItemMovieBinding
import com.tochukwu.moviepractice.domain.model.Movie
import com.tochukwu.moviepractice.presentation.movies.MovieAdapter
import com.tochukwu.moviepractice.presentation.movies.MovieAdapter.Companion.DIFF_CALLBACK
import javax.inject.Inject


class AddMovieListAdapter @Inject constructor(
    private val glide: RequestManager
) : androidx.recyclerview.widget.ListAdapter<Movie, AddMovieListAdapter.AddMovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddMovieViewHolder {
        return AddMovieViewHolder.from(parent, glide)

    }

    override fun onBindViewHolder(holder: AddMovieViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    class AddMovieViewHolder(
        val binding: ItemAddMovieBinding,
        private val requestManager: RequestManager
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(movieItem: Movie) {
            binding.apply {
                requestManager.load(movieItem.poster).transition(DrawableTransitionOptions.withCrossFade()).into(poster)
                movieTitle.text = movieItem.title
                movieYear.text = movieItem.year
                fabAddMovie.isEnabled = true
                fabAddMovie.setOnClickListener { view->

                    navigateToMovieFragment(movieItem, view)
                    fabAddMovie.isEnabled = false
                }

            }
        }

        private fun navigateToMovieFragment(movieItem: Movie, view: View) {
            val directions = AddMovieFragmentDirections.actionAddMovieFragmentToMovieFragment()
            view.findNavController().navigate(directions)
        }


        companion object {
            fun from(parent: ViewGroup, glide: RequestManager): AddMovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAddMovieBinding.inflate(layoutInflater, parent, false)
                return AddMovieViewHolder(binding, glide)
            }
        }
    }

}




