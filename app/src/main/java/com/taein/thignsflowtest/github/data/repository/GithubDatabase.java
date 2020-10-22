package com.taein.thignsflowtest.github.data.repository;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.taein.thignsflowtest.github.data.dao.GithubRepoDao;
import com.taein.thignsflowtest.github.data.entity.GithubIssue;
import com.taein.thignsflowtest.github.data.entity.GithubRepo;

@Database(entities = {GithubRepo.class, GithubIssue.class}, version = 2)
public abstract class GithubDatabase extends RoomDatabase {
    public abstract GithubRepoDao ParcelInfoDao();
    private static GithubDatabase INSTANCE;
    private static final Object sync = new Object();
    private static Context context;

    public static GithubDatabase getInstance(Context context) {
        GithubDatabase.context = context;

        if (INSTANCE == null) {
            synchronized (sync) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            GithubDatabase.class, "GithubDatabase.db")
                            .fallbackToDestructiveMigration()
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
