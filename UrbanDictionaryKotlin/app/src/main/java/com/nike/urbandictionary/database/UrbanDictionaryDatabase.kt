package com.nike.urbandictionary.database

import android.app.Application
import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.nike.urbandictionary.models.UrbanDictionaryDefinition
import com.nike.urbandictionary.networking.UrbanDictionaryRepository

@Database(entities = [UrbanDictionaryDefinition::class], version = 1)
abstract class UrbanDictionaryDatabase : RoomDatabase() {

    abstract fun urbanDictionaryDao(): UrbanDictionaryDao

    companion object {
        private var INSTANCE: UrbanDictionaryDatabase? = null

        fun getInstance(context: Context): UrbanDictionaryDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, UrbanDictionaryDatabase::class.java, "dictionary-database")
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE!!
        }
    }
}
