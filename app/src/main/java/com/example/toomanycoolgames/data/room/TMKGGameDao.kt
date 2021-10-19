package com.example.toomanycoolgames.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.toomanycoolgames.data.model.TMKGGame
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import com.example.toomanycoolgames.data.model.TMKGReleaseDate
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TMKGGameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun cacheGameInfo(game: TMKGGame): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun cacheReleaseDate(game: TMKGReleaseDate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun cacheInfoAndReleaseDates(
        game: TMKGGame,
        releaseDates: List<TMKGReleaseDate>
    )

    @Transaction
    @Query("SELECT * FROM TMKGGame WHERE is_tracked = 1")
    abstract fun getAllTrackedGames(): Flow<List<TMKGGameRelease>>

    @Transaction
    @Query("SELECT * FROM TMKGGame where api_id = :apiId")
    abstract fun getGameReleases(apiId: Long): LiveData<TMKGGameRelease>

    @Query("SELECT EXISTS(SELECT * FROM TMKGGame WHERE api_id = :apiId)")
    abstract suspend fun isInfoCached(apiId: Long): Boolean

    @Query("SELECT * FROM TMKGGame WHERE api_id = :apiId")
    abstract fun getGameInfo(apiId: Long): LiveData<TMKGGame>

    @Query("UPDATE TMKGGame SET is_tracked = :isNowTracked WHERE api_id = :gameId")
    abstract suspend fun updateTrackStatus(gameId: Long, isNowTracked: Boolean)

    @Query("UPDATE TMKGGame SET play_status_position = :playStatusPosition WHERE api_id = :gameId")
    abstract suspend fun updatePlayStatus(gameId: Long, playStatusPosition: Int)

    @Query("UPDATE TMKGGame SET notes = :notes WHERE api_id = :gameId")
    abstract suspend fun updateNotes(gameId: Long, notes: String)

    @Query("DELETE FROM TMKGGame")
    abstract suspend fun untrackAllGames()
}