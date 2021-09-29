package com.example.toomanycoolgames.ui.search

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.*
import com.api.igdb.exceptions.RequestException
import com.example.toomanycoolgames.R
import com.example.toomanycoolgames.data.GameRepository
import com.example.toomanycoolgames.data.TMKGResult
import com.example.toomanycoolgames.data.isMainGame
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import proto.Game
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @SuppressLint("StaticFieldLeak") @ApplicationContext private val context: Context,
    private val gameRepository: GameRepository
) : ViewModel() {

    private val _searchResults = MutableSharedFlow<List<Game>>(replay = 1)
    val searchResults: SharedFlow<List<Game>> = _searchResults.asSharedFlow()

    val gamesResults: Flow<List<Game>> = searchResults.map { it.filter(isMainGame) }
    val otherResults: Flow<List<Game>> = searchResults.map { it.filterNot(isMainGame) }

    val gamesTabTitle = gamesResults.map { context.getString(R.string.search_tab_games, it.size) }.asLiveData()
    val othersTabTitle = otherResults.map { context.getString(R.string.search_tab_dlc_others, it.size) }.asLiveData()

    private val _searchException = MutableLiveData<RequestException>()
    val searchException: LiveData<RequestException> = _searchException

    init {
        _searchResults.tryEmit(emptyList())
    }

    fun searchForGames(query: String) = viewModelScope.launch {
        when (val results = gameRepository.searchIgdbForGames(query)) {
            is TMKGResult.Success -> _searchResults.tryEmit(results.data)
            is TMKGResult.Error -> _searchException.postValue(results.exception)
        }
    }
}
