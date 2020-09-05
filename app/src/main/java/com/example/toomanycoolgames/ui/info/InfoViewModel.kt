package com.example.toomanycoolgames.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.toomanycoolgames.data.IGDBRepository
import com.example.toomanycoolgames.data.Result
import proto.Game

class InfoViewModel(
    state: SavedStateHandle
) : ViewModel() {
    private val gameRepository = IGDBRepository()
    private val gameId: Long = state["gameId"] ?: throw IllegalArgumentException("Missing game id")
    val gameInfo: LiveData<Result<Game>> = liveData {
        emit(gameRepository.getGameInfo(gameId))
    }
}