package com.example.toomanycoolgames.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.toomanycoolgames.data.model.TMKGGame
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import com.example.toomanycoolgames.data.model.TMKGReleaseDate
import kotlinx.coroutines.flow.Flow

@Dao
interface TMKGGameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheGameInfo(game: TMKGGame): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheReleaseDate(game: TMKGReleaseDate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheInfoAndReleaseDates(
        game: TMKGGame,
        releaseDates: List<TMKGReleaseDate>
    )

    @Transaction
    @Query("SELECT * FROM TMKGGame")
    fun getAllCachedGames(): Flow<List<TMKGGameRelease>>

    @Transaction
    @Query("SELECT * FROM TMKGGame WHERE is_tracked = 1")
    fun getAllTrackedGames(): Flow<List<TMKGGameRelease>>

    @Transaction
    @Query("SELECT * FROM TMKGGame WHERE is_tracked = 1")
    fun observeAllTrackedGames(): LiveData<List<TMKGGameRelease>>

    @Transaction
    @Query("SELECT * FROM TMKGGame where api_id = :apiId")
    fun observeGameRelease(apiId: Long): LiveData<TMKGGameRelease>

    @Transaction
    @Query("SELECT * FROM TMKGGame where api_id = :apiId")
    fun getGameRelease(apiId: Long): TMKGGameRelease

    @Query("SELECT EXISTS(SELECT * FROM TMKGGame WHERE api_id = :apiId)")
    suspend fun isInfoCached(apiId: Long): Boolean

    @Query("SELECT * FROM TMKGGame WHERE api_id = :apiId")
    fun getGameInfo(apiId: Long): LiveData<TMKGGame>

    @Query("UPDATE TMKGGame SET is_tracked = :isNowTracked WHERE api_id = :gameId")
    suspend fun updateTrackStatus(gameId: Long, isNowTracked: Boolean)

    @Query("UPDATE TMKGGame SET play_status_position = :playStatusPosition WHERE api_id = :gameId")
    suspend fun updatePlayStatus(gameId: Long, playStatusPosition: Int)

    @Query("UPDATE TMKGGame SET notes = :notes WHERE api_id = :gameId")
    suspend fun updateNotes(gameId: Long, notes: String)

    @Query("DELETE FROM TMKGGame")
    suspend fun untrackAllGames()
}