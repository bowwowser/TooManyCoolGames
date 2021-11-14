package com.example.toomanycoolgames.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.toomanycoolgames.data.TMKGGameRepository
import com.example.toomanycoolgames.data.computeResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    gameRepository: TMKGGameRepository
) : ViewModel() {

    val trackedGames = gameRepository.allTrackedGames.map { computeResult(it) { showError() } }

    private fun showError() {
        TODO("Not yet implemented")
    }
}
