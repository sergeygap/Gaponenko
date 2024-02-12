package com.gap.tinkoffeducation.presentation.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gap.tinkoffeducation.data.repository.FilmsRepositoryImpl
import com.gap.tinkoffeducation.domain.entity.Films
import com.gap.tinkoffeducation.domain.usecases.GetFilmsDetails
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FilmsRepositoryImpl(application)
    private val getFilmsDetails = GetFilmsDetails(repository)

    private val _filmLD = MutableLiveData<Films>()
    val filmLD: LiveData<Films>
        get() = _filmLD

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _filmsStateLD = MutableLiveData<Boolean>()

    val filmsStateLD: LiveData<Boolean>
        get() = _filmsStateLD

    fun loadData(id: Int) {
        viewModelScope.launch {
            val response  = getFilmsDetails(id)
            if (response.id != ERROR_ID && response.name != "") {
                _filmsStateLD.postValue(true)
                _isLoading.postValue(true)
                val film = getFilmsDetails(id)
                _filmLD.postValue(film)
                _isLoading.postValue(false)
            } else {
                _filmsStateLD.postValue(false)
            }
        }
    }

    fun progressState(state: Boolean) {
        _isLoading.postValue(state)
    }

    companion object {
        private const val ERROR_ID = -2024
    }


}