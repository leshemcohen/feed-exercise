package com.lightricks.feedexercise.network

import com.lightricks.feedexercise.data.FeedItem
import com.squareup.moshi.JsonClass

/**
 * todo: add Data Transfer Object data class(es) here
 */


@JsonClass(generateAdapter = true)
data class GetFeedResponse(
    val templatesMetadata : List<FeedItem>
)