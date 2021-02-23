package com.example.toomanycoolgames.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toomanycoolgames.data.GameRepository
import com.example.toomanycoolgames.data.TMKGResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import proto.Game
import javax.inject.Inject

typealias IGDBResult = TMKGResult<List<Game>>

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {

    private var _searchResults = MutableLiveData<IGDBResult>()
    val searchResults: LiveData<IGDBResult> = _searchResults

    fun searchForGames(query: String) = viewModelScope.launch {
        _searchResults.value = gameRepository.searchIgdbForGames(query)
    }
}
