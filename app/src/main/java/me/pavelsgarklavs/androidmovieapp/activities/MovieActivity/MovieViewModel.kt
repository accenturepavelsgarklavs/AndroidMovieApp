package me.pavelsgarklavs.androidmovieapp.activities.MovieActivity

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import kotlinx.coroutines.coroutineScope
import me.pavelsgarklavs.androidmovieapp.api.MovieApi.MovieApi
import me.pavelsgarklavs.androidmovieapp.api.MovieApi.MovieApiService
import me.pavelsgarklavs.androidmovieapp.api.models.Movies.Movie
import me.pavelsgarklavs.androidmovieapp.api.models.Movies.MovieResponse
import me.pavelsgarklavs.androidmovieapp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel {

    private lateinit var moviesResponse: List<Movie>

    suspend fun getMovieData(progressBar: ProgressBar, callback: (List<Movie>) -> Unit) =
        coroutineScope {
            val apiService = MovieApiService.getInstance().create(MovieApi::class.java)
            progressBar.visibility = View.VISIBLE

            apiService.getMovies(Constants.apiKey).enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    t.message?.let { Log.e(ContentValues.TAG, it) }
                }
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    progressBar.visibility = View.GONE
                    val callBack = response.body() ?: return
                    moviesResponse = callBack.movies
                    return callback(callBack.movies)
                }
            })
        }

    suspend fun onRefresh(callback: (List<Movie>) -> Unit) =
        coroutineScope {
            val apiService = MovieApiService.getInstance().create(MovieApi::class.java)

            apiService.getMovies(Constants.apiKey).enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    t.message?.let { Log.e(ContentValues.TAG, it) }
                }
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    val callBack = response.body() ?: return
                    moviesResponse = callBack.movies
                    return callback(callBack.movies)
                }
            })
        }

    fun onFilter(position: Int): List<Movie> {
        return when (position) {
            1 -> {
                moviesResponse.sortedWith(compareByDescending { it.voteAverage })
            }
            2 -> {
                moviesResponse.sortedWith(compareBy { it.voteAverage })
            }
            else -> {
                moviesResponse
            }
        }
    }
}