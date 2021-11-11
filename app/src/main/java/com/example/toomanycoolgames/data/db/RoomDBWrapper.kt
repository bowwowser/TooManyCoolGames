package com.example.toomanycoolgames.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.toomanycoolgames.data.Result
import com.example.toomanycoolgames.data.Result.Success
import com.example.toomanycoolgames.data.model.TMKGGame
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import com.example.toomanycoolgames.data.model.TMKGReleaseDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RoomDBWrapper @Inject constructor(
    private val tmkgGameDao: TMKGGameDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DBWrapper {

    override fun observeAllTrackedGames(): LiveData<Result<List<TMKGGameRelease>>> {
        return tmkgGameDao.observeAllTrackedGames().map { Success(it) }
    }

    override suspend fun isInfoCached(apiId: Long): Boolean {
        return tmkgGameDao.isInfoCached(apiId)
    }

    override fun observeGameRelease(apiId: Long): LiveData<Result<TMKGGameRelease>> {
        return tmkgGameDao.observeGameRelease(apiId).map { Success(it) }
    }

    override suspend fun cacheInfoAndReleaseDates(
        game: TMKGGame,
        releaseDates: List<TMKGReleaseDate>
    ) {
        return tmkgGameDao.cacheInfoAndReleaseDates(game, releaseDates)
    }

    override suspend fun updateTrackStatus(gameId: Long, nowTracked: Boolean) {
        return tmkgGameDao.updateTrackStatus(gameId, nowTracked)
    }

    override suspend fun updatePlayStatus(gameId: Long, playStatusPosition: Int) {
        return tmkgGameDao.updatePlayStatus(gameId, playStatusPosition)
    }

    override suspend fun updateNotes(gameId: Long, notes: String) {
        return tmkgGameDao.updateNotes(gameId, notes)
    }
}