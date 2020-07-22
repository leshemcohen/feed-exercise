package com.lightricks.feedexercise.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable

/***
 * todo: add Room's Data Access Object interface(s) here
 */

@Dao
interface FeedItemDao {
    @Insert
    fun insertAll(vararg feedItemEntitys: FeedItemEntity) : Completable

    @Query("DELETE FROM FeedItemEntity")
    fun deleteAll() : Completable

    @Query("SELECT * FROM FeedItemEntity")
    fun getAll(): LiveData<List<FeedItemEntity>>

    @Query("SELECT COUNT(id) FROM FeedItemEntity")
    fun getCount(): Int
}