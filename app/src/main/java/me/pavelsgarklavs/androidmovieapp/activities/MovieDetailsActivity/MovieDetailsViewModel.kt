package me.pavelsgarklavs.androidmovieapp.activities.MovieDetailsActivity

import android.app.Activity
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.pavelsgarklavs.androidmovieapp.R
import me.pavelsgarklavs.androidmovieapp.api.MovieApi.MovieApi
import me.pavelsgarklavs.androidmovieapp.api.MovieApi.MovieApiService
import me.pavelsgarklavs.androidmovieapp.api.models.Genres.Genre
import me.pavelsgarklavs.androidmovieapp.api.models.Genres.GenreResponse
import me.pavelsgarklavs.androidmovieapp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsViewModel {

    fun setGenreName(genreId: Int, activity: Activity) {

        val movieGenresTextView = activity.findViewById<TextView>(R.id.movie_genre)

        GlobalScope.launch(Dispatchers.Main) {
            getGenresData { allGenres: List<Genre> ->
                for (genre in allGenres) {
                    if (genreId == genre.id && genre.name != null) {
                        movieGenresTextView.text = genre.name
                    }
                }
            }
        }


    }

    private fun getGenresData(callback: (List<Genre>) -> Unit) {
        val apiService = MovieApiService.getInstance().create(MovieApi::class.java)
        apiService.getGenres(Constants.apiKey).enqueue(object : Callback<GenreResponse> {
            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                val callBack = response.body() ?: return
                return callback(callBack.genres)
            }
        })
    }

}