package com.fortesfilmes.service;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.fortesfilmes.dao.MovieDao;
import com.fortesfilmes.model.MovieModel;

@Database(entities = MovieModel.class, version = 1)
public abstract class RoomDB extends RoomDatabase {

    private static final String DB_NAME = "movies_db";
    private static RoomDB instance;

    public static synchronized RoomDB getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract MovieDao movieDao();
}
