package com.example.toomanycoolgames.di

import android.content.Context
import androidx.room.Room
import com.example.toomanycoolgames.data.GameRepository
import com.example.toomanycoolgames.data.TMKGGameRepository
import com.example.toomanycoolgames.data.api.ApiWrapper
import com.example.toomanycoolgames.data.api.IGDBApiWrapper
import com.example.toomanycoolgames.data.db.DBWrapper
import com.example.toomanycoolgames.data.db.RoomDBWrapper
import com.example.toomanycoolgames.data.db.TMKGDatabase
import com.example.toomanycoolgames.data.db.TMKGGameDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    private val dotenv = dotenv {
        directory = "./assets" // dot at start is IMPORTANT
        filename = "env"
    }

    @Provides
    @Named("clientId")
    fun providesClientId(): String = dotenv["IGDB_CLIENT_ID"]

    @Provides
    @Named("accessToken")
    fun providesAccessToken(): String = dotenv["IGDB_ACCESS_TOKEN"]

    @Singleton
    @Provides
    fun providesTMKGGameRepository(
        dbWrapper: RoomDBWrapper,
        apiWrapper: IGDBApiWrapper
    ): GameRepository {
        return TMKGGameRepository(dbWrapper, apiWrapper)
    }

    @Singleton
    @Provides
    fun providesRoomWrapper(gameDao: TMKGGameDao): DBWrapper = RoomDBWrapper(gameDao)

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
    fun providesIGDBWrapper(
        @Named("clientId") clientId: String,
        @Named("accessToken") accessToken: String
    ): ApiWrapper = IGDBApiWrapper(clientId, accessToken)

    @Provides
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.IO
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