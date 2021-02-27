package com.example.anchorbooks.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ClassBooks::class, ClassDetail::class], version = 1)
abstract class BaseDatos: RoomDatabase() {
    abstract fun getBooksDAO(): BooksDao
    companion object{
        @Volatile
        private var INSTANCE: BaseDatos? = null
        fun getDataBase(context: Context): BaseDatos {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    BaseDatos::class.java, "basedatos")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}