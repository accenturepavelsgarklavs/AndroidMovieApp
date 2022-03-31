package me.pavelsgarklavs.androidmovieapp.activities.MovieActivity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.launch
import me.pavelsgarklavs.androidmovieapp.MovieAdapter
import me.pavelsgarklavs.androidmovieapp.R
import me.pavelsgarklavs.androidmovieapp.api.models.Movies.Movie

class MovieActivity : AppCompatActivity() {

    private val viewModel = MovieViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val rv = findViewById<RecyclerView>(R.id.rv_movies_list)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val filterAutoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.filter_auto_complete_text_view)
        val swipeToRefresh = findViewById<SwipeRefreshLayout>(R.id.swipe_to_refresh)

        val filters = resources.getStringArray(R.array.filters)
        val filterAdapter = ArrayAdapter(this, R.layout.dropdown_item, filters)
        filterAutoCompleteTextView.setAdapter(filterAdapter)

        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)

        lifecycleScope.launch {
            viewModel.getMovieData(progressBar) { movies: List<Movie> ->
                rv.adapter = MovieAdapter(movies)
            }
        }

        swipeToRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.onRefresh { movies: List<Movie> ->
                    rv.adapter = MovieAdapter(movies)
                }
            }
            swipeToRefresh.isRefreshing = false
            filterAutoCompleteTextView.setText("None", false)
        }

        filterAutoCompleteTextView.setOnItemClickListener { adapterView, view, position, id ->
            rv.adapter = MovieAdapter(viewModel.onFilter(position))
        }

    }

}