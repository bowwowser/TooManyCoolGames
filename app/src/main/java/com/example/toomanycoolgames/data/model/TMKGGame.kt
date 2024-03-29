package com.example.toomanycoolgames.data.model

import androidx.room.*
import proto.GameCategoryEnum
import proto.RegionRegionEnum

@Entity
data class TMKGGame(
    @PrimaryKey(autoGenerate = true) val gameId: Long = 0,
    @ColumnInfo(name = "is_tracked") val isTracked: Boolean = false,
    @ColumnInfo(name = "api_id") val apiId: Long = -1,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "category") val category: Int = GameCategoryEnum.MAIN_GAME.number,
    @ColumnInfo(name = "cover_id") val coverId: String = "",
    @ColumnInfo(name = "summary") val summary: String = "",
    @ColumnInfo(name = "notes") val notes: String = "",
    @ColumnInfo(name = "play_status_position") val playStatusPosition: Int = 0
)

data class TMKGGameRelease(
    @Embedded val game: TMKGGame = TMKGGame(),
    @Relation(
        parentColumn = "api_id",
        entityColumn = "api_game_id"
    )
    val releaseDates: List<TMKGReleaseDate> = emptyList()
)

@Entity
data class TMKGReleaseDate(
    @PrimaryKey(autoGenerate = true) val rdId: Long = 0,
    @ColumnInfo(name = "api_game_id") val apiGameId: Long = -1,
    @ColumnInfo(name = "platform_name") val platformName: String = "",
    @ColumnInfo(name = "release_date_human") val releaseDateHuman: String = "", // millis
    @ColumnInfo(name = "region_ordinal") val regionOrdinal: Int = RegionRegionEnum.REGION_REGION_NULL.ordinal,
)
