package com.kemalakkus.movieapp_mvvm_retrofit_room.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kemalakkus.movieapp_mvvm_retrofit_room.databinding.SavedItemBinding
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Movie

class SavedMovieAdepter: RecyclerView.Adapter<SavedMovieAdepter.SavedMovieHolder>(){
    class SavedMovieHolder(val binding: SavedItemBinding): RecyclerView.ViewHolder(binding.root)
    private val differCallback=object : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem==newItem
        }
    }
    val differ= AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedMovieHolder {
        val view=SavedItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  SavedMovieHolder(view)
    }

    override fun onBindViewHolder(holder: SavedMovieHolder, position: Int) {
        val movie=differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load("https://image.tmdb.org/t/p/w342/" + movie.poster_path)
                .into(holder.binding.popularItemImage)
        }
        holder.binding.titleTxt.text=movie.title
        holder.binding.ratee.text="Rating: ${movie.rating}"
        holder.binding.frame.setBackgroundColor(Color.parseColor(movie.ratingColor))
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(movie)
            }

        }

    }
    private var onItemClickListener: ((Movie) -> Unit)? = null


    fun setOnItemClickListener(listener: (Movie) -> Unit){
        onItemClickListener = listener


    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}