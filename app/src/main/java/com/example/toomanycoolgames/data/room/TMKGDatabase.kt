package com.example.toomanycoolgames.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [TMKGGame::class], version = 1, exportSchema = false)
abstract class TMKGDatabase : RoomDatabase() {
    abstract fun gameDao(): TMKGGameDao

    companion object {
        /**
         * Singleton instance for our `RoomDatabase` instance.
         */
        @Volatile
        private var INSTANCE: TMKGDatabase? = null

        /**
         * Get the `RoomDatabase` instance, or initialize it if uninitialized.
         * @param context Android context for constructing DB instance.
         * @param scope Scope for running DB initialization queries.
         */
        fun getDatabase(context: Context, scope: CoroutineScope): TMKGDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TMKGDatabase::class.java,
                    "games"
                )
                    .addCallback(TMKGDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    /**
     * Callback when DB gets created.
     * @param scope Used for running Room DAO queries.
     */
    private class TMKGDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { tmkgDatabase ->
                scope.launch {
                    clearDatabase(tmkgDatabase.gameDao())
                }
            }
        }

        /**
         * Clear database of all previous entries.
         */
        suspend fun clearDatabase(gameDao: TMKGGameDao) {
            gameDao.untrackAllGames()
        }
    }
}
