package me.pavelsgarklavs.androidmovieapp.activities.MovieDetailsActivity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import me.pavelsgarklavs.androidmovieapp.MovieData
import me.pavelsgarklavs.androidmovieapp.R
import me.pavelsgarklavs.androidmovieapp.api.models.Genres.Genre
import me.pavelsgarklavs.androidmovieapp.utils.Constants

class MovieDetailsActivity : AppCompatActivity() {

    private val viewModel = MovieDetailsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movies = MovieData.movie

        val movieTitleTextView = findViewById<TextView>(R.id.movie_title)
        val movieReleaseDateTextView = findViewById<TextView>(R.id.movie_release_date)
        val posterImageView = findViewById<ImageView>(R.id.movie_poster)
        val overviewTextView = findViewById<TextView>(R.id.movie_overview)
        val movieGenresTextView = findViewById<TextView>(R.id.movie_genre)

        movieTitleTextView.text = movies.title
        movieReleaseDateTextView.text = "Released: ${movies.release}"
        overviewTextView.text = movies.overview
        Glide.with(this).load(Constants.imageBaseURL + movies.poster).into(posterImageView)

        lifecycleScope.launch {
            viewModel.getGenresData { allGenres: List<Genre> ->
                for (genre in allGenres) {
                    if (movies.genre?.get(0) == genre.id && genre.name != null) {
                        movieGenresTextView.text = genre.name
                    }
                }
            }
        }
    }

}