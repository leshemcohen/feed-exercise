package com.lightricks.feedexercise.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import com.lightricks.feedexercise.data.FeedItem
import com.lightricks.feedexercise.data.FeedRepository
import com.lightricks.feedexercise.database.AppDatabase
import com.lightricks.feedexercise.database.FeedItemEntity
import com.lightricks.feedexercise.network.*
import com.lightricks.feedexercise.util.Event
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

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
            .subscribe({ handleResponse() },{ throwable-> handleNetworkError(throwable)})
    }

    private fun handleNetworkError(error: Throwable?) {
        isLoading.postValue(false)
        Log.d("TAG", "handleNetworkError: ${error?.message.toString()}" )//+ error.toString())
    }

    private fun handleResponse() {
        Log.d("TAG", "handleResponse")
        isLoading.postValue(false)
    }
}

/**
 * This class creates instances of [FeedViewModel].
 * It's not necessary to use this factory at this stage. But if we will need to inject
 * dependencies into [FeedViewModel] in the future, then this is the place to do it.
 */
class FeedViewModelFactory(val application: Application, val feedRepository: FeedRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            throw IllegalArgumentException("factory used with a wrong class")
        }
        @Suppress("UNCHECKED_CAST")
        return FeedViewModel(application, feedRepository) as T
    }
}