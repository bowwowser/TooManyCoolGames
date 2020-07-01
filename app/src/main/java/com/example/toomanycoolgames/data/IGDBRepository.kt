package com.example.toomanycoolgames.data

import APICalypse
import IGDBWrapper
import RequestException
import android.util.Log
import games
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import proto.Game

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class IGDBRepository {
    private val IGDB_KEY = "7e6eee9293a968a5273b626d3c4b65ea"

    /**
     * Get search results for a query string
     */
    suspend fun getSearchResults(
        query: String
    ): Result<List<Game>> {
        IGDBWrapper.userkey = IGDB_KEY
        return withContext(Dispatchers.IO) {
            val apiCalypse = APICalypse().search(query).fields("name,cover.image_id")
            try {
                val searchResult: List<Game> = IGDBWrapper.games(apiCalypse)
                Log.d("HomeFragment", "!!! Size: ${searchResult.size}")
                return@withContext Result.Success(
                    searchResult
                )
            } catch (e: RequestException) {
                Log.e("HomeFragment", "Error fetching Bowser games: ${e.message}", e)
                return@withContext Result.Error(
                    e
                )
            }
        }
    }
}