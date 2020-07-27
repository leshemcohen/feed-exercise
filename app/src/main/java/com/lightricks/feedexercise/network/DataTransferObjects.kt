package com.lightricks.feedexercise.network

import com.squareup.moshi.*

/**
 * todo: add Data Transfer Object data class(es) here
 */


@JsonClass(generateAdapter = true)
data class GetFeedResponse(
    val templatesMetadata : List<FeedItemDTO>
)


@JsonClass(generateAdapter = true)
data class FeedItemDTO(
    val id: String, // Kotlin note: "val" means read-only value.
    @Json(name = "templateThumbnailURI") @ThumbnailUrl val thumbnailUrl: String,
    val isPremium: Boolean
)


@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class ThumbnailUrl
internal class ThumbnailUrlAdapter {

    private val BASE_URL = "https://assets.swishvideoapp.com/Android/demo/catalog/thumbnails/"

    @ToJson
    fun toJson(@ThumbnailUrl thumbnailUrl: String): String {
        return thumbnailUrl
    }

    @FromJson
    @ThumbnailUrl
    fun fromJson(thumbnailUrl: String): String {
        return "$BASE_URL$thumbnailUrl"
    }
}