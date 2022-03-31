package me.pavelsgarklavs.androidmovieapp.activities.MovieDetailsActivity

import android.content.ContentValues
import android.util.Log
import kotlinx.coroutines.coroutineScope
import me.pavelsgarklavs.androidmovieapp.api.MovieApi.MovieApi
import me.pavelsgarklavs.androidmovieapp.api.MovieApi.MovieApiService
import me.pavelsgarklavs.androidmovieapp.api.models.Genres.Genre
import me.pavelsgarklavs.androidmovieapp.api.models.Genres.GenreResponse
import me.pavelsgarklavs.androidmovieapp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsViewModel {

    suspend fun getGenresData(callback: (List<Genre>) -> Unit) = coroutineScope {
        val apiService = MovieApiService.getInstance().create(MovieApi::class.java)

        apiService.getGenres(Constants.apiKey).enqueue(object : Callback<GenreResponse> {
            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                t.message?.let { Log.e(ContentValues.TAG, it) }
            }
            override fun onResponse(
                call: Call<GenreResponse>,
                response: Response<GenreResponse>
            ) {
                val callBack = response.body() ?: return
                return callback(callBack.genres)
            }
        })
    }

}