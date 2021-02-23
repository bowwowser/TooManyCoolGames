package com.example.toomanycoolgames.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.toomanycoolgames.data.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    gameRepository: GameRepository
) : ViewModel() {

    val trackedGames = gameRepository.allTrackedGames.asLiveData()
}
