package com.example.debtnote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterPP extends ArrayAdapter<PP> {
    Activity context;
    int resource;
    List<PP> objects;
    public AdapterPP( Activity context, int resource,  List<PP> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View row = layoutInflater.inflate(this.resource,null);

        TextView txtProduct = row.findViewById(R.id.txtProduct);
        TextView txtPrice = row.findViewById(R.id.txtPrice);
        ImageButton btnRemoveProduct = row.findViewById(R.id.btnRemoveProduct);

        PP pp = this.objects.get(position);
        txtProduct.setText("  "+pp.getProductName());
        txtPrice.setText(pp.getPrice());

        btnRemoveProduct.setTag(pp);
        btnRemoveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionBtnRemoveProduct(view);
            }
        });

        return row;
    }

    private void actionBtnRemoveProduct(View view) {
        ((MainActivity2)this.context).actionRemoveProduct((PP) view.getTag());
    }
}
