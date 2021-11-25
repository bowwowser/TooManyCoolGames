package com.example.toomanycoolgames.data.api

import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.games
import com.example.toomanycoolgames.data.Result
import com.example.toomanycoolgames.data.Result.Error
import com.example.toomanycoolgames.data.Result.Success
import com.example.toomanycoolgames.data.model.TMKGGame
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import com.example.toomanycoolgames.data.model.TMKGReleaseDate
import com.example.toomanycoolgames.logError
import io.github.cdimascio.dotenv.dotenv
import proto.Game
import proto.ReleaseDate
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class IGDBApiWrapper @Inject constructor(
    @Named("clientId") clientId: String,
    @Named("accessToken") accessToken: String
) : ApiWrapper {

    init {
        IGDBWrapper.setCredentials(clientId, accessToken)
    }

    override suspend fun getGameReleaseByApiId(apiId: Long): Result<TMKGGameRelease> {
        return try {
            val game = IGDBWrapper.games(gameInfoQuery(apiId)).first()
            Success(game.toTMKGGameRelease())
        } catch (e: RequestException) {
            logError(e) { "Error querying IGDB game [$apiId]: ${e.message}" }
            Error(e)
        }
    }

    override suspend fun getGameReleasesBySearchQuery(query: String): Result<List<TMKGGameRelease>> {
        return try {
            val games = IGDBWrapper.games(gameSearchQuery(query))
            Success(games.map { it.toTMKGGameRelease() })
        } catch (e: RequestException) {
            logError(e) { "Error searching IGDB for \"$query\": [${e.statusCode}] ${e.result}" }
            Error(e)
        }
    }

    private fun gameInfoQuery(id: Long): APICalypse {
        return APICalypse().fields(Fields.gameInfoFields)
            .where("id = $id")
    }

    private fun gameSearchQuery(query: String): APICalypse {
        return APICalypse().fields(Fields.gameSearchFields)
            .search(query)
    }
}

fun Game.toTMKGGameRelease(): TMKGGameRelease {
    return TMKGGameRelease(
        game = toTMKGGame(),
        releaseDates = releaseDatesList.map { it.toTMKGReleaseDate(gameId = id) }.toList()
    )
}

fun Game.toTMKGGame(): TMKGGame = TMKGGame(
    apiId = id,
    name = name,
    category = category.number,
    coverId = cover.imageId,
    summary = summary
)

fun ReleaseDate.toTMKGReleaseDate(gameId: Long): TMKGReleaseDate = TMKGReleaseDate(
    apiGameId = gameId,
    platformName = platform.name,
    releaseDateHuman = human,
    regionOrdinal = region.ordinal
)

enum class Fields(val value: String) {
    ALL("*"),
    NAME("name"),
    CATEGORY("category"),
    COVER_IMAGE_ID("cover.image_id"),
    RELEASE_DATE_HUMAN("release_dates.human"),
    RELEASE_DATE_PLATFORM_NAME("release_dates.platform.name"),
    RELEASE_DATE_REGION("release_dates.region")
    ;

    companion object {
        val gameInfoFields
            get() = arrayOf(
                ALL.value,
                COVER_IMAGE_ID.value,
                CATEGORY.value,
                RELEASE_DATE_HUMAN.value,
                RELEASE_DATE_PLATFORM_NAME.value,
                RELEASE_DATE_REGION.value
            ).joinToString()
        val gameSearchFields
            get() = arrayOf(
                NAME.value,
                COVER_IMAGE_ID.value,
                CATEGORY.value
            ).joinToString()
    }
}

fun checkApiTokenFreshness(): String {
    val dotenv = dotenv {
        directory = "./assets"
        filename = "env"
    }
    val regeneratedDate = dotenv["IGDB_REGENERATED_TIMESTAMP"].toLong()
    val expiresIn = dotenv["IGDB_EXPIRES_SEC"].toLong()
    val expireDate = (regeneratedDate + expiresIn) * 1000
    val currentMillis = Calendar.getInstance().timeInMillis

    return if (currentMillis >= expireDate) {
        "API Key expired"
    } else {
        "Access Token expires in ${(expireDate - currentMillis) / 86_400_000} days"
    }
}