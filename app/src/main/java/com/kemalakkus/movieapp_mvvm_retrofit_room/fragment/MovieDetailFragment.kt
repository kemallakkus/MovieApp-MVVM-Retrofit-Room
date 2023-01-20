package com.kemalakkus.movieapp_mvvm_retrofit_room.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kemalakkus.movieapp_mvvm_retrofit_room.R
import com.kemalakkus.movieapp_mvvm_retrofit_room.databinding.FragmentMovieDetailBinding
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Genre
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Movie
import com.kemalakkus.movieapp_mvvm_retrofit_room.viewmodel.MovieViewModels


class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var viewModel: MovieViewModels
    private lateinit var alertBuilder: AlertDialog.Builder
    private var genrelist: List<Genre>? = null
    val arg: MovieDetailFragmentArgs by navArgs()
    private lateinit var moviewX: Movie


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arg.movie


        viewModel = ViewModelProvider(this).get(MovieViewModels::class.java)
        viewModel.getGenres()


        observeLiveGenreData(movie)
        fabListener(movie)
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }


    }

    private fun fabListener(movie: Movie) {
        binding.fab.setOnClickListener {
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog, null)
            alertBuilder = AlertDialog.Builder(requireContext())
            alertBuilder.setView(view)
            val dialog = alertBuilder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
            radioGroup.setOnCheckedChangeListener { group, selected ->
                val id = movie.id
                val image = movie.poster_path
                val title = movie.title
                val first = view.findViewById<RadioButton>(R.id.firstSection).text.toString()
                val second = view.findViewById<RadioButton>(R.id.secondSection).text.toString()
                val third = view.findViewById<RadioButton>(R.id.thirdSection).text.toString()
                when (selected) {
                    R.id.firstSection -> {
                        moviewX = Movie(
                            id,
                            movie.overview,
                            movie.popularity,
                            image,
                            movie.release_date,
                            title,
                            movie.genre_ids,
                            movie.vote_average,
                            movie.vote_count,
                            first,
                            "#c93a3a"
                        )
                        viewModel.insertMovie(moviewX)
                        dialog.dismiss()
                    }
                    R.id.secondSection -> {
                        moviewX = Movie(
                            id,
                            movie.overview,
                            movie.popularity,
                            image,
                            movie.release_date,
                            title,
                            movie.genre_ids,
                            movie.vote_average,
                            movie.vote_count,
                            second,
                            "#3ab8c9"
                        )
                        viewModel.insertMovie(moviewX)
                        dialog.dismiss()
                    }
                    R.id.thirdSection -> {
                        moviewX = Movie(
                            id,
                            movie.overview,
                            movie.popularity,
                            image,
                            movie.release_date,
                            title,
                            movie.genre_ids,
                            movie.vote_average,
                            movie.vote_count,
                            third,
                            "#c9c93a"
                        )
                        viewModel.insertMovie(moviewX)
                        dialog.dismiss()


                    }
                }
                Snackbar.make(it, "Successfully Saved", Snackbar.LENGTH_SHORT).show()

            }


        }
    }

    private fun observeLiveGenreData(movie: Movie) {
        viewModel.genresMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                genrelist = it.genres
            }
            setDetailsMovies(movie, genrelist!!)
        })
    }

    private fun setDetailsMovies(movie: Movie, list: List<Genre>) {
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w342/" + movie.poster_path)
            .into(binding.detailImage)
        binding.detailOverview.text = movie.overview
        binding.detailTitle.text = movie.title
        binding.detailVote.text = movie.vote_average.toString()

        var genre = ""
        for (id in movie.genre_ids!!) {
            var result = list.find { x -> x.id == id }
            result?.let {
                genre += it.name + " ,"

            }
        }
        genre = genre.substring(0, genre.length)


        binding.detailGenre.text = genre

    }

}