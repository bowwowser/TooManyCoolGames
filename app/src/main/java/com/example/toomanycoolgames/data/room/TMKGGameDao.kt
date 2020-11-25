package com.example.toomanycoolgames.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TMKGGameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheGameInfo(game: TMKGGame)

    @Query("SELECT EXISTS(SELECT * FROM TMKGGame WHERE igdb_id = :igdbId)")
    suspend fun isGameInfoCached(igdbId: Long): Boolean

    @Query("SELECT * FROM TMKGGame WHERE igdb_id = :igdbId")
    fun getGameInfo(igdbId: Long): LiveData<TMKGGame>

    @Query("UPDATE TMKGGame SET is_tracked = :isNowTracked WHERE igdb_id = :gameId")
    suspend fun updateGameTrackStatus(gameId: Long, isNowTracked: Boolean)

    @Query("DELETE FROM TMKGGame")
    suspend fun untrackAllGames()
}