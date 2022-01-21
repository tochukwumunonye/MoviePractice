package com.tochukwu.moviepractice.presentation.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tochukwu.moviepractice.R
import com.tochukwu.moviepractice.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment @Inject constructor(
    var movieListAdapter: MovieAdapter,
    var viewModel: MovieViewModel? = null
) : Fragment(R.layout.fragment_movies){


     private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        setupRecyclerView()
        subscribeObservers()
        viewModel?.returnAllMoviesFromDb()

        binding.fabAddMovie.setOnClickListener {
            findNavController().navigate(

                MovieFragmentDirections.actionMovieFragmentToAddMovieFragment()
            )
        }
    }

    private fun setupRecyclerView() {
        binding.rvMovieItems.apply{
            adapter = movieListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(this)
        }
    }

    private val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(
      0, LEFT or RIGHT  ){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }
        /**
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val item = movieListAdapter.
            viewModel?.deleteMovie()
            Toast.makeText(activity, Constants.DELETED_MOVIE_FROM_DB, Toast.LENGTH_LONG).show()
        }**/
    }


    private fun subscribeObservers() {
        viewModel?.moviesFromDb?.observe(viewLifecycleOwner, {movieList->
            movieListAdapter.submitList(movieList)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}



