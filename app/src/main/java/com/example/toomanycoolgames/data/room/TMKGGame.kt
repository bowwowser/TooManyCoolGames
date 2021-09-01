package com.example.toomanycoolgames.data.room

import androidx.room.*

@Entity
data class TMKGGame(
    @PrimaryKey(autoGenerate = true) val gameId: Long,
    @ColumnInfo(name = "is_tracked") val isTracked: Boolean,
    @ColumnInfo(name = "igdb_id") val igdbId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "cover_id") val coverId: String,
    @ColumnInfo(name = "summary") val summary: String,

    )

data class TMKGGameWithReleaseDates(
    @Embedded val game: TMKGGame,
    @Relation(
        parentColumn = "gameId",
        entityColumn = "game_fk"
    )
    val releaseDates: List<TMKGReleaseDate>
)

@Entity
data class TMKGReleaseDate(
    @PrimaryKey(autoGenerate = true) val rdId: Long,
    @ColumnInfo(name = "game_fk") val gameFk: Long,
    @ColumnInfo(name = "platform_name") val platformName: String,
    @ColumnInfo(name = "release_date_human") val releaseDateHuman: String, // millis
    @ColumnInfo(name = "region_ordinal") val regionOrdinal: Int,
//    @ColumnInfo(name = "release_date") val releaseDate: Long, // millis
)
