package com.example.orders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "OrderDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "orders";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_HUNSU = "hunsu";
    private static final String COLUMN_DISHNAME = "dishname";
    private static final String COLUMN_MADEOF = "madeof";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_HUNSU + " TEXT NOT NULL,"
                + COLUMN_DISHNAME + " TEXT NOT NULL,"
                + COLUMN_MADEOF + " TEXT NOT NULL" + ")";
        db.execSQL(CREATE_TABLE);

        String INIT_TABLE = new StringBuilder().
                append("INSERT INTO ").
                append(TABLE_NAME).
                append(" (").
                append(COLUMN_HUNSU).append(" ,").
                append(COLUMN_DISHNAME).append(" ,").
                append(COLUMN_MADEOF).
                append(" )").
                append(" VALUES").
                append(" ('MAXHUN', '红烧肉', '五花肉/芝麻')").append(" ,").
                append(" ('MAXHUN', '糖醋排骨', '排骨/芝麻')").append(" ,").
                append(" ('MAXHUN', '红烧排骨', '排骨/小葱/香料')").append(" ,").
                append(" ('MAXHUN', '红烧鸡腿', '鸡腿/小葱/香料')").append(" ,").
                append(" ('MAXHUN', '凉拌鸡腿肉', '鸡腿/小葱/香菜')").append(" ,").
                append(" ('MAXHUN', '红烧鸡块', '整鸡切块/青红椒/葱/姜/蒜')").append(" ,").
                append(" ('MAXHUN', '香葱鸡块', '鸡腿/大葱/芝麻')").append(" ,").
                append(" ('MAXHUN', '甜肉', '五花肉/冰糖')").append(" ,").
                append(" ('MAXHUN', '红烧带鱼', '带鱼/干辣椒/葱/姜/蒜')").append(" ,").
                append(" ('MINHUN', '青椒炒肉片', '青椒/肥瘦相间的猪肉/葱/姜')").append(" ,").
                append(" ('MINHUN', '青椒虾仁', '青椒/大虾/葱/姜')").append(" ,").
                append(" ('MINHUN', '油焖虾', '大虾/小葱/小葱')").append(" ,").
                append(" ('MINHUN', '番茄炒蛋', '西红柿/鸡蛋/小葱')").append(" ,").
                append(" ('MINHUN', '芹菜肉丝', '芹菜/肥瘦相间的猪肉/葱/姜')").append(" ,").
                append(" ('MINHUN', '爆炒鱿鱼须', '鱿鱼须/青椒/洋葱/黑胡椒/蒜')").append(" ,").
                append(" ('MINHUN', '蒸鸡蛋', '鸡蛋/小葱')").append(" ,").
                append(" ('SU', '丝瓜毛豆', '丝瓜/毛豆/葱')").append(" ,").
                append(" ('SU', '青椒土豆丝', '青椒/土豆丝/葱/姜/蒜')").append(" ,").
                append(" ('SU', '煎豆腐', '老豆腐/葱/姜')").append(" ,").
                append(" ('SU', '清炒茼蒿', '茼蒿/葱/蒜')").append(" ,").
                append(" ('SU', '蒜泥菠菜', '菠菜/葱/蒜')").append(" ,").
                append(" ('SU', '清炒空心菜', '空心菜/葱/蒜')").append(" ,").
                append(" ('SU', '芹菜香干', '芹菜/香干/葱/蒜')").append(" ,").
                append(" ('SU', '清炒四季豆', '四季豆/葱/蒜')").append(" ,").
                append(" ('SU', '蚝油生菜', '生菜/蚝油')").
                toString();
        try{
            db.execSQL(INIT_TABLE);
        }catch (Exception e)
        {
            Log.e("Error", "An error occurred: " + e.getMessage());
        }


        db.execSQL("create table OLDDISH (ID INTEGER PRIMARY KEY AUTOINCREMENT, MAXHUN TEXT NOT NULL, MINHUN text NOT NULL, SU TEXT NOT NULL)");
        db.execSQL("INSERT INTO OLDDISH (MAXHUN, MINHUN, SU) VALUES ('红烧肉', '青椒炒肉片', '蚝油生菜')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // 插入菜单的方法
    public void addDish(String hunsu, String dishname, String madeof) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_HUNSU, hunsu);
        values.put(COLUMN_DISHNAME, dishname);
        values.put(COLUMN_MADEOF, madeof);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // 查询菜单的方法
    public boolean queryOrders(String dishname) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_DISHNAME + " = '" + dishname + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return true;
        else
            return false;
    }
}