package com.example.packettracer.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BordoreauQRDTO.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BordoreauDao bordoreauDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "bordoreau_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
