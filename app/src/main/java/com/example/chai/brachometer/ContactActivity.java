package com.example.chai.brachometer;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;


public class ContactActivity extends AppCompatActivity {
    private ArrayList<Contact> _allContactsList;
    public static final String TAG_SET_CONTACTS = "chosenContactsSet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        populateContacts();
        ListView contacts = (ListView)findViewById(R.id.lv_contacts);
        final ContatAdapter contactAdapter = new ContatAdapter(this, R.layout.contat_row, _allContactsList);
        assert contacts != null;
        contacts.setAdapter(contactAdapter);
        findViewById(R.id.btn_finish_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(ContactActivity.this,SendActivity.class);
                nextActivity.putExtra(TAG_SET_CONTACTS,contactAdapter.get_chosenContactsSet());
                startActivity(nextActivity);

            }
        });
    }

    /**
     * svae all contacts in _allContactsList
     */
    private void populateContacts() {
        ArrayList<Contact> allContacts = new ArrayList<>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        if (phones == null) {
            return;
        }
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if (name==null || name.length()==0 || phoneNumber==null || phoneNumber.length()==0) continue;
            allContacts.add(new Contact(name, phoneNumber));

        }
        phones.close();
        // create temp and remove duplicates
        ArrayList<String> tempContacts = new ArrayList<>();
        _allContactsList = new ArrayList<>();
        for (int i = 0; i < allContacts.size(); i++) {
            Contact contact = allContacts.get(i);
            String value = contact.name + " " + contact.phone;
            if (tempContacts.contains(value)) continue;
            tempContacts.add(value);
            _allContactsList.add(contact);
        }
        Collections.sort(_allContactsList);
    }
}
