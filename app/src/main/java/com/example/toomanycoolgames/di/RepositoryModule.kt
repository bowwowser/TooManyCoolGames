package com.example.toomanycoolgames.di

import android.content.Context
import androidx.room.Room
import com.example.toomanycoolgames.data.TMKGGameRepository
import com.example.toomanycoolgames.data.api.IGDBApiWrapper
import com.example.toomanycoolgames.data.db.RoomDBWrapper
import com.example.toomanycoolgames.data.db.TMKGDatabase
import com.example.toomanycoolgames.data.db.TMKGGameDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    private val IGDB_CLIENT_ID = "***REMOVED***"
    private val IGDB_ACCESS_TOKEN = "***REMOVED***"

    @Singleton
    @Provides
    fun providesGameRepository(
        dbWrapper: RoomDBWrapper,
        apiWrapper: IGDBApiWrapper
    ): TMKGGameRepository {
        return TMKGGameRepository(dbWrapper, apiWrapper)
    }

    @Singleton
    @Provides
    fun providesRoomWrapper(gameDao: TMKGGameDao): RoomDBWrapper = RoomDBWrapper(gameDao)

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

    @Singleton
    @Provides
    fun providesIGDBWrapper(): IGDBApiWrapper = IGDBApiWrapper(IGDB_CLIENT_ID, IGDB_ACCESS_TOKEN)
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