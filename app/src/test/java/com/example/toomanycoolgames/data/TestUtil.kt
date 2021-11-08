package com.example.toomanycoolgames.data

import com.example.toomanycoolgames.data.model.TMKGGame
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import com.example.toomanycoolgames.data.model.TMKGReleaseDate
import proto.Game
import proto.GameCategoryEnum
import proto.RegionRegionEnum

fun createGameRelease(gameId: Long, apiId: Long): TMKGGameRelease {
    return TMKGGameRelease(
        game = createGame(gameId, apiId),
        releaseDates = arrayOf<Long>(1, 2, 3).map { createReleaseDate(it, gameId) }.toList()
    )
}

fun createGame(id: Long, apiId: Long): TMKGGame = TMKGGame(
    gameId = id,
    isTracked = true,
    apiId = apiId,
    name = TEST_GAME_NAME,
    category = GameCategoryEnum.MAIN_GAME_VALUE,
    coverId = TEST_GAME_COVER_ID,
    summary = "some dummy text",
    notes = "some dummy text",
    playStatusPosition = 0 // redefine to a better constant
)

fun createReleaseDate(id: Long, gameId: Long): TMKGReleaseDate = TMKGReleaseDate(
    rdId = id,
    apiGameId = gameId,
    platformName = "Dummy",
    releaseDateHuman = "2020-02-02",
    regionOrdinal = RegionRegionEnum.WORLDWIDE_VALUE
)

fun createApiGame(apiId: Long): Game = Game.newBuilder(Game.getDefaultInstance())
    .setId(apiId)
    .build()

const val TEST_GAME_NAME = "test"
const val TEST_GAME_COVER_ID: String = "" // find a semi-valid one to test with? or just dummy text?