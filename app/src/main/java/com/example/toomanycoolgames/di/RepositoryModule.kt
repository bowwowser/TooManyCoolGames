package com.example.toomanycoolgames.di

import android.content.Context
import androidx.room.Room
import com.example.toomanycoolgames.data.GameRepository
import com.example.toomanycoolgames.data.room.TMKGDatabase
import com.example.toomanycoolgames.data.room.TMKGGameDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun providesGameRepository(tmkgGameDao: TMKGGameDao): GameRepository {
        return GameRepository(tmkgGameDao)
    }

    @Singleton
    @Provides
    fun providesGameDao(database: TMKGDatabase): TMKGGameDao {
        return database.gameDao()
    }

    @Singleton
    @Provides
    fun providesGameDatabase(@ApplicationContext context: Context): TMKGDatabase {
        return Room.databaseBuilder(context, TMKGDatabase::class.java, "games")
            .fallbackToDestructiveMigration()
            .build()
    }
}

// TODO sort out callback management
//class TMKGDatabaseCallback(
//    private val scope: CoroutineScope
//) : RoomDatabase.Callback() {
//
//    override fun onCreate(db: SupportSQLiteDatabase) {
//        super.onCreate(db)
//        INSTANCE?.let { tmkgDatabase ->
//            scope.launch {
//                clearDatabase(tmkgDatabase.gameDao())
//            }
//        }
//    }
//
//    /**
//     * Clear database of all previous entries.
//     */
//    suspend fun clearDatabase(gameDao: TMKGGameDao) {
//        gameDao.untrackAllGames()
//    }
//}