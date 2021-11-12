package com.example.toomanycoolgames.ui.search

import android.content.Context
import androidx.lifecycle.*
import com.example.toomanycoolgames.R
import com.example.toomanycoolgames.data.Result.Error
import com.example.toomanycoolgames.data.Result.Success
import com.example.toomanycoolgames.data.TMKGGameRepository
import com.example.toomanycoolgames.data.isMainGame
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val gameRepository: TMKGGameRepository
) : ViewModel() {

    private val _searchResults = MutableSharedFlow<List<TMKGGameRelease>>(replay = 1)
    val searchResults: SharedFlow<List<TMKGGameRelease>> = _searchResults.asSharedFlow()

    val gamesResults: Flow<List<TMKGGameRelease>> = searchResults.map { it.filter(isMainGame) }
    val otherResults: Flow<List<TMKGGameRelease>> = searchResults.map { it.filterNot(isMainGame) }

    val gamesTabTitle = gamesResults.map { context.getString(R.string.search_tab_games, it.size) }.asLiveData()
    val othersTabTitle = otherResults.map { context.getString(R.string.search_tab_dlc_others, it.size) }.asLiveData()

    private val _searchException = MutableLiveData<Exception>()
    val searchException: LiveData<Exception> = _searchException

    init {
        _searchResults.tryEmit(emptyList())
    }

    fun searchForGames(query: String) = viewModelScope.launch {
        when (val results = gameRepository.searchApiForGames(query)) {
            is Success -> _searchResults.tryEmit(results.data)
            is Error -> _searchException.postValue(results.exception)
        }
    }
}
