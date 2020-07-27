package com.lightricks.feedexercise.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable

/***
 * todo: add Room's Data Access Object interface(s) here
 */

@Dao
interface FeedItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(feedItemEntitylist: List<FeedItemEntity>) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFeedItem(vararg feedItemEntitys: FeedItemEntity) : Completable

    @Query("DELETE FROM FeedItemEntity")
    fun deleteAll() : Completable

    @Query("SELECT * FROM FeedItemEntity")
    fun getAll(): LiveData<List<FeedItemEntity>>

    @Query("SELECT COUNT(id) FROM FeedItemEntity")
    fun getCount(): Int
}