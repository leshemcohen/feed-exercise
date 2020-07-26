package com.lightricks.feedexercise.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * todo: add Room's Entity data class(es) here
 */


@Entity
data class FeedItemEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String?,
    @ColumnInfo(name = "is_premium") val isPremium: Boolean
)

