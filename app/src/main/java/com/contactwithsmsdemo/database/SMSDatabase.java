package com.contactwithsmsdemo.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import com.contactwithsmsdemo.BuildConfig;
import com.contactwithsmsdemo.model.Sms;

@Database(entities = {Sms.class},
        version = BuildConfig.VERSION_CODE, exportSchema = false)

public abstract class
SMSDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "SMSDB";

    public abstract DaoAccess daoAccess();

    private static SMSDatabase mInstance;

    @Ignore
    public static SMSDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context, SMSDatabase.class, DATABASE_NAME)
                    .addMigrations(MIGRATION_OLD_TO_NEW_VERSION)
                    .fallbackToDestructiveMigration().build();
        }
        return mInstance;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static final Migration MIGRATION_OLD_TO_NEW_VERSION = new Migration(BuildConfig.VERSION_CODE, BuildConfig.VERSION_CODE) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

          /*  database.execSQL("CREATE TABLE IF NOT EXISTS AtCheckinData(slNo INTEGER PRIMARY KEY AUTOINCREMENT,jsonString TEXT, lattitude TEXT, longitude TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS AutoCheckoutOfflineData(slNo INTEGER PRIMARY KEY AUTOINCREMENT,jsonString TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS AutoCheckoutOfflineData(slNo INTEGER PRIMARY KEY AUTOINCREMENT,jsonString TEXT)");*/

        }
    };

}