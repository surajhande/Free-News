package com.srjhnd.opennews.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HeadlineDAO {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(headline: Headline)

    @Insert
    suspend fun insertAll(headlines: List<Headline>)

    @Delete
    suspend fun delete(headline: Headline)

    @Update
    suspend fun update(headline: Headline)

    @Query("SELECT * FROM headlines_table ORDER BY publishedAt DESC")
    fun getHeadlines(): LiveData<List<Headline>>

    @Query("SELECT COUNT(*) FROM headlines_table")
    fun getCount(): Int
}