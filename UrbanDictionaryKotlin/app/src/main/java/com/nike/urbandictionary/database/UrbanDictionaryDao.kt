package com.nike.urbandictionary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.nike.urbandictionary.models.UrbanDictionaryDefinition

@Dao
interface UrbanDictionaryDao {

    @Query("SELECT * FROM urbandictionary where word LIKE  :word ")
    fun getDefinitions(word: String): List<UrbanDictionaryDefinition>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDefinition(definitions: List<UrbanDictionaryDefinition>)

    @Query("DELETE FROM urbandictionary")
    fun deleteAllDefinitions()
}
