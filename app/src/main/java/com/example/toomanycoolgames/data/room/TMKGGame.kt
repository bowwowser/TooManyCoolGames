package com.example.toomanycoolgames.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TMKGGame(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "is_tracked") val isTracked: Boolean,
    @ColumnInfo(name = "igdb_id") val igdbId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "cover_id") val coverId: String,
    @ColumnInfo(name = "summary") val summary: String
)