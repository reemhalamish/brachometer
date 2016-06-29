package com.example.chai.brachometer;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by chai on 26/06/2016.
 */
public class ContatAdapter extends ArrayAdapter<Contact> {
    public static ArrayList<Contact> _chosenContactsSet = new ArrayList<>();

    public ContatAdapter(Context context, int resource, ArrayList<Contact> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("adapter","create");
        final View view = LayoutInflater.from(super.getContext()).inflate(R.layout.contat_row,parent,false);
        final TextView tvPhone = (TextView) view.findViewById(R.id.tv_phone);
        final TextView tvContact = (TextView) view.findViewById(R.id.tv_name);
        final Contact contact = super.getItem(position);
        tvPhone.setText(contact.phone);
        tvContact.setText(contact.name);
        if(_chosenContactsSet.contains(contact)){
            tvPhone.setTextColor(Color.BLUE);
            tvContact.setTextColor(Color.BLUE);
        }
        else{
            tvPhone.setTextColor(Color.BLACK);
            tvContact.setTextColor(Color.BLACK);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_chosenContactsSet.contains(contact)){
                    _chosenContactsSet.remove(contact);
                    tvPhone.setTextColor(Color.BLACK);
                    tvContact.setTextColor(Color.BLACK);
                }
                else{
                    _chosenContactsSet.add(contact);
                    tvPhone.setTextColor(Color.BLUE);
                    tvContact.setTextColor(Color.BLUE);
                }
            }
        });

        return view;
    }

    public ArrayList<Contact> get_chosenContactsSet() {
        return _chosenContactsSet;
    }
}
