package com.example.toomanycoolgames.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.toomanycoolgames.data.Result
import com.example.toomanycoolgames.data.Result.Success
import com.example.toomanycoolgames.data.model.TMKGGame
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import com.example.toomanycoolgames.data.model.TMKGReleaseDate

class FakeDBWrapper(
    private val localGames: MutableMap<Long, TMKGGameRelease>
) : DBWrapper {

    private val observableGames = MutableLiveData<List<TMKGGameRelease>>()

    private fun refreshObservableGames() {
        observableGames.value = localGames.values.toList()
    }

    override fun observeAllTrackedGames(): LiveData<Result<List<TMKGGameRelease>>> {
        refreshObservableGames()
        return observableGames.map { Success(it) }
    }

    override suspend fun isInfoCached(apiId: Long): Boolean {
        return localGames.containsKey(apiId)
    }

    override fun observeGameRelease(apiId: Long): LiveData<Result<TMKGGameRelease>> {
        refreshObservableGames()
        return observableGames.map { result -> Success(result.first { it.game.apiId == apiId }) }
    }

    override suspend fun cacheInfoAndReleaseDates(
        game: TMKGGame,
        releaseDates: List<TMKGReleaseDate>
    ) {
        localGames[game.apiId] = TMKGGameRelease(game, releaseDates)
    }

    override suspend fun updateTrackStatus(gameId: Long, nowTracked: Boolean) {
        saveUpdates(gameId, localGames.getValue(gameId).let {
            it.copy(it.game.copy(isTracked = nowTracked))
        })
    }

    override suspend fun updatePlayStatus(gameId: Long, playStatusPosition: Int) {
        saveUpdates(gameId, localGames.getValue(gameId).let {
            it.copy(it.game.copy(playStatusPosition = playStatusPosition))
        })
    }

    override suspend fun updateNotes(gameId: Long, notes: String) {
        saveUpdates(gameId, localGames.getValue(gameId).let {
            it.copy(it.game.copy(notes = notes))
        })
    }

    private fun saveUpdates(gameId: Long, newRelease: TMKGGameRelease) {
        localGames[gameId] = newRelease
        refreshObservableGames()
    }
}
