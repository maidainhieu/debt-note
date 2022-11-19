package com.example.debtnote;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DebtSQLiteHeper extends SQLiteOpenHelper {

    public DebtSQLiteHeper(Context context) {
        super(context, "DEBT", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE CustormerName(Name TEXT PRIMARY KEY )");
        sqLiteDatabase.execSQL("CREATE TABLE ProductPrice(ProductName TEXT PRIMARY KEY, Price TEXT NOT NULL )");
        sqLiteDatabase.execSQL("CREATE TABLE Orders(Id INTEGER PRIMARY KEY AUTOINCREMENT, Time TEXT NOT NULL, CustomerName TEXT NOT NULL, ProductName TEXT NOT NULL, Quantity TEXT NOT NULL, Price TEXT NOT NULL )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST CustormerName");
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST ProductPrice");
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST Orders");
        onCreate(sqLiteDatabase);
    }
}
