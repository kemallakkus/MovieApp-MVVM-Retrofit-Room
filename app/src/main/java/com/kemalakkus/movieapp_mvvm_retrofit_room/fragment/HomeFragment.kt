package com.kemalakkus.movieapp_mvvm_retrofit_room.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kemalakkus.movieapp_mvvm_retrofit_room.R
import com.kemalakkus.movieapp_mvvm_retrofit_room.adapter.PopularMovieAdepter
import com.kemalakkus.movieapp_mvvm_retrofit_room.adapter.RecentMovieAdepter
import com.kemalakkus.movieapp_mvvm_retrofit_room.databinding.FragmentHomeBinding
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Genre
import com.kemalakkus.movieapp_mvvm_retrofit_room.viewmodel.MovieViewModels

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MovieViewModels
    private val popularAdepter = PopularMovieAdepter(arrayListOf(), arrayListOf())
    private val recentAdepter = RecentMovieAdepter(arrayListOf(), arrayListOf())
    var genrelist: List<Genre>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieViewModels::class.java)
        binding.popularRecyler.adapter = popularAdepter
        binding.popularRecyler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recentRecyler.adapter = recentAdepter
        binding.recentRecyler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewModel.apply {
            getPopularMovies()
            getRecentMovies()
            getGenres()
        }

        observePopularLiveData()
        observeGenreLiveData()
        observerRecentData()


        popularAdepter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("movie", it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_movieDetailFragment,
                bundle
            )
        }

        recentAdepter.setOnItemClickListenerRecent {
            val bundle = Bundle().apply {
                putSerializable("movie", it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_movieDetailFragment,
                bundle
            )
        }
    }


    fun observePopularLiveData() {
        viewModel.popularMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                genrelist?.let { genres ->
                    popularAdepter.updatePopularList(it.results, genres)
                }
                binding.Nested.visibility = View.VISIBLE
                binding.progressBar.visibility = View.INVISIBLE
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.VISIBLE
            binding.Nested.visibility = View.INVISIBLE
        })
    }

    fun observeGenreLiveData() {
        viewModel.genresMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                genrelist = it.genres

            }
        })
    }

    fun observerRecentData() {
        viewModel.recentMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                genrelist?.let { genres ->
                    recentAdepter.updateRecentList(it.results, genres)
                }
            }
        })
    }
}




