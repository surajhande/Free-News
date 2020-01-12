package com.srjhnd.opennews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.srjhnd.opennews.data.Headline
import com.srjhnd.opennews.data.HeadlineRepository

class HeadlineViewModel(private val headlineRepository: HeadlineRepository) : ViewModel() {
    val headlines: LiveData<List<Headline>> = headlineRepository.allHeadlines
    fun insertHeadline(headline: Headline) = headlineRepository.inserHeadline(headline)
    fun updateHeadline(headline: Headline) = headlineRepository.updateHeadline(headline)
    fun deleteHeadline(headline: Headline) = headlineRepository.deleteHeadline(headline)

}