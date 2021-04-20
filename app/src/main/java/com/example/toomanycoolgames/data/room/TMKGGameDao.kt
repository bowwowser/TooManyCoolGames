package com.example.toomanycoolgames.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TMKGGameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheGameInfo(game: TMKGGame): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheReleaseDate(game: TMKGReleaseDate)

    @Query("SELECT * FROM TMKGGame WHERE is_tracked = 1")
    fun getAllTrackedGames(): Flow<List<TMKGGame>>

    @Transaction
    @Query(
        """
        SELECT *
        FROM TMKGGame g
        INNER JOIN TMKGReleaseDate r
        ON g.gameId = r.game_fk
        WHERE g.igdb_id = :igdbId
    """
    )
    fun getGameWithReleasesDates(igdbId: Long): LiveData<TMKGGameWithReleaseDates>

    @Query("SELECT EXISTS(SELECT * FROM TMKGGame WHERE igdb_id = :igdbId)")
    suspend fun isGameInfoCached(igdbId: Long): Boolean

    @Query("SELECT * FROM TMKGGame WHERE igdb_id = :igdbId")
    fun getGameInfo(igdbId: Long): LiveData<TMKGGame>

    @Query("UPDATE TMKGGame SET is_tracked = :isNowTracked WHERE igdb_id = :gameId")
    suspend fun updateGameTrackStatus(gameId: Long, isNowTracked: Boolean)

    @Query("DELETE FROM TMKGGame")
    suspend fun untrackAllGames()
}