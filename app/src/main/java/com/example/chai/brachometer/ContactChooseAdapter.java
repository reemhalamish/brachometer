package com.example.chai.brachometer;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chai on 26/06/2016.
 */
public class ContactChooseAdapter extends ArrayAdapter<Contact> {
    public static ArrayList<Contact> _chosenContacts = new ArrayList<>();
    private ArrayList<Contact> _allContacts, _contactsToView;

    public ContactChooseAdapter(Context context, int resource, ArrayList<Contact> objects) {
        super(context, resource, objects);
        _allContacts = (ArrayList<Contact>) objects.clone();
        _contactsToView = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("adapter","create");
        final View view = LayoutInflater.from(super.getContext()).inflate(R.layout.contat_row,parent,false);
        final TextView tvPhone = (TextView) view.findViewById(R.id.tv_phone);
        final TextView tvContact = (TextView) view.findViewById(R.id.tv_name);
        final Contact contact = super.getItem(position);
        tvPhone.setText(contact.getPhone());
        tvContact.setText(contact.getName());
        if(_chosenContacts.contains(contact)){
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
                if(_chosenContacts.contains(contact)){
                    _chosenContacts.remove(contact);
                    tvPhone.setTextColor(Color.BLACK);
                    tvContact.setTextColor(Color.BLACK);
                }
                else{
                    _chosenContacts.add(contact);
                    tvPhone.setTextColor(Color.BLUE);
                    tvContact.setTextColor(Color.BLUE);
                }
            }
        });

        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    _contactsToView = _allContacts;
                    results.count = _contactsToView.size();
                    results.values = _contactsToView;
                    return results;
                }

                String toFilter = constraint.toString();
                _contactsToView = new ArrayList<>();
                for (Contact contact : _allContacts) {
                    if (contact.contains(toFilter)) {
                        _contactsToView.add(contact);
                    }
                }
                results.count = _contactsToView.size();
                results.values = _contactsToView;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                _contactsToView = (ArrayList<Contact>) results.values;
                clear();
                addAll(_contactsToView);
                notifyDataSetChanged();
            }
        };
    }

    //    public ArrayList<Contact> get_chosenContactsSet() {
//        return _chosenContacts;
//    }
}
