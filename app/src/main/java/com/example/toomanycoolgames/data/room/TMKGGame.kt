package com.example.toomanycoolgames.data.room

import androidx.room.*

@Entity
data class TMKGGame(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "is_tracked") val isTracked: Boolean,
    @ColumnInfo(name = "igdb_id") val igdbId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "cover_id") val coverId: String,
    @ColumnInfo(name = "summary") val summary: String,

    )

data class TMKGGameWithReleaseDates(
    @Embedded val game: TMKGGame,
    @Relation(
        parentColumn = "id",
        entityColumn = "game_id"
    )
    val releaseDates: List<TMKGReleaseDate>
)

@Entity
data class TMKGReleaseDate(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "game_id") val gameId: Long,
    @ColumnInfo(name = "platform_name") val platformName: String,
    @ColumnInfo(name = "release_date_human") val releaseDateHuman: String, // millis
//    @ColumnInfo(name = "release_date") val releaseDate: Long, // millis
)
