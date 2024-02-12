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

    fun loadData(id: Int) {
        viewModelScope.launch {
            val film = getFilmsDetails(id)
            _filmLD.postValue(film)
            Log.d("NewsDetailsViewModel", film.name)
        }
    }


}