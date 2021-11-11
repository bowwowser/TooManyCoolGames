package com.example.toomanycoolgames.data.db

import androidx.lifecycle.LiveData
import com.example.toomanycoolgames.data.Result
import com.example.toomanycoolgames.data.model.TMKGGame
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import com.example.toomanycoolgames.data.model.TMKGReleaseDate

interface DBWrapper {
    fun observeAllTrackedGames(): LiveData<Result<List<TMKGGameRelease>>>
    suspend fun isInfoCached(apiId: Long): Boolean
    fun observeGameRelease(apiId: Long): LiveData<Result<TMKGGameRelease>>
    suspend fun cacheInfoAndReleaseDates(game: TMKGGame, releaseDates: List<TMKGReleaseDate>)
    suspend fun updateTrackStatus(gameId: Long, nowTracked: Boolean)
    suspend fun updatePlayStatus(gameId: Long, playStatusPosition: Int)
    suspend fun updateNotes(gameId: Long, notes: String)
}