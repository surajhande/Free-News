package com.srjhnd.freenews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srjhnd.freenews.data.Headline
import com.srjhnd.freenews.data.HeadlineRepository
import com.srjhnd.freenews.data.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

class FeedViewModel @Inject constructor(private val repository: HeadlineRepository) : ViewModel() {
    val headlines: LiveData<List<Headline>> = repository.allHeadlines
    val requestState: MutableLiveData<State> = MutableLiveData(State.IDLE)
    var initialUpdateRequired: Boolean = true

    private fun insertHeadline(headline: Headline) = CoroutineScope(IO).launch {
        repository.insertHeadline(headline)
    }

    fun updateHeadline(headline: Headline) = CoroutineScope(IO).launch {
        repository.updateHeadline(headline)
    }

    fun deleteHeadline(headline: Headline) = CoroutineScope(IO).launch {
        repository.deleteHeadline(headline)
    }

    fun getCount() = runBlocking {
        repository.getCountAsync().await()
    }

    fun fetchTopHeadlines() = viewModelScope.launch {
        requestState.postValue(State.LOADING)
        repository.fetchTopHeadlines().collect { response ->
            Timber.d("response arrived as ${response.status}")
            if (response.status == "ERROR") {
                requestState.postValue(State.FAILURE)
            } else {
                response.articles.forEach {
                    println("source is ${it.source?.name}")
                    it.timestamp = System.currentTimeMillis()
                    insertHeadline(it)
                }
                requestState.postValue(State.IDLE)
            }
        }

    }
}
