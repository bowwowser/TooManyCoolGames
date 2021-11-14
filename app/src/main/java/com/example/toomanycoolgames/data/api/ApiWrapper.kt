package com.example.toomanycoolgames.data.api

import com.example.toomanycoolgames.data.Result
import com.example.toomanycoolgames.data.model.TMKGGameRelease

interface ApiWrapper {
    suspend fun getGameReleaseByApiId(apiId: Long): Result<TMKGGameRelease>

    suspend fun getGameReleasesBySearchQuery(query: String): Result<List<TMKGGameRelease>>
}