package com.srjhnd.opennews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srjhnd.opennews.data.Headline
import com.srjhnd.opennews.data.HeadlineRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HeadlineViewModel(private val headlineRepository: HeadlineRepository) : ViewModel() {
    val headlines: LiveData<List<Headline>> = headlineRepository.allHeadlines
    fun insertHeadline(headline: Headline) = headlineRepository.insertHeadline(headline)
    fun updateHeadline(headline: Headline) = headlineRepository.updateHeadline(headline)
    fun deleteHeadline(headline: Headline) = headlineRepository.deleteHeadline(headline)
    fun getCount() = runBlocking {
        headlineRepository.getCountAsync().await()
    }

    fun fetchTopHeadlines() = viewModelScope.launch {
        headlineRepository.fetchTopHeadlines()
    }
}