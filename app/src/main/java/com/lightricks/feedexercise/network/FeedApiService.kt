package com.lightricks.feedexercise.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * todo: add the FeedApiService interface and the Retrofit and Moshi code here
 */


interface FeedApiService{
    @GET("Android/demo/feed.json")
    fun singleFeedItem(): Single<GetFeedResponse>

}

object ServiceBuilder {

    private val BASE_URL = "https://assets.swishvideoapp.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(ThumbnailUrlAdapter())
                .build()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}