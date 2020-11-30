package com.example.toomanycoolgames.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.toomanycoolgames.data.GameRepository
import com.example.toomanycoolgames.data.Result
import proto.Game

class SearchViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {

    val searchResults: LiveData<Result<List<Game>>> = liveData {
        emit(gameRepository.searchIgdbForGames("Hades"))
    }
}

class SearchViewModelFactory(private val gameRepository: GameRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(gameRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}