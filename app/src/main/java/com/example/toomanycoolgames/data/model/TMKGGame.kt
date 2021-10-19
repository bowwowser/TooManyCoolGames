package com.example.toomanycoolgames.data.model

import androidx.room.*

@Entity
data class TMKGGame(
    @PrimaryKey(autoGenerate = true) val gameId: Long,
    @ColumnInfo(name = "is_tracked") val isTracked: Boolean,
    @ColumnInfo(name = "api_id") val apiId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: Int,
    @ColumnInfo(name = "cover_id") val coverId: String,
    @ColumnInfo(name = "summary") val summary: String,
    @ColumnInfo(name = "notes") val notes: String,
    @ColumnInfo(name = "play_status_position") val playStatusPosition: Int
)

data class TMKGGameRelease(
    @Embedded val game: TMKGGame,
    @Relation(
        parentColumn = "api_id",
        entityColumn = "api_game_id"
    )
    val releaseDates: List<TMKGReleaseDate>
)

@Entity
data class TMKGReleaseDate(
    @PrimaryKey(autoGenerate = true) val rdId: Long,
    @ColumnInfo(name = "api_game_id") val apiGameId: Long,
    @ColumnInfo(name = "platform_name") val platformName: String,
    @ColumnInfo(name = "release_date_human") val releaseDateHuman: String, // millis
    @ColumnInfo(name = "region_ordinal") val regionOrdinal: Int,
)
