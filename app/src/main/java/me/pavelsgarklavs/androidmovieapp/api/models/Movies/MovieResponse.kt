package me.pavelsgarklavs.androidmovieapp.api.models.Movies

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponse(
    @SerializedName("results")
    val movies: List<Movie>

) : Parcelable {
    constructor() : this(mutableListOf())
}