package com.example.toomanycoolgames.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.toomanycoolgames.data.GameRepository
import com.example.toomanycoolgames.data.computeResult
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    gameRepository: GameRepository
) : ViewModel() {

    val trackedGames = gameRepository.allTrackedGames.map {
        computeResult(it) { showError() }
        return@map emptyList<TMKGGameRelease>()
    }

    private fun showError() {
        TODO("Not yet implemented")
    }
}
