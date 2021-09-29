package com.example.toomanycoolgames.ui.info

import androidx.lifecycle.*
import com.example.toomanycoolgames.data.GameRepository
import com.example.toomanycoolgames.data.room.TMKGGameWithReleaseDates
import com.example.toomanycoolgames.logDebug
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val repository: GameRepository,
    state: SavedStateHandle
) : ViewModel() {

    private val gameId: Long = state["gameId"] ?: throw IllegalArgumentException("Missing game id")

    private var _isExpanded = MutableLiveData<Boolean>(false)
    val isExpanded: LiveData<Boolean> = _isExpanded
    val gameInfo: LiveData<TMKGGameWithReleaseDates> = liveData {
        emitSource(repository.getGameInfoWithReleaseDates(gameId))
    }

    fun onTrackButtonPressed() = viewModelScope.launch {
//        TODO null fix
        gameInfo.value?.let { game ->
            val isNowTracked = game.game.isTracked.not()
            repository.changeGameTrackStatus(gameId, isNowTracked)
        }
    }

    fun onSummaryExpandPressed() {
        _isExpanded.value = isExpanded.value?.not()
    }

    fun onNotesUpdated(notes: CharSequence) = viewModelScope.launch {
        // TODO add some proper debounce logic
        logDebug { "Updating notes (${notes.subSequence(0, minOf(notes.length, 15))}...)" }
        repository.updateGameNotes(gameId, notes.toString())
    }

    fun onStatusSelected(
        selectedStatusPosition: Int
    ) = viewModelScope.launch {
        logDebug { "Status selected ($selectedStatusPosition)" }
        if (selectedStatusPosition != 0
            && selectedStatusPosition != gameInfo.value?.game?.playStatusPosition
        ) {
            repository.changeGamePlayStatus(gameId, selectedStatusPosition)
        }
    }
}