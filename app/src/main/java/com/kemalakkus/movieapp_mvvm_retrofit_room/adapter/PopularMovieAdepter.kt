package com.kemalakkus.movieapp_mvvm_retrofit_room.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kemalakkus.movieapp_mvvm_retrofit_room.databinding.PopularItemBinding
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Genre
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Movie

class PopularMovieAdepter(val popularList:ArrayList<Movie>, val genrelist:ArrayList<Genre>):
    RecyclerView.Adapter<PopularMovieAdepter.PopularHolder>() {
    class PopularHolder(var binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularHolder {
        val view = PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularHolder(view)
    }

    override fun onBindViewHolder(holder: PopularHolder, position: Int) {
        holder.itemView.apply {
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342/" + popularList[position].poster_path)
                .into(holder.binding.popularItemImage)
        }
        var genre = ""
        for (id in popularList.get(position).genre_ids!!) {
            var result = genrelist.find { x -> x.id == id }
            result?.let {
                genre += it.name + ", "
            }
        }
        genre = genre.substring(0, genre.length)
        holder.binding.popularGenre.text = genre
        holder.binding.popularItemTitle.text = popularList.get(position).title
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(popularList[position])
            }

        }

    }
    private var onItemClickListener: ((Movie) -> Unit)? = null


    fun setOnItemClickListener(listener: (Movie) -> Unit){
        onItemClickListener = listener


    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    fun updatePopularList(newPopularList: List<Movie>,newGenreList:List<Genre>) {
        popularList.clear()
        genrelist.clear()
        genrelist.addAll(newGenreList)
        popularList.addAll(newPopularList)
        notifyDataSetChanged()
    }



}