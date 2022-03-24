package me.pavelsgarklavs.androidmovieapp.activities.MovieDetailsActivity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import me.pavelsgarklavs.androidmovieapp.R
import me.pavelsgarklavs.androidmovieapp.utils.Constants

class MovieDetailsActivity : AppCompatActivity() {
    private val viewModel = MovieDetailsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val title = intent.getStringExtra("movie_title")
        val releaseDate = intent.getStringExtra("movie_release_date")
        val poster = intent.getStringExtra("movie_poster")
        val overview = intent.getStringExtra("movie_overview")
        val genreId = intent.getIntExtra("movie_genre_id", 0)

        val movieTitleTextView = findViewById<TextView>(R.id.movie_title)
        val movieReleaseDateTextView = findViewById<TextView>(R.id.movie_release_date)
        val posterImageView = findViewById<ImageView>(R.id.movie_poster)
        val overviewTextView = findViewById<TextView>(R.id.movie_overview)

        movieTitleTextView.text = title
        movieReleaseDateTextView.text = "Released: ${releaseDate}"
        overviewTextView.text = overview
        Glide.with(this).load(Constants.imageBaseURL + poster).into(posterImageView)
        viewModel.setGenreName(genreId, this)

    }


}