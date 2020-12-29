package com.nan.jetpackprimer.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nan.jetpackprimer.BaseApplication

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private val db: AppDatabase by lazy {
            Room.databaseBuilder(
                BaseApplication.context,
                AppDatabase::class.java, "database-name"
            ).build()
        }

        fun userDao(): UserDao {
            return db.userDao()
        }
    }

}