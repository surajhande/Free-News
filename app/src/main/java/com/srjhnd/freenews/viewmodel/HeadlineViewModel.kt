package com.srjhnd.freenews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srjhnd.freenews.data.Headline
import com.srjhnd.freenews.data.HeadlineRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HeadlineViewModel(private val headlineRepository: HeadlineRepository) : ViewModel() {
    val headlines: LiveData<List<Headline>> = headlineRepository.allHeadlines
    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)

    fun insertHeadline(headline: Headline) = CoroutineScope(IO).launch {
        headlineRepository.insertHeadline(headline)
    }

    fun updateHeadline(headline: Headline) = CoroutineScope(IO).launch {
        headlineRepository.updateHeadline(headline)
    }

    fun deleteHeadline(headline: Headline) = CoroutineScope(IO).launch {
        headlineRepository.deleteHeadline(headline)
    }

    fun getCount() = runBlocking {
        headlineRepository.getCountAsync().await()
    }

    fun fetchTopHeadlines() = viewModelScope.launch {
        isRefreshing.value = false
        headlineRepository.fetchTopHeadlines().collect { response ->
            if (response.status != "") {
                response.articles.forEach {
                    println("source is ${it.source?.name}")
                    insertHeadline(it) }
            }
        }
        isRefreshing.value = true
    }
}
