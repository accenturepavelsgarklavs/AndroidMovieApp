package me.pavelsgarklavs.androidmovieapp.activities.MovieActivity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.pavelsgarklavs.androidmovieapp.MovieAdapter
import me.pavelsgarklavs.androidmovieapp.R
import me.pavelsgarklavs.androidmovieapp.api.MovieApi.MovieApi
import me.pavelsgarklavs.androidmovieapp.api.MovieApi.MovieApiService
import me.pavelsgarklavs.androidmovieapp.api.models.Movies.Movie
import me.pavelsgarklavs.androidmovieapp.api.models.Movies.MovieResponse
import me.pavelsgarklavs.androidmovieapp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val rv = findViewById<RecyclerView>(R.id.rv_movies_list)

        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)

        lifecycleScope.launch(Dispatchers.Main) {
            getMovieData { movies: List<Movie> ->
                rv.adapter = MovieAdapter(movies)
            }
        }
    }

    // TODO: Move API Call to movie view model
    private fun getMovieData(callback: (List<Movie>) -> Unit) {
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE
        val apiService = MovieApiService.getInstance().create(MovieApi::class.java)
        apiService.getMovies(Constants.apiKey).enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.e(TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                progressBar.visibility = View.GONE
                val callBack = response.body() ?: return
                return callback(callBack.movies)
            }
        })
    }
}