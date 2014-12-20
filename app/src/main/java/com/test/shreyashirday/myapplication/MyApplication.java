package com.test.shreyashirday.myapplication;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import shreyashirday.DaoMaster;
import shreyashirday.DaoSession;

/**
 * Created by shreyashirday on 12/18/14.
 */
public class MyApplication extends Application {

    public DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "example-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
