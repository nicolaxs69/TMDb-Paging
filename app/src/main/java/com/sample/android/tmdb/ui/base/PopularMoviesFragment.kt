package com.sample.android.tmdb.ui.base

import com.sample.android.tmdb.SortType
import com.sample.android.tmdb.SortType.MOST_POPULAR
import com.sample.android.tmdb.di.ActivityScoped
import com.sample.android.tmdb.ui.MovieFragment
import javax.inject.Inject

@ActivityScoped
class PopularMoviesFragment @Inject
constructor() : MovieFragment() {

    override fun getSortType(): SortType = MOST_POPULAR
}