package com.srjhnd.freenews.viewmodel

import androidx.lifecycle.ViewModel
import com.srjhnd.freenews.data.HeadlineRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class HeadlineViewModel @Inject constructor(private val repository: HeadlineRepository) :
    ViewModel() {
    fun getHeadlineByTitle(title: String) = runBlocking {
        repository.getHeadlineByIdAsync(title).await()
    }
}