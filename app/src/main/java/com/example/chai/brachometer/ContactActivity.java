package com.example.chai.brachometer;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class ContactActivity extends AppCompatActivity {
    private static final String TAG = "contacts";
    private ArrayList<Contact> _allContactsList;
    private HashSet<String> _allPhoneNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        populateContacts();
        ListView contacts = (ListView)findViewById(R.id.lv_contacts);

        final ContactChooseAdapter contactAdapter = new ContactChooseAdapter(this, R.layout.contat_row, _allContactsList);
        assert contacts != null;
        contacts.setAdapter(contactAdapter);
        findViewById(R.id.btn_finish_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prevActivitySender = getIntent();
                Intent nextActivity = new Intent(ContactActivity.this,EditContactActivity.class);
                nextActivity.putExtras(prevActivitySender.getExtras());
                startActivity(nextActivity);

            }
        });


        EditText edtFindContact = (EditText) findViewById(R.id.edt_contact);
        edtFindContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactAdapter.getFilter().filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * svae all contacts in _allContactsList
     */
    private void populateContacts() {
        ArrayList<Contact> allContacts = new ArrayList<>();
        _allPhoneNumbers = new HashSet<>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        if (phones == null) {
            return;
        }
        while (phones.moveToNext())
        {

            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if (name==null || name.length()==0 || phoneNumber==null || phoneNumber.length()==0) continue;

            phoneNumber = phoneNumber.replace(" ", "").replace("-", "").replace("+972", "0");
            if (_allPhoneNumbers.contains(phoneNumber)) {
                Log.d(TAG, "found duplicate! at " + name + " , " + phoneNumber);
                continue;
            }

            Contact contact = new Contact(name, phoneNumber);

            allContacts.add(contact);
            _allPhoneNumbers.add(phoneNumber);

        }
        phones.close();
        // create temp and remove duplicates
        ArrayList<String> tempContacts = new ArrayList<>();
        _allContactsList = new ArrayList<>();
        for (int i = 0; i < allContacts.size(); i++) {
            Contact contact = allContacts.get(i);
            String value = contact.getName() + " " + contact.getPhone();
            if (tempContacts.contains(value)) continue;
            tempContacts.add(value);
            _allContactsList.add(contact);
        }
        Collections.sort(_allContactsList);
    }
}
