package com.lightricks.feedexercise.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lightricks.feedexercise.database.AppDatabase
import com.lightricks.feedexercise.database.FeedItemDao
import com.lightricks.feedexercise.network.FeedApiService
import com.lightricks.feedexercise.network.MockFeedApiService
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class FeedRepositoryTest {

    private lateinit var db : AppDatabase
    private val apiService =  MockFeedApiService()
    private lateinit var dao : FeedItemDao

    private lateinit var feedRepository : FeedRepository

    @Before
    fun setup(){
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.feedItemDao()
        feedRepository = FeedRepository(apiService, db)
    }

    @After
    fun close(){
        db.close()
    }

    @Test
    fun testRefresh(){
        assertTrue("Database is empty",db.feedItemDao().getCount() == 0)
        val observer = feedRepository.refresh().test()
        observer.awaitTerminalEvent()
        observer.assertComplete()
        assertTrue("Data loaded to db",db.feedItemDao().getCount() > 0)
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()
    @Test
    fun getFeedItems(){
        assertTrue("Database is empty",dao.getCount() == 0)
        val observer = feedRepository.refresh().test()
        observer.awaitTerminalEvent()
        observer.assertComplete()
        val feedItems = feedRepository.getFeedItems().blockingObserve()
        assertTrue("Feed Items", !feedItems.isNullOrEmpty())
    }


    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(t: T) {
                value = t
                latch.countDown()
                removeObserver(this)
            }
        }
        observeForever(observer)
        latch.await(5, TimeUnit.SECONDS)
        return value
    }

}

