package com.srjhnd.freenews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.srjhnd.freenews.data.HeadlineRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeadlineViewModelFactory @Inject constructor(private val repository: HeadlineRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HeadlineViewModel(repository) as T
    }
}
