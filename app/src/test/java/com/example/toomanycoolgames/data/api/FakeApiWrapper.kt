package com.example.toomanycoolgames.data.api

import com.example.toomanycoolgames.data.Result
import com.example.toomanycoolgames.data.Result.Error
import com.example.toomanycoolgames.data.Result.Success
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import java.util.stream.Collectors

class FakeApiWrapper(
    private val serviceGames: Map<Long, TMKGGameRelease>
) : ApiWrapper {

    override suspend fun getGameReleaseByApiId(apiId: Long): Result<TMKGGameRelease> {
        serviceGames[apiId]?.let {
            return Success(it)
        }
        return Error(Exception("Game not found"))
    }

    override suspend fun getGameReleasesBySearchQuery(query: String): Result<List<TMKGGameRelease>> {
        return Success(
            serviceGames.values.stream()
                .filter { it.game.name.contains(query) }
                .collect(Collectors.toList())
        )
    }
}

class NoOpApiWrapper : ApiWrapper {
    override suspend fun getGameReleaseByApiId(apiId: Long): Result<TMKGGameRelease> {
        return Success(TMKGGameRelease())
    }

    override suspend fun getGameReleasesBySearchQuery(query: String): Result<List<TMKGGameRelease>> {
        return Success(emptyList())
    }

}