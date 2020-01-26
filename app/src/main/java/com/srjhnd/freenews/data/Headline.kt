package com.srjhnd.freenews.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*


@Entity(tableName = "headlines_table")
data class Headline(
    @Embedded
    var source: Source?,
    var author: String?,
    @PrimaryKey
    var title: String,
    var description: String?,
    var url: String?,
    var urlToImage: String?,
    @TypeConverters(DateConverter::class)
    var publishedAt: Date?,
    var content: String?
)