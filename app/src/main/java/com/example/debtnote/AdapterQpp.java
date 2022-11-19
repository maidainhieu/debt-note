package com.example.debtnote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.zip.Inflater;

public class AdapterQpp extends ArrayAdapter<Qpp> {

    Activity context;
    int resource;
    List<Qpp> objects;

    public AdapterQpp( Activity context, int resource,  List<Qpp> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);

         TextView txtQtyQpp = row.findViewById(R.id.txtQtyQpp);
         TextView txtProductQpp = row.findViewById(R.id.txtProductQpp);
         TextView txtPriceQpp = row.findViewById(R.id.txtPriceQpp);

         ImageView btnRemoveQpp = row.findViewById(R.id.btnRemoveQpp);

         Qpp qpp = this.objects.get(position);
         txtQtyQpp.setText("   "+qpp.getQuantaty());
         txtProductQpp.setText(qpp.getProduct());

         txtPriceQpp.setText(new java.text.DecimalFormat("#").format(Float.parseFloat(qpp.getPrice()))+" VNĐ");
         btnRemoveQpp.setTag(qpp);
         btnRemoveQpp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 actionBtnRemoveQpp(view);
             }
         });
        return row;
    }

    private void actionBtnRemoveQpp(View view) {
        ((MainActivity2)this.context).actionRemoveQpp((Qpp) view.getTag());
    }
}
