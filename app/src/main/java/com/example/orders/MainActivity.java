package com.example.orders;



import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import com.example.orders.*;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    String mAddHunsu;
    String mAddDish;
    String mAddSideDish;
    private static final String DATABASE_NAME = "OrderDatabase.db";
    private static final String TABLE_NAME = "orders";
    private static final String COLUMN_HUNSU = "hunsu";
    private static final String COLUMN_DISHNAME = "dishname";
    private static final String COLUMN_MADEOF = "madeof";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);




    }

    public void click(View view) {

        TextView textView = findViewById(R.id.textView);
        TextView textView2 = findViewById(R.id.textView2);

        db = dbHelper.getReadableDatabase();

        String strMaxHun = "";
        String strMinHun = "";
        String strSu = "";
        String strOldMaxHun = "";
        String strOldMinHun = "";
        String strOldSu = "";


        String query = "SELECT * FROM OLDDISH";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            // 显示数据或进行其他操作
            strOldMaxHun = cursor.getString(cursor.getColumnIndex("MAXHUN"));
            strOldMinHun = cursor.getString(cursor.getColumnIndex("MINHUN"));
            strOldSu = cursor.getString(cursor.getColumnIndex("SU"));
            Log.d("OLDDISH",  strOldMaxHun + strOldMinHun + strOldSu);
        }
        cursor.close();


        query = "SELECT * FROM " + TABLE_NAME+ " WHERE " +
                COLUMN_HUNSU + " = 'MAXHUN'" + "ORDER BY RANDOM() LIMIT 1";
        cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            // 显示数据或进行其他操作
            while (strOldMaxHun.equals(cursor.getString(cursor.getColumnIndex(COLUMN_DISHNAME)))){
                cursor.moveToNext();
            }
            String hunsu = cursor.getString(cursor.getColumnIndex(COLUMN_HUNSU));
            String dishname = cursor.getString(cursor.getColumnIndex(COLUMN_DISHNAME));
            String madeof = cursor.getString(cursor.getColumnIndex(COLUMN_MADEOF));
            Log.d("RandomDish",  hunsu + dishname + madeof);
            strMaxHun = dishname + "\n" + madeof;
            strOldMaxHun = dishname;

        }
        cursor.close();

        query = "SELECT * FROM " + TABLE_NAME+ " WHERE " +
                COLUMN_HUNSU + " = 'MINHUN'" + "ORDER BY RANDOM() LIMIT 1";
        cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (strOldMinHun.equals(cursor.getString(cursor.getColumnIndex(COLUMN_DISHNAME)))){
                cursor.moveToNext();
            }
            // 显示数据或进行其他操作
            String hunsu = cursor.getString(cursor.getColumnIndex(COLUMN_HUNSU));
            String dishname = cursor.getString(cursor.getColumnIndex(COLUMN_DISHNAME));
            String madeof = cursor.getString(cursor.getColumnIndex(COLUMN_MADEOF));
            Log.d("RandomDish",  hunsu + dishname + madeof);
            strMinHun = dishname + "\n" + madeof;

            strOldMinHun = dishname;
        }

        cursor.close();

        query = "SELECT * FROM " + TABLE_NAME+ " WHERE " +
                COLUMN_HUNSU + " = 'SU'" + "ORDER BY RANDOM() LIMIT 1";
        cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (strOldSu.equals(cursor.getString(cursor.getColumnIndex(COLUMN_DISHNAME)))){
                cursor.moveToNext();
            }
            // 显示数据或进行其他操作
            String hunsu = cursor.getString(cursor.getColumnIndex(COLUMN_HUNSU));
            String dishname = cursor.getString(cursor.getColumnIndex(COLUMN_DISHNAME));
            String madeof = cursor.getString(cursor.getColumnIndex(COLUMN_MADEOF));
            Log.d("RandomDish",  hunsu + dishname + madeof);
            strSu = dishname + "\n" + madeof;

            strOldSu = dishname;
        }


        // 关闭Cursor和数据库连接
        cursor.close();




        db.close();

        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MAXHUN", strOldMaxHun);
        values.put("MINHUN", strOldMinHun);
        values.put("SU", strOldSu);

        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{"1"};
        int rowsUpdated = db.update(
                "OLDDISH",
                values,
                whereClause,
                whereArgs);

        if (rowsUpdated > 0) {
            // 更新成功
            Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
        } else {
            // 更新失败或没有找到匹配的记录
            Toast.makeText(this, "更新失败或未找到记录", Toast.LENGTH_SHORT).show();
        }

        db.close(); // 记得关闭数据库连接

        Date date = new Date();
        String a = new SimpleDateFormat("yyyy-MM-dd").format(date);

        textView.setText(a + " 的菜单");
        textView.setGravity(Gravity.CENTER);

        textView2.setText("大荤：\n\n" + strMaxHun + "\n\n" + "小荤：\n\n" + strMinHun + "\n\n" + "素：\n\n" + strSu);
        textView2.setGravity(Gravity.CENTER);

        Button button = findViewById(R.id.button);
        button.setText("换一波 ^^");

    }

    public void onClickAddDish(View view) {
        Intent intent = new Intent(MainActivity.this, AddDishs.class);
        startActivity(intent);
    }

}