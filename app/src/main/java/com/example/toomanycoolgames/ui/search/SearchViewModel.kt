package com.example.toomanycoolgames.ui.search

import androidx.lifecycle.*
import com.example.toomanycoolgames.data.GameRepository
import com.example.toomanycoolgames.data.Result
import kotlinx.coroutines.launch
import proto.Game

typealias IGDBResult = Result<List<Game>>

class SearchViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {

    private var _searchResults = MutableLiveData<IGDBResult>()
    val searchResults: LiveData<IGDBResult> = _searchResults

    fun searchForGames(query: String) = viewModelScope.launch {
        _searchResults.value = gameRepository.searchIgdbForGames(query)
    }
}

class SearchViewModelFactory(private val gameRepository: GameRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(gameRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}