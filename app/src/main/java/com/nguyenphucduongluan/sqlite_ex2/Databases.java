package com.nguyenphucduongluan.sqlite_ex2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Databases extends SQLiteOpenHelper {
    public static final String DB_NAME = "films.db";
    public static final int DB_VERSION = 1;

    public static final String TBL_NAME = "film";
    public static final String COL_CODE = "film_code";
    public static final String COL_NAME = "film_name";
    public static final String COL_PRICE = "film_price";
    public static MainActivity activity;

    public Databases(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
         String sql =
                "CREATE TABLE IF NOT EXISTS " + TBL_NAME +
                        " ( " + COL_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_NAME + " VARCHAR(50), " +
                        COL_PRICE + " REAL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(db);
    }
    //Thực hiện câu lẹnh select
    public Cursor querryData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    //Thực hiện câu lệnh insert, update, delete

    public boolean execSql(String sql){
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
            return true;
        }
        catch (Exception e) {
            Log.e("Error: ", e.toString());
            return false;
        }
    }
    public int numbOfRows() {
       Cursor c =  querryData(" SELECT * FROM " + TBL_NAME);
       int numbOfRows = c.getCount();
       c.close();
       return numbOfRows;
    }


    public void createSampleData() {
        if( numbOfRows() == 0){
            execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Conan: Thám tử lừng danh', 45000)");
            execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Doraemon: Chú người máy đến từ tương lai', 45000)");
            execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Bảy viên ngọc rồng', 45000)");
            execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Thủy thủ mặt trăng', 45000)");
            execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Vua trò chơi Yugioh', 45000)");
            execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Harry Potter', 45000)");
            execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Con mèo đi hia', 45000)");
        }
    }
}
