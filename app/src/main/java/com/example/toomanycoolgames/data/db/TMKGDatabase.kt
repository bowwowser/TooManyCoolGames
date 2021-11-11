package com.example.toomanycoolgames.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.toomanycoolgames.data.model.TMKGGame
import com.example.toomanycoolgames.data.model.TMKGReleaseDate

@Database(entities = [TMKGGame::class, TMKGReleaseDate::class], version = 10, exportSchema = false)
abstract class TMKGDatabase : RoomDatabase() {

    abstract fun gameDao(): TMKGGameDao
}
