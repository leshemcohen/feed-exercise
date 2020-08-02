package com.lightricks.feedexercise.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.lightricks.feedexercise.data.FeedItem
import com.lightricks.feedexercise.data.FeedRepository
import com.lightricks.feedexercise.database.AppDatabase
import com.lightricks.feedexercise.network.ServiceBuilder
import com.lightricks.feedexercise.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * This view model manages the data for [FeedFragment].
 */
open class FeedViewModel(application: Application,private val feedRepository : FeedRepository) : AndroidViewModel(application) {
    private val isLoading = MutableLiveData<Boolean>()
//    private val isEmpty = MutableLiveData<Boolean>()
    private val isEmpty = MediatorLiveData<Boolean>()
//    private val feedItems = MediatorLiveData<List<FeedItem>>()
    private val feedItems : LiveData<List<FeedItem>>
    private val networkErrorEvent = MutableLiveData<Event<String>>()

    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getIsEmpty(): LiveData<Boolean> = isEmpty
    fun getFeedItems(): LiveData<List<FeedItem>> = feedItems
    fun getNetworkErrorEvent(): LiveData<Event<String>> = networkErrorEvent

    init {
        refresh()
        feedItems = feedRepository.getFeedItems()
        isEmpty.addSource(feedRepository.getFeedItems()){items -> isEmpty.postValue(items.isEmpty())}
    }

    fun refresh() {
        isLoading.value = true
        feedRepository.refresh()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ isLoading.postValue(false) },{ throwable-> handleNetworkError(throwable)})
    }

    private fun handleNetworkError(error: Throwable?) {
        isLoading.postValue(false)
        networkErrorEvent.postValue(Event<String>(error?.message.toString()?:"Error"))
        Log.d("TAG", "handleNetworkError: ${error?.message.toString()}" )//+ error.toString())
    }
}

/**
 * This class creates instances of [FeedViewModel].
 * It's not necessary to use this factory at this stage. But if we will need to inject
 * dependencies into [FeedViewModel] in the future, then this is the place to do it.
 */
class FeedViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            throw IllegalArgumentException("factory used with a wrong class")
        }
        @Suppress("UNCHECKED_CAST")
        return FeedViewModel(application, FeedRepository(ServiceBuilder.service,
            AppDatabase.getDatabase(application))) as T
    }
}