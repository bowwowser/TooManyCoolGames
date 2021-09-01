package com.example.toomanycoolgames.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TMKGGame::class, TMKGReleaseDate::class], version = 5, exportSchema = false)
abstract class TMKGDatabase : RoomDatabase() {

    abstract fun gameDao(): TMKGGameDao
}
