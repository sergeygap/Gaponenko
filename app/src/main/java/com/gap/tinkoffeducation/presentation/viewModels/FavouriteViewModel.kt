package com.gap.tinkoffeducation.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gap.tinkoffeducation.data.repository.FilmsRepositoryImpl
import com.gap.tinkoffeducation.domain.entity.Films
import com.gap.tinkoffeducation.domain.usecases.GetListFilms
import kotlinx.coroutines.launch

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FilmsRepositoryImpl(application)
    private val getListUseCase = GetListFilms(repository)
    private var page = 1

    private val _filmsLD = MutableLiveData<List<Films>>()

    val filmsLD: LiveData<List<Films>>
        get() = _filmsLD
    private val _filmsStateLD = MutableLiveData<Boolean>()

    val filmsStateLD: LiveData<Boolean>
        get() = _filmsStateLD

    fun getListFilms() {
        viewModelScope.launch {
            if (getListUseCase(page).isNotEmpty()) {
                _filmsStateLD.postValue(true)
                val loadedNewsList = filmsLD.value?.toMutableList()
                if (loadedNewsList != null) {
                    loadedNewsList.addAll(getListUseCase(page))
                    _filmsLD.postValue(loadedNewsList)
                } else {
                    _filmsLD.postValue(getListUseCase(page))
                }
                page++
            } else {
                _filmsStateLD.postValue(false)
            }

        }
    }

    fun updateFilmsList() {
        page = 1
        _filmsLD.value = emptyList()
        getListFilms()
    }


}