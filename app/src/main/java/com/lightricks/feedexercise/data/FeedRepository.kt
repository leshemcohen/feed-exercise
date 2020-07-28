package com.lightricks.feedexercise.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.lightricks.feedexercise.database.AppDatabase
import com.lightricks.feedexercise.database.FeedItemEntity
import com.lightricks.feedexercise.network.FeedApiService
import com.lightricks.feedexercise.network.FeedItemDTO
import com.lightricks.feedexercise.network.GetFeedResponse
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

/**
 * This is our data layer abstraction. Users of this class don't need to know
 * where the data actually comes from (network, database or somewhere else).
 */
class FeedRepository (
    private val feedApiService: FeedApiService,
    private val feedDatabase : AppDatabase
){
    private val feedItems: LiveData<List<FeedItem>> =
        Transformations.map(feedDatabase.feedItemDao().getAll()) {
            it.toFeedItems()
        }

    public fun refresh() : Completable{
        return feedApiService.singleFeedItem().flatMapCompletable { response ->  insertToDb(response.templatesMetadata)}
    }


    private fun insertToDb(items: List<FeedItemDTO>?): Completable{
        val listEntity = ArrayList<FeedItemEntity>()
        for (item in items!!)
        {
            val feedItemEntity = FeedItemEntity(item.id, item.thumbnailUrl, item.isPremium)
            listEntity.add(feedItemEntity)
        }
        return feedDatabase.feedItemDao().insertAll(listEntity)
    }

    fun List<FeedItemEntity>.toFeedItems(): List<FeedItem> {
        return map {
            FeedItem(it.id, it.thumbnailUrl, it.isPremium)
        }
    }

    fun getFeedItems() : LiveData<List<FeedItem>>{
        return feedItems
    }
}