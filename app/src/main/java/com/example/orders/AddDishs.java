package com.example.orders;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddDishs extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    private Context context;
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
        setContentView(R.layout.activity_add_dishs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView textViewMessage = findViewById(R.id.textInputEditText);
        TextView textViewMessage2 = findViewById(R.id.textInputEditText2);
        Button buttonPositive = findViewById(R.id.buttonPositive);
        Button buttonNegative = findViewById(R.id.buttonNegative);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        dbHelper = new DatabaseHelper(this);
        context = this;

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton2)
                    mAddHunsu = "MAXHUN";
                else if(checkedId == R.id.radioButton3)
                    mAddHunsu = "MINHUN";
                else if(checkedId == R.id.radioButton4)
                    mAddHunsu = "SU";
            }
        });

        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(textViewMessage.getText()) || TextUtils.isEmpty(textViewMessage2.getText())){
                    Toast.makeText(context,"输入不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAddDish = textViewMessage.getText().toString();
                mAddSideDish = textViewMessage2.getText().toString();
                db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                if(mAddHunsu == null){
                    mAddHunsu = "MAXHUN";
                }
                values.put(COLUMN_HUNSU, mAddHunsu);
                values.put(COLUMN_DISHNAME, mAddDish);
                values.put(COLUMN_MADEOF, mAddSideDish);

                db.insert(TABLE_NAME, null, values);
                db.close();
                finish();
            }
        });
        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}