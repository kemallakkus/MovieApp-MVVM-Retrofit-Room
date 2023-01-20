package com.kemalakkus.movieapp_mvvm_retrofit_room.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kemalakkus.movieapp_mvvm_retrofit_room.databinding.RecentItemBinding
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Genre
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Movie

class RecentMovieAdepter(val recentlist:ArrayList<Movie>, val genreList:ArrayList<Genre>):
    RecyclerView.Adapter<RecentMovieAdepter.RecentMovieHolder>() {
    class RecentMovieHolder(val binding: RecentItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentMovieHolder {
        val view = RecentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentMovieHolder(view)
    }

    override fun onBindViewHolder(holder: RecentMovieHolder, position: Int) {
        holder.itemView.apply {
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342/" + recentlist[position].poster_path)
                .into(holder.binding.recentItemImage)
        }
        var genre = ""
        for (id in recentlist.get(position).genre_ids!!) {
            var result = genreList.find { x -> x.id == id }
            result?.let {
                genre += it.name + ", "
            }
        }
        genre = genre.substring(0, genre.length)
        holder.binding.recentGenre.text = genre
        holder.binding.recentItemTitle.text = recentlist.get(position).title
        holder.binding.recentCate.text=recentlist.get(position).release_date
        holder.binding.vote.text=recentlist[position].vote_average.toString()
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(recentlist[position])
            }
        }

    }
    private var onItemClickListener: ((Movie) -> Unit)? = null
    fun setOnItemClickListenerRecent(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }




    override fun getItemCount(): Int {
        return recentlist.size
    }

    fun updateRecentList(newPopularList: List<Movie>, newGenreList: List<Genre>) {
        recentlist.clear()
        genreList.clear()
        genreList.addAll(newGenreList)
        recentlist.addAll(newPopularList)
        notifyDataSetChanged()

    }
}