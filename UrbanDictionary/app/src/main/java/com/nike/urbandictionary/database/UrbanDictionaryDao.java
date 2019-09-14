package com.nike.urbandictionary.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nike.urbandictionary.models.UrbanDictionaryDefinition;

import java.util.List;

@Dao
public interface UrbanDictionaryDao {

    @Query("SELECT * FROM urbandictionary where word LIKE  :word ")
    List<UrbanDictionaryDefinition> getDefinitions(String word);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertDefinition(List<UrbanDictionaryDefinition> definitions);

    @Query("DELETE FROM urbandictionary")
    abstract void deleteAllDefinitions();
}
