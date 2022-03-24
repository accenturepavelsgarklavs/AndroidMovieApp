package me.pavelsgarklavs.androidmovieapp.api.models.Genres

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>
) : Parcelable {
    constructor() : this(mutableListOf())
}