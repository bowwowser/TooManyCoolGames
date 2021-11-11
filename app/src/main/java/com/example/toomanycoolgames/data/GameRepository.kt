package com.example.toomanycoolgames.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.toomanycoolgames.data.model.TMKGGameRelease

interface GameRepository {
    val allTrackedGames: LiveData<Result<List<TMKGGameRelease>>>

    /**
     * Get search results for a query string.
     */
    suspend fun searchApiForGames(query: String): Result<List<TMKGGameRelease>>

    suspend fun observeGameRelease(apiId: Long): LiveData<Result<TMKGGameRelease>>

    /**
     * Changes the track status for the provided game id.
     *
     * @param gameId IGDB game id
     */
    @WorkerThread
    suspend fun changeTrackStatus(gameId: Long, isNowTracked: Boolean)

    /**
     * Changes the play status for the provided game id.
     *
     * @param gameId IGDB game id
     */
    @WorkerThread
    suspend fun changePlayStatus(gameId: Long, playStatusPosition: Int)

    /**
     * Updates the notes for the provided game id.
     *
     * @param gameId IGDB game id
     */
    @WorkerThread
    suspend fun updateNotes(gameId: Long, notes: String)
}