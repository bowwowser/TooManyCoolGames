package com.example.toomanycoolgames

import android.app.Application
import com.example.toomanycoolgames.data.GameRepository
import com.example.toomanycoolgames.data.room.TMKGDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TMKGApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { TMKGDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { GameRepository(database.gameDao()) }
}