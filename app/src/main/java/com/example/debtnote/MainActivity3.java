package com.example.debtnote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    TextView txtDayDetail,txtCustomerDetail;
    ListView lvDebtDetail;
    AdapterQppforMain3 adapterQppforMain3;
    ArrayList<Qpp> listQpp;
    SQLiteDatabase DebtDatabase;
    DebtSQLiteHeper DebtDatabaseHeper;
    String customer;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main3);
        handleDatabase();
        addControls();
        addEvents();

    }

    private void handleDatabase() {
        DebtDatabaseHeper = new DebtSQLiteHeper(MainActivity3.this);

        DebtDatabase = DebtDatabaseHeper.getWritableDatabase();

    }


    @Override
    protected void onPause() {
        super.onPause();
        DebtDatabaseHeper.close();
    }

    private void addControls() {
        txtDayDetail = findViewById(R.id.txtDayDetail);
        txtCustomerDetail = findViewById(R.id.txtCustomerDetail);
        lvDebtDetail = findViewById(R.id.lvDebtDetail);
    }

    private void addEvents() {
        Intent intent = getIntent();
        customer=intent.getStringExtra("CUSTOMER");
        date=intent.getStringExtra("DEBTDAY");
        txtCustomerDetail.setText(customer);
        txtDayDetail.setText(date);
        listQpp = getListQpp();
        adapterQppforMain3 = new AdapterQppforMain3(MainActivity3.this, R.layout.quantity_product_price, listQpp);
        lvDebtDetail.setAdapter(adapterQppforMain3);
    }

    private ArrayList<Qpp> getListQpp() {
        listQpp = new ArrayList<>();
        Cursor cursor = DebtDatabase.rawQuery("Select * from Orders WHERE Time = '"+this.date+"' AND CustomerName = '"+this.customer+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Qpp qpp = new Qpp(cursor.getString(4),cursor.getString(3),cursor.getString(5));
            listQpp.add(qpp);
            cursor.moveToNext();
        }
        return listQpp;
    }

    public void actionRemoveQpp(Qpp qpp)
    {
        String quantity = qpp.getQuantaty();
        String product = qpp.getProduct();
        String price = qpp.getPrice();
        String s = "DELETE FROM Orders WHERE Time = '"+this.date+"' AND CustomerName = '"+this.customer+"' AND ProductName = '"+product+"' AND Quantity = '"+quantity+"' AND Price = '"+price+"'";
        DebtDatabase.execSQL(s);
        refreshListQpp();

    }

    private void refreshListQpp() {
        listQpp = getListQpp();
        adapterQppforMain3 = new AdapterQppforMain3(MainActivity3.this, R.layout.quantity_product_price, listQpp);
        lvDebtDetail.setAdapter(adapterQppforMain3);
    }

}