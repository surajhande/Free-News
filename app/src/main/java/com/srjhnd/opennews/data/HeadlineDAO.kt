package com.srjhnd.opennews.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HeadlineDAO {


    @Insert
    fun insert(headline: Headline)

    @Insert
    fun insertAll(headlines: List<Headline>)

    @Delete
    fun delete(headline: Headline)

    @Update
    fun update(headline: Headline)

    @Query("SELECT * FROM headlines_table")
    fun getHeadlines(): LiveData<List<Headline>>
}