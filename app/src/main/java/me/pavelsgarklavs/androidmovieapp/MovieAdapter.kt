package me.pavelsgarklavs.androidmovieapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.pavelsgarklavs.androidmovieapp.activities.MovieDetailsActivity.MovieDetailsActivity
import me.pavelsgarklavs.androidmovieapp.api.models.Movies.Movie
import me.pavelsgarklavs.androidmovieapp.utils.Constants

class MovieAdapter(
    private val movies: List<Movie>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindMovie(movie: Movie) {

            val title = itemView.findViewById<TextView>(R.id.movie_title)
            val releaseDate = itemView.findViewById<TextView>(R.id.movie_release_date)
            val moviePoster = itemView.findViewById<ImageView>(R.id.movie_poster)
            val averageScore = itemView.findViewById<TextView>(R.id.movie_average_score)

            title.text = movie.title
            releaseDate.text = "Released: ${movie.release}"
            averageScore.text = movie.voteAverage.toString()
            Glide.with(itemView).load(Constants.imageBaseURL + movie.poster).into(moviePoster)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, MovieDetailsActivity::class.java)

                intent.putExtra("movie_title", movie.title)
                intent.putExtra("movie_release_date", movie.release)
                intent.putExtra("movie_poster", movie.poster)
                intent.putExtra("movie_overview", movie.overview)
                intent.putExtra("movie_genre_id", movie.genre?.get(0))

                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movies[position])
    }
}