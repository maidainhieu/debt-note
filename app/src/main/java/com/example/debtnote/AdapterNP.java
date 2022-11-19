package com.example.debtnote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterNP extends ArrayAdapter<NP> {

    Activity context;
    int resource;
    List<NP> objects;

    public AdapterNP(Activity context, int resource, List<NP> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View row = layoutInflater.inflate(this.resource,null);

        TextView txtDebtNameNp = row.findViewById(R.id.txtDebtNameNp);
        TextView txtDebtToTalNp = row.findViewById(R.id.txtDebtToTalNp);

        NP np = this.objects.get(position);
        txtDebtNameNp.setText(np.getName());
        txtDebtToTalNp.setText("Nợ tổng: "+np.getPrice()+" VNĐ");
        return row;
    }
}
