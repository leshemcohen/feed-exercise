package com.lightricks.feedexercise.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * todo: add the abstract class that extents RoomDatabase here
 */

@Database(entities = arrayOf(FeedItemEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun feedItemDao(): FeedItemDao

    companion object {
        fun createDatabase(context: Context): AppDatabase
                = Room.databaseBuilder(context, AppDatabase::class.java,
            "database-feeditems").build()
    }
}