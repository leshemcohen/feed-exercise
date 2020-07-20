package com.lightricks.feedexercise.data

import com.squareup.moshi.*


/**
 * This data class is our internal representation of a feed item.
 * In a real-life application this is template meta-data for a user's project.
 * The rest of the properties are left out for brevity.
 */

@JsonClass(generateAdapter = true)
data class FeedItem(
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