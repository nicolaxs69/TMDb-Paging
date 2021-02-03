package com.sample.android.tmdb.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.android.tmdb.util.EspressoIdlingResource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

abstract class BaseViewModel<T> : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    protected open val mutableLiveData = MutableLiveData<T>()
    val liveData: LiveData<T>
        get() = mutableLiveData

    protected abstract val requestObservable: Observable<T>

    protected fun sendRequest() {
        composeObservable { requestObservable }.subscribe({
            mutableLiveData.postValue(it)
        }) {
            Timber.e(it)
        }.also { compositeDisposable.add(it) }
    }

    private fun <T> composeObservable(task: () -> Observable<T>): Observable<T> = task()
            .doOnSubscribe { EspressoIdlingResource.increment() } // App is busy until further notice
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement() // Set app as idle.
                }
            }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all disposables;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}