package com.srjhnd.opennews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.srjhnd.opennews.data.HeadlineRepository

class HeadlineViewModelFactory(private val repository: HeadlineRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HeadlineViewModel(repository) as T
    }
}
