package com.lightricks.feedexercise.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * todo: add the abstract class that extents RoomDatabase here
 */

@Database(entities = arrayOf(FeedItemEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun feedItemDao(): FeedItemDao
}