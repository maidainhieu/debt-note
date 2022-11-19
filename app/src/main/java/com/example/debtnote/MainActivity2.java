package com.example.debtnote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ForegroundServiceStartNotAllowedException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Collectors;

public class MainActivity2 extends AppCompatActivity {
    float sumtotal = 0;
    String datefordeletedetaildeb;
    TextView txtDebtDay,txtDebtSum;
    ImageButton btnAddDebt,btnFind, btnDay, btnAddCustormerOrProduct;

    EditText txtQuantatyProduct,txtCustomerAdd,txtProductAdd,txtUnitPrice;
    ArrayList<Qpp> listQpp;
    AdapterQpp adapterQpp;
    ListView lvQpp;


    ArrayList<DP> listDP;
    AdapterDP adapterDP;
    ListView lvDP;


    ArrayList<NP> listNP;
    AdapterNP adapterNP;
    ListView lvNP;

    ArrayList<PP> listPP;
    AdapterPP adapterPP;
    ListView lvListProduct;



    ArrayList<String> listCustomerName;
    AdapterCustomer adapterCustomer;
    ListView lvListCustomer;

    SQLiteDatabase DebtDatabase;
    DebtSQLiteHeper DebtDatabaseHeper;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<String> listNameCustomer;
    ArrayList<String> listNameProduct;
    ArrayAdapter<String> arrayAdapterListNameCustomer;
    ArrayAdapter<String> arrayAdapterListNameProduct;
    AutoCompleteTextView autotxtDebtNameAdd,autotxtDebtProduct,autotxtDebtNameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main2);
        handleDatabase();
        addControls();
        addEvents();


    }

    private void handleDatabase() {
        DebtDatabaseHeper = new DebtSQLiteHeper(MainActivity2.this);

        DebtDatabase = DebtDatabaseHeper.getWritableDatabase();

    }

    private void addControls() {



        autotxtDebtNameAdd = findViewById(R.id.autotxtDebtNameAdd);
        autotxtDebtProduct = findViewById(R.id.autotxtDebtProduct);
        autotxtDebtNameEdit = findViewById(R.id.autotxtDebtNameEdit);

        refreshListCustomer();
        refreshListProduct();
        txtDebtDay = findViewById(R.id.txtDebtDay);
        txtQuantatyProduct = findViewById(R.id.txtQuantatyProduct);
        txtProductAdd = findViewById(R.id.txtProductAdd);
        txtUnitPrice = findViewById(R.id.txtUnitPrice);
        txtCustomerAdd = findViewById(R.id.txtCustomerAdd);
        txtDebtSum = findViewById(R.id.txtDebtSum);

        btnDay = findViewById(R.id.btnDay);
        btnAddDebt = findViewById(R.id.btnAddDebt);
        btnFind = findViewById(R.id.btnFind);
        btnAddCustormerOrProduct = findViewById(R.id.btnAddCustormerOrProduct);
        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
       // tab1.setIndicator("Nợ mới");
        tab1.setIndicator("",getResources().getDrawable(R.drawable.loan));
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);


        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("",getResources().getDrawable(R.drawable.find2));
       // tab2.setIndicator("Tìm nợ");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("t3");
        //tab3.setIndicator("Xem nợ");
        tab3.setIndicator("",getResources().getDrawable(R.drawable.see));
        tab3.setContent(R.id.tab3);
        tabHost.addTab(tab3);

        TabHost.TabSpec tab4 = tabHost.newTabSpec("t4");
       // tab4.setIndicator("Thêm");
        tab4.setIndicator("",getResources().getDrawable(R.drawable.add2));
        tab4.setContent(R.id.tab4);
        tabHost.addTab(tab4);

        lvQpp = findViewById(R.id.lvQpp);


        txtDebtDay.setText(dayFormat.format(calendar.getTime()));

        //////////////////////////////////////
       actionNP();
       actionPP();
       actionNameCustomer();
    }

    private void refreshListProduct()
    {
        listNameProduct = getListNameProduct();
        arrayAdapterListNameProduct = new ArrayAdapter<String>(
                MainActivity2.this,
                android.R.layout.simple_list_item_1,
                listNameProduct);
        autotxtDebtProduct.setAdapter(arrayAdapterListNameProduct);
    }
    private void refreshListCustomer()
    {
        listNameCustomer = getListNameCustomer();
        arrayAdapterListNameCustomer = new ArrayAdapter<String>(
                MainActivity2.this,
                android.R.layout.simple_list_item_1,
                listNameCustomer);
        autotxtDebtNameAdd.setAdapter(arrayAdapterListNameCustomer);
        autotxtDebtNameEdit.setAdapter(arrayAdapterListNameCustomer);

    }

    private void actionPP() {
        lvListProduct = findViewById(R.id.lvListProduct);
        listPP = getListPP();
        adapterPP = new AdapterPP(MainActivity2.this,R.layout.product_price,listPP);
        lvListProduct.setAdapter(adapterPP);
    }

    private void actionNameCustomer(){

        lvListCustomer = findViewById(R.id.lvListCustomer);
        listCustomerName = getListCustomerName();
        adapterCustomer = new AdapterCustomer(MainActivity2.this,R.layout.customer,listCustomerName);
        lvListCustomer.setAdapter(adapterCustomer);
    }

    private ArrayList<String> getListCustomerName() {
        ArrayList<String> listCustomerName = new ArrayList<>();
        Cursor cursor = DebtDatabase.rawQuery("SELECT * from CustormerName",null);
        if (cursor==null)
        {
            return null;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            listCustomerName.add(cursor.getString(0));
            cursor.moveToNext();
        }

        return listCustomerName;
    }

    private ArrayList<PP> getListPP() {
        ArrayList<PP> listPP = new ArrayList<>();

        Cursor cursor = DebtDatabase.rawQuery("SELECT * from ProductPrice",null);
        if (cursor==null)
        {
            return null;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            PP pp = new PP(cursor.getString(0),cursor.getString(1));
            listPP.add(pp);
            cursor.moveToNext();
        }

        return listPP;

    }

    private void actionNP() {
        lvNP = findViewById(R.id.lvNP);
        listNP = getlistNP();
        adapterNP = new AdapterNP(MainActivity2.this, R.layout.name_price,listNP);
        lvNP.setAdapter(adapterNP);
    }




    private ArrayList<NP> getlistNP() {
        ArrayList<NP> listNP = new ArrayList<>();

        Cursor cursor = DebtDatabase.rawQuery("SELECT CustomerName, sum(Price) as [Total] from Orders Group by CustomerName",null);
        if (cursor==null)
        {
            return null;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            NP np = new NP(cursor.getString(0),cursor.getString(1));
            listNP.add(np);
            cursor.moveToNext();
        }

        return listNP;

    }

    private ArrayList<Qpp> getlistQpp() {
        sumtotal = 0;
        ArrayList<Qpp> listQpp = new ArrayList<>();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        String date = day + "/" + (month+1) + "/" + year;
        this.datefordeletedetaildeb = date;
        String name = this.autotxtDebtNameAdd.getText().toString();
        Cursor cursor = DebtDatabase.rawQuery("Select * from Orders WHERE Time = '"+date+"' AND CustomerName = '"+name+"'",null);
        if (cursor==null)
        {
            return null;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            Qpp qpp = new Qpp(cursor.getString(4),cursor.getString(3),cursor.getString(5));
            sumtotal+= Float.parseFloat(qpp.getPrice());
            listQpp.add(qpp);
            cursor.moveToNext();
        }

        return listQpp;
    }

    private void addEvents() {
        btnAddCustormerOrProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionBtnAddCustormerOrProduct();
            }
        });

        btnAddDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionBtnAddDebt();
            }
        });


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionBtnFind();
            }
        });

        btnDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionShowDatePicker();
            }
        });

        autotxtDebtNameAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetQPP();
            }
        });

        autotxtDebtProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetQPP();
            }
        });

    }

    private void resetQPP() {


        txtDebtSum.setText("");
        listQpp = new ArrayList<>();
        adapterQpp = new AdapterQpp(MainActivity2.this, R.layout.quantity_product_price, listQpp);
        lvQpp.setAdapter(adapterQpp);
    }


    private void actionShowDatePicker() {
        resetQPP();
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                txtDebtDay.setText(dayFormat.format(calendar.getTime()));

            }
        };


        DatePickerDialog datePickerDialog = new DatePickerDialog(
                MainActivity2.this,
                callBack,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();


    }

    private void actionBtnAddDebt() {

        if(!this.autotxtDebtNameAdd.getText().toString().equalsIgnoreCase("") && !this.autotxtDebtProduct.getText().toString().equalsIgnoreCase("") && !this.txtQuantatyProduct.getText().toString().equalsIgnoreCase("")) {

            if(checkNameExists(this.autotxtDebtNameAdd.getText().toString().trim()) && checkProductExists(this.autotxtDebtProduct.getText().toString().trim())) {

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                String date = day + "/" + (month + 1) + "/" + year;
                String productChoice = this.autotxtDebtProduct.getText().toString();
                Cursor cursor = DebtDatabase.rawQuery("Select * from ProductPrice WHERE ProductName = '" + productChoice + "'", null);
                cursor.moveToFirst();
                String PricePerUnit = cursor.getString(1);

                ContentValues values = new ContentValues();
                values.put("Time", date);
                values.put("CustomerName", this.autotxtDebtNameAdd.getText().toString().trim());
                values.put("ProductName", this.autotxtDebtProduct.getText().toString().trim());
                values.put("Quantity", this.txtQuantatyProduct.getText().toString().trim());
                values.put("Price", Float.toString(Float.parseFloat(this.txtQuantatyProduct.getText().toString()) * Float.parseFloat(PricePerUnit)));
                DebtDatabase.insert("Orders", null, values);
                this.autotxtDebtProduct.setText("");
                this.txtQuantatyProduct.setText("");
                Toast.makeText(MainActivity2.this, "Thêm nợ mới thành công", Toast.LENGTH_SHORT).show();
                refreshListQpp();



                actionBtnFind();
                actionNP();

            }
            else if (!checkNameExists(this.autotxtDebtNameAdd.getText().toString().trim()) && !checkProductExists(this.autotxtDebtProduct.getText().toString().trim()))
            {
                this.autotxtDebtProduct.setText("");
                this.autotxtDebtNameAdd.setText("");
                Toast.makeText(MainActivity2.this, "Tên con nợ và sản phẩm không tồn tại hoặc nhập không chuẩn. Vui lòng qua tab THÊM để thêm tên", Toast.LENGTH_SHORT).show();
            }
            else if(checkNameExists(this.autotxtDebtNameAdd.getText().toString().trim()))
            {
                this.autotxtDebtProduct.setText("");
                Toast.makeText(MainActivity2.this, "Tên sản phẩm không tồn tại hoặc nhập không chuẩn. Vui lòng qua tab THÊM để thêm tên", Toast.LENGTH_SHORT).show();
            }
            else
            {
                this.autotxtDebtNameAdd.setText("");
                Toast.makeText(MainActivity2.this, "Tên con nợ chưa tồn tại hoặc nhập không chuẩn. Vui lòng qua tab THÊM để thêm tên", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(MainActivity2.this, "Nhập thiếu thông tin. Vui lòng nhập lại ", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshListQpp() {
        lvQpp = findViewById(R.id.lvQpp);
        listQpp = getlistQpp();
        adapterQpp = new AdapterQpp(MainActivity2.this, R.layout.quantity_product_price, listQpp);
        lvQpp.setAdapter(adapterQpp);

        txtDebtSum.setText("Tổng tiền thiếu: " + new java.text.DecimalFormat("#").format(sumtotal) + " VNĐ");
    }

    private boolean checkNameExists(String name) {
        ArrayList<String> listName = getListNameCustomer();
        for (String nametemp : listName)
        {
            if(name.equals(nametemp))
            {
                return true;
            }
        }
        return false;
    }

    private boolean checkProductExists(String name) {
        ArrayList<String> listProduct = getListNameProduct();
        for (String nametemp : listProduct)
        {
            if(name.equals(nametemp))
            {
                return true;
            }
        }
        return false;
    }
    private void actionBtnFind(){
        lvDP = findViewById(R.id.lvDP);
        listDP = getlistDP();
        adapterDP = new AdapterDP(MainActivity2.this,R.layout.day_price,listDP);
        lvDP.setAdapter(adapterDP);

    }

    private ArrayList<DP> getlistDP() {
        ArrayList<DP> listDP = new ArrayList<>();
        String name = this.autotxtDebtNameEdit.getText().toString();
       // anh minh' Group by Time
        Cursor cursor = DebtDatabase.rawQuery("SELECT Time, sum(Price) as [Total] from Orders WHERE CustomerName = '"+name+"' Group by Time",null);
        if (cursor==null)
        {
            return null;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            DP dp = new DP(cursor.getString(0),cursor.getString(1));
            listDP.add(dp);
            cursor.moveToNext();
        }

        return listDP;

    }

    public String capitalizeWord(String str){
        String words[]=str.split("\\s");
        String capitalizeWord="";
        for(String w:words){
            String first=w.substring(0,1);
            String afterfirst=w.substring(1);
            capitalizeWord+=first.toUpperCase()+afterfirst+" ";
        }
        return capitalizeWord.trim();
    }

    private void actionBtnAddCustormerOrProduct() {

        if(!this.txtCustomerAdd.getText().toString().equalsIgnoreCase(""))
        {
            ContentValues values = new ContentValues();

            String DebtName  = this.txtCustomerAdd.getText().toString().trim();
            values.put("Name",capitalizeWord(DebtName));

            DebtDatabase.insert("CustormerName",null,values);
            this.txtCustomerAdd.setText("");
            Toast.makeText(MainActivity2.this,"Thêm khách hàng mới thành công",Toast.LENGTH_SHORT).show();

        }

        if(!this.txtProductAdd.getText().toString().equalsIgnoreCase("") && !this.txtUnitPrice.getText().toString().equalsIgnoreCase(""))
        {
            ContentValues values = new ContentValues();

            String ProName  = this.txtProductAdd.getText().toString().trim();

            values.put("ProductName",capitalizeWord(ProName));
            values.put("Price",this.txtUnitPrice.getText().toString().trim());
            DebtDatabase.insert("ProductPrice",null,values);
            this.txtProductAdd.setText("");
            this.txtUnitPrice.setText("");
            Toast.makeText(MainActivity2.this,"Thêm món hàng mới thành công",Toast.LENGTH_SHORT).show();
        }
        listNameProduct.clear();
        listNameCustomer.clear();
        listNameProduct = getListNameProduct();
        listNameCustomer = getListNameCustomer();
        // this code will refresh autocompleteTextView
        arrayAdapterListNameCustomer = new ArrayAdapter<String>(
                MainActivity2.this,
                android.R.layout.simple_list_item_1,
                listNameCustomer);
        autotxtDebtNameAdd.setAdapter(arrayAdapterListNameCustomer);
        autotxtDebtNameEdit.setAdapter(arrayAdapterListNameCustomer);

        arrayAdapterListNameProduct = new ArrayAdapter<String>(
                MainActivity2.this,
                android.R.layout.simple_list_item_1,
                listNameProduct);
        autotxtDebtProduct.setAdapter(arrayAdapterListNameProduct);
        actionPP();
        actionNameCustomer();
    }

    private ArrayList<String> getListNameCustomer()
    {
        ArrayList<String> listNameCustomer = new ArrayList<>();
        Cursor cursor = DebtDatabase.rawQuery("Select * from CustormerName",null);
        if (cursor==null)
        {
            return null;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            listNameCustomer.add(cursor.getString(0));
            cursor.moveToNext();
        }
        return listNameCustomer;
    }


    private ArrayList<String> getListNameProduct()
    {
        ArrayList<String> listNameProduct = new ArrayList<>();
        Cursor cursor = DebtDatabase.rawQuery("Select * from ProductPrice",null);
        if (cursor==null)
        {
            return null;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            listNameProduct.add(cursor.getString(0));
            cursor.moveToNext();
        }

        return listNameProduct;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        DebtDatabaseHeper.close();
    }

    public void deleteDatabase(DP dp)
    {
        String customerName = autotxtDebtNameEdit.getText().toString();
        String day = dp.getDay();
        String s = "DELETE FROM Orders WHERE Time = '"+day+"' AND CustomerName = '"+customerName+"'";
        DebtDatabase.execSQL(s);
        actionBtnFind();
        actionNP();
    }

    public void actionDebDetailOfCustomer(DP dp)
    {
        String customerName = autotxtDebtNameEdit.getText().toString();
        String day= dp.getDay();
        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
        intent.putExtra("CUSTOMER",customerName);
        intent.putExtra("DEBTDAY",day);
        startActivity(intent);
    }

    public void actionRemoveProduct(PP pp)
    {
        String productName = pp.getProductName();
        String s = "DELETE FROM ProductPrice WHERE ProductName = '"+productName+"'";
        DebtDatabase.execSQL(s);
        actionPP();
        refreshListProduct();
    }

    public void actionRemoveCustomer(String customername)
    {
        String s = "DELETE FROM CustormerName WHERE Name = '"+customername+"'";
        DebtDatabase.execSQL(s);
        actionNameCustomer();
        refreshListCustomer();
    }

    public void actionRemoveQpp(Qpp qpp)
    {
        String quantity = qpp.getQuantaty();
        String product = qpp.getProduct();
        String price = qpp.getPrice();
        String customer = this.autotxtDebtNameAdd.getText().toString();
        String date = this.datefordeletedetaildeb;
        String s = "DELETE FROM Orders WHERE Time = '"+date+"' AND CustomerName = '"+customer+"' AND ProductName = '"+product+"' AND Quantity = '"+quantity+"' AND Price = '"+price+"'";
        DebtDatabase.execSQL(s);
        refreshListQpp();
        /////
        actionNP();
        actionBtnFind();

    }

    @Override
    protected void onResume() {
        super.onResume();
        actionNP();
        refreshListQpp();
        actionBtnFind();
    }
}