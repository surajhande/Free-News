package com.srjhnd.freenews.data

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Exception

class DateConverter {

    @TypeConverter
    fun toDate(publishedAt: String): Date? {
        var date: Date? = null
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        try {
            date = dateFormat.parse(publishedAt)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return date
    }

    @TypeConverter
    fun toString(date: Date): String? {
        var publishedAt: String? = null
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            publishedAt = dateFormat.format(date)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return publishedAt
    }
}