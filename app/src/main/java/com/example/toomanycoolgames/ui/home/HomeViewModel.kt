package com.example.toomanycoolgames.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.toomanycoolgames.data.IGDBRepository
import com.example.toomanycoolgames.data.Result
import proto.Game

class HomeViewModel(
    private val igdbRepository: IGDBRepository = IGDBRepository()
) : ViewModel() {

    val searchResults: LiveData<Result<List<Game>>> = liveData {
        emit(igdbRepository.getSearchResults("Bowser"))
    }
}