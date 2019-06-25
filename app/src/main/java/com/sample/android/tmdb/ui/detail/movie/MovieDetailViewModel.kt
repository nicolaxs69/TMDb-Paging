package com.sample.android.tmdb.ui.detail.movie

import com.sample.android.tmdb.repository.MoviesRemoteDataSource
import com.sample.android.tmdb.ui.detail.DetailViewModel
import com.sample.android.tmdb.vo.Cast
import com.sample.android.tmdb.vo.Movie
import com.sample.android.tmdb.vo.Video
import io.reactivex.Observable

class MovieDetailViewModel(private val datasource: MoviesRemoteDataSource)
    : DetailViewModel<Movie>() {

    override fun getTrailers(id: Int): Observable<List<Video>> = datasource.getMovieTrailers(id)

    override fun getCast(id: Int): Observable<List<Cast>> = datasource.getMovieCast(id)
}