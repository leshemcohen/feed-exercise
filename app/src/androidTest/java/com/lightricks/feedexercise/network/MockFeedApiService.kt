package com.lightricks.feedexercise.network

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Single

/**
 * todo: implement the mock feed API service here
 */
class MockFeedApiService: FeedApiService{
    private fun readAsset(fileName: String): String {
        val context = ApplicationProvider.getApplicationContext<Context>()
        return context.assets.open(fileName)
            .bufferedReader()
            .use { it.readText() }
    }

    public var numberOfItems : Int = -1

    
    override fun singleFeedItem(): Single<GetFeedResponse> {
        val json = readAsset("get_feed_response.json")
        val getFeedResponse : GetFeedResponse
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(ThumbnailUrlAdapter())
            .build()
        val adapter  = moshi.adapter(GetFeedResponse::class.java)
        getFeedResponse = adapter.fromJson(json)!!

        numberOfItems = getFeedResponse.templatesMetadata.size

        return Single.just(getFeedResponse)
    }

}
