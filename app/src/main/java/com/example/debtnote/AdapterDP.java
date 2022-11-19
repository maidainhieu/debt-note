package com.example.debtnote;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterDP extends ArrayAdapter<DP>  {
    Activity context;
    int resource;
    List<DP> objects;


    public AdapterDP( Activity context, int resource, List<DP> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects= objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater =this.context.getLayoutInflater();
        View row = layoutInflater.inflate(this.resource,null);
        TextView txtDebtDayDp = row.findViewById(R.id.txtDebtDayDp);
        TextView txtDebtToTalDp = row.findViewById(R.id.txtDebtToTalDp);

        ImageButton btndetailDP = row.findViewById(R.id.btndetailDP);
        ImageButton btnDeleteDP = row.findViewById(R.id.btnDeleteDP);


        DP dp = this.objects.get(position);
        txtDebtDayDp.setText("Ngày: "+ dp.getDay());
        txtDebtToTalDp.setText("Nợ "+dp.getPrice()+" VNĐ");

        btndetailDP.setTag(dp);
        btnDeleteDP.setTag(dp);

        btndetailDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionBtndetailDP(view);
            }
        });

        btnDeleteDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionBtnDeleteDP(view);
            }
        });


        return row;
    }

    private void actionBtnDeleteDP(View view) {
        ((MainActivity2)this.context).deleteDatabase((DP) view.getTag());
    }

    private void actionBtndetailDP(View view) {
        ((MainActivity2)this.context).actionDebDetailOfCustomer((DP) view.getTag());
    }
}
