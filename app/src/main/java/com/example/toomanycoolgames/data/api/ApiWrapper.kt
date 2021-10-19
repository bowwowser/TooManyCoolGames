package com.example.toomanycoolgames.data.api

import com.api.igdb.exceptions.RequestException
import com.example.toomanycoolgames.data.model.TMKGGameRelease

sealed class ApiResult<out R> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: RequestException) : ApiResult<Nothing>()
}

interface ApiWrapper {
    suspend fun getGameReleaseByApiId(apiId: Long): ApiResult<TMKGGameRelease>

    suspend fun getGameReleasesBySearchQuery(query: String): ApiResult<List<TMKGGameRelease>>
}