package com.lightricks.feedexercise.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import com.lightricks.feedexercise.data.FeedItem
import com.lightricks.feedexercise.database.AppDatabase
import com.lightricks.feedexercise.database.FeedItemEntity
import com.lightricks.feedexercise.network.FeedApiService
import com.lightricks.feedexercise.network.FeedItemDTO
import com.lightricks.feedexercise.network.GetFeedResponse
import com.lightricks.feedexercise.network.ThumbnailUrlAdapter
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
open class FeedViewModel(application: Application) : AndroidViewModel(application) {
    private val isLoading = MutableLiveData<Boolean>()
    private val isEmpty = MutableLiveData<Boolean>()
    private val feedItems = MediatorLiveData<List<FeedItem>>()
    private val networkErrorEvent = MutableLiveData<Event<String>>()

    private val feedDatabase = Room.databaseBuilder(getApplication(), AppDatabase::class.java,
                                                "database-feeditems").build()

    private val BASE_URL = "https://assets.swishvideoapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(ThumbnailUrlAdapter())
                .build()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private val service = retrofit.create(FeedApiService::class.java)


    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getIsEmpty(): LiveData<Boolean> = isEmpty
    fun getFeedItems(): LiveData<List<FeedItem>> = feedItems
    fun getNetworkErrorEvent(): LiveData<Event<String>> = networkErrorEvent

    init {
        refresh()
    }

    fun refresh() {
        isEmpty.value = true
        isLoading.value = true

        service.singleFeedItem()
            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ feedResponse ->
                handleResponse(feedResponse)
            },{ error ->
                handleNetworkError(error)
            })
    }

    private fun convertToFeedItemsList(items: List<FeedItemDTO>?): List<FeedItem> {
        val listFeedItem = ArrayList<FeedItem>()
        for (item in items!!)
        {
            val feedItem = FeedItem(item.id, item.thumbnailUrl, item.isPremium)
            listFeedItem.add(feedItem)
        }
        return listFeedItem
    }


    private fun insertToDb(items: List<FeedItemDTO>?){
        val listEntity = ArrayList<FeedItemEntity>()
        for (item in items!!)
        {
            val feedItemEntity = FeedItemEntity(item.id, item.thumbnailUrl, item.isPremium)
            listEntity.add(feedItemEntity)
        }
        feedDatabase.feedItemDao().insertAll(listEntity).subscribe()
    }

    private fun handleNetworkError(error: Throwable?) {
        isLoading.postValue(false)
        Log.d("TAG", "handleNetworkError: ${error?.message.toString()}" )//+ error.toString())
    }

    private fun handleResponse(feedResponse: GetFeedResponse?) {
        Log.d("TAG", "handleResponse: ${feedResponse?.toString()}")
        val feed = feedResponse?.templatesMetadata
        insertToDb(feed)
        feedItems.postValue(convertToFeedItemsList(feed))
        isLoading.postValue(false)
        isEmpty.postValue(feed?.isEmpty() ?: true)
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
        return FeedViewModel(application) as T
    }
}