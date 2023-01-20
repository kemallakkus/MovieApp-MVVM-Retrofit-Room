package com.kemalakkus.movieapp_mvvm_retrofit_room.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kemalakkus.movieapp_mvvm_retrofit_room.R
import com.kemalakkus.movieapp_mvvm_retrofit_room.adapter.SavedMovieAdepter
import com.kemalakkus.movieapp_mvvm_retrofit_room.databinding.FragmentFavoriteBinding
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Genre
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Movie
import com.kemalakkus.movieapp_mvvm_retrofit_room.viewmodel.MovieViewModels


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: MovieViewModels
    private lateinit var movie: Movie
    private val savedAdepter= SavedMovieAdepter()
    var genrelist: List<Genre>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieViewModels::class.java)
        setAdepter()
        observeSavedMovie()
        remoteSavedMeals(view)

        savedAdepter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("movie",it)
            }
            findNavController().navigate(
                R.id.action_favoriteFragment_to_movieDetailFragment,
                bundle
            )
        }
    }

    private fun observeSavedMovie() {
        viewModel.readMovies().observe(viewLifecycleOwner, Observer {
            it?.let { movies ->
                savedAdepter.differ.submitList(movies)
            }
        })
    }

    private fun setAdepter() {
        binding.savedRec.apply {
            adapter=savedAdepter
            layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }
    }

    private fun remoteSavedMeals(view: View) {
        val itemTouchHelperCallBack=object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position= viewHolder.adapterPosition
                val movie =savedAdepter.differ.currentList.get(position)
                viewModel.delete(movie)
                Snackbar.make(view,"Deleted article successfully", Snackbar.LENGTH_SHORT).apply {
                    setAction("undo"){
                        viewModel.insertMovie(movie)
                    }
                    show()
                }
            }

        }
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.savedRec)
        }
    }
}