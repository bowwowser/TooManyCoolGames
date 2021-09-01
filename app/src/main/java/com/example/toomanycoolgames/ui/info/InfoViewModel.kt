package com.example.toomanycoolgames.ui.info

import androidx.lifecycle.*
import com.example.toomanycoolgames.data.GameRepository
import com.example.toomanycoolgames.data.room.TMKGGameWithReleaseDates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val repository: GameRepository,
    state: SavedStateHandle
) : ViewModel() {

    private val debounceInterval = 500L
    private var lastEditTime = 0L

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

    @Suppress("UNUSED_PARAMETER")
    fun onNotesUpdated(
        notes: CharSequence,
        start: Int, before: Int, count: Int
    ) = viewModelScope.launch {
        // TODO extract debounce logic?
        val time = System.currentTimeMillis()
        if (time - lastEditTime >= debounceInterval) {
            lastEditTime = time
            repository.updateGameNotes(gameId, notes.toString())
        }
    }
}