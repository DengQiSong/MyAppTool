package com.hyh.www.common.app;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.hyh.www.common.config.config;
import com.hyh.www.common.module.DaoMaster;
import com.hyh.www.common.module.DaoSession;

/**
 * 作者：Denqs on 2017/2/27.
 */

public class BaseApplication extends Application {
    public static BaseApplication instances;
    //数据库
    private MySQLiteOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    //判断是否在前台
    public static int isforeground;//0后台
    //判断是否被强制杀死 在登陆页赋值
    public static String isKill;

    public static BaseApplication getInstances() {
        return instances;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        setDatabase();
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                isforeground++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                isforeground--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        MigrationHelper.DEBUG=true;
        mHelper = new MySQLiteOpenHelper(this, config.db_name, null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }
}
