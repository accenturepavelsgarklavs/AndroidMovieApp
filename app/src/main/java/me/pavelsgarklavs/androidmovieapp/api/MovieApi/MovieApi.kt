package me.pavelsgarklavs.androidmovieapp.api.MovieApi

import me.pavelsgarklavs.androidmovieapp.api.models.Genres.GenreResponse
import me.pavelsgarklavs.androidmovieapp.api.models.Movies.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("3/movie/popular")
    fun getMovies(@Query("api_key") apiKey: String): Call<MovieResponse>

    @GET("3/genre/movie/list")
    fun getGenres(@Query("api_key") apiKey: String): Call<GenreResponse>

}