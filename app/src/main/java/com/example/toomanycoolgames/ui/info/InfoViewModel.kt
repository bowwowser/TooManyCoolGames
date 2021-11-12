package com.example.toomanycoolgames.ui.info

import androidx.lifecycle.*
import com.example.toomanycoolgames.data.TMKGGameRepository
import com.example.toomanycoolgames.data.computeResult
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import com.example.toomanycoolgames.logDebug
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val repository: TMKGGameRepository,
    state: SavedStateHandle
) : ViewModel() {

    private val gameId: Long = state["gameId"] ?: throw IllegalArgumentException("Missing game id")

    private var _isExpanded = MutableLiveData(false)
    val isExpanded: LiveData<Boolean> = _isExpanded
    val gameInfo: LiveData<TMKGGameRelease?> = liveData {
        emitSource(repository.observeGameRelease(gameId).map { computeResult(it) { showError() } })
    }

    fun trackGameRelease() = viewModelScope.launch {
//        TODO null fix
        gameInfo.value?.let { game ->
            val isNowTracked = game.game.isTracked.not()
            repository.changeTrackStatus(gameId, isNowTracked)
        }
    }

    fun expandSummary() {
        _isExpanded.value = isExpanded.value?.not()
    }

    fun updateNotes(notes: CharSequence) = viewModelScope.launch {
        // TODO add some proper debounce logic
        logDebug { "Updating notes (${notes.subSequence(0, minOf(notes.length, 15))}...)" }
        repository.updateNotes(gameId, notes.toString())
    }

    fun changePlayStatus(
        selectedStatusPosition: Int
    ) = viewModelScope.launch {
        logDebug { "Status selected ($selectedStatusPosition)" }
        if (selectedStatusPosition != 0
            && selectedStatusPosition != gameInfo.value?.game?.playStatusPosition
        ) {
            repository.changePlayStatus(gameId, selectedStatusPosition)
        }
    }

    private fun showError() {
        TODO("Not yet implemented")
    }
}