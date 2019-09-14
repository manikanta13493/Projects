package com.nike.urbandictionary.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nike.urbandictionary.models.UrbanDictionaryDefinition;

@Database(entities = {UrbanDictionaryDefinition.class}, version = 1)
public abstract class UrbanDictionaryDatabase extends RoomDatabase {
    private static UrbanDictionaryDatabase INSTANCE;

    public abstract UrbanDictionaryDao urbanDictionaryDao();

    public static UrbanDictionaryDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UrbanDictionaryDatabase.class, "dictionary-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
