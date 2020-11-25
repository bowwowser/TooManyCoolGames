package com.example.toomanycoolgames.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.toomanycoolgames.data.GameRepository
import com.example.toomanycoolgames.data.Result
import proto.Game

class HomeViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {

    val searchResults: LiveData<Result<List<Game>>> = liveData {
        emit(gameRepository.searchIgdbForGames("Hades"))
    }
}

class HomeViewModelFactory(private val gameRepository: GameRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(gameRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}