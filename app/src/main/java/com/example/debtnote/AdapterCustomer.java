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

public class AdapterCustomer extends ArrayAdapter<String> {
    Activity context;
    int resource;
    List<String> objects;
    public AdapterCustomer(Activity context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View row = layoutInflater.inflate(this.resource,null);

        TextView txtCustomer = row.findViewById(R.id.txtCustomer);
        ImageButton btnRemoveCustomer = row.findViewById(R.id.btnRemoveCustomer);

        String s = this.objects.get(position);
        txtCustomer.setText(s);
        btnRemoveCustomer.setTag(s);
        btnRemoveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionBtnRemoveCustomer(view);
            }
        });
        return row;
    }

    private void actionBtnRemoveCustomer(View view) {
        ((MainActivity2)this.context).actionRemoveCustomer((String) view.getTag());
    }
}
