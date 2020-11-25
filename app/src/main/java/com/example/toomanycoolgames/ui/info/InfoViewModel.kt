package com.example.toomanycoolgames.ui.info

import android.app.Application
import androidx.lifecycle.*
import com.example.toomanycoolgames.TMKGApplication
import com.example.toomanycoolgames.data.room.TMKGGame
import kotlinx.coroutines.launch

class InfoViewModel(
    application: Application,
    state: SavedStateHandle
) : AndroidViewModel(application) {

    private val repository = (application as TMKGApplication).repository
    private val gameId: Long = state["gameId"] ?: throw IllegalArgumentException("Missing game id")

    val gameInfo: LiveData<TMKGGame> = liveData {
        emitSource(repository.getGameInfo(gameId))
    }

    fun onTrackButtonPressed() = viewModelScope.launch {
//        TODO null fix
        gameInfo.value?.let { game ->
            val isNowTracked = game.isTracked.not()
            repository.changeGameTrackStatus(gameId, isNowTracked)
        }
    }
}