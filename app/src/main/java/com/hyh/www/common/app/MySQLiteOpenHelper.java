package com.hyh.www.common.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.hyh.www.common.model.gen.DaoMaster;
import com.hyh.www.common.model.gen.UserDao;

/**
 * 作者：Denqs on 2017/2/27.
 * 实现安全升级
 */

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db,UserDao.class);
    }
}
