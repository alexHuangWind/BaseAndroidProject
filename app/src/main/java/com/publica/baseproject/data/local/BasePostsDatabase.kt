package com.publica.baseproject.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.publica.baseproject.data.local.dao.PostsDao

abstract class BasePostsDatabase : RoomDatabase() {

    abstract fun getPostsDao(): PostsDao

    companion object {
        const val DB_NAME = "foodium_database"

        @Volatile
        private var INSTANCE: BasePostsDatabase? = null

        fun getInstance(context: Context): BasePostsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BasePostsDatabase::class.java,
                    DB_NAME
                ).addMigrations(*DatabaseMigrations.MIGRATIONS).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}