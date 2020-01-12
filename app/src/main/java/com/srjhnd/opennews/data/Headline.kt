package com.srjhnd.opennews.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "headlines_table")
data class Headline(
    @Embedded
    var source: Source?,
    var author: String?,
    var title: String?,
    var description: String?,
    var url: String?,
    var urlToImage: String?,
    var publishedAt: String?,
    var content: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}