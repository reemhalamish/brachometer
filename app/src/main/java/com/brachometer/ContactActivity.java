package com.brachometer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


public class ContactActivity extends AppCompatActivity {
    private static final String TAG = "contacts";
    static final int REQUEST_ASK_PERMISSIONS_CONTACTS = 12345;
    protected final static List<String> _fs_contactsPermissions = Arrays.asList(
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_CONTACTS
    );


    private ArrayList<Contact> _allContactsList;
    private HashSet<String> _allPhoneNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        if (isOkPopulateContacts()) {
            populateContacts();
            showContactsOnScreen();
        } else {
            askUserToEnableContacts();
        }
    }

    protected boolean isOkPopulateContacts() {
        return PermissionAsker.isAuthorised(this, _fs_contactsPermissions);
    }

    protected void askUserToEnableContacts() {
        PermissionAsker.askPermissions(this, _fs_contactsPermissions, REQUEST_ASK_PERMISSIONS_CONTACTS);
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

//        String[] fakeNames = new String[]{
//                "Alan Turing",
//                "Augustus De Morgan",
//                "Gandalf the Grey",
//                "Albus Percival Wulfric Brian Dumbledore",
//                "Albert Einstein",
//                "John Galt",
//                "George Orwell",
//                "Yeshayahu Leibowitz",
//                "Ender Wiggin",
//                "Augustin-Louis Cauchy",
//                "Aviv Tsenzor",
//                "Hezzy Laplacian",
//                "Gottfried Wilhelm Leibniz",
//        };
//
//        String[] fakePhones = new String[] {
//                "0101011010",
//                "2222222222",
//                "+999-123456789",
//                "0500999999",
//                "0299792458",
//                "0576225446",
//                "0519841984",
//                "0529011808",
//                "+666-55334210",
//                "123344555",
//                "04-9876543",
//                "053357777",
//                "248163264"
//        };
//
//        for (int i=0; i < fakeNames.length; i++) {
//            _allContactsList.add(new Contact(fakeNames[i], fakePhones[i]));
//            _allPhoneNumbers.add(fakePhones[i]);
//        }
    }

    protected void showContactsOnScreen() {
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
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_ASK_PERMISSIONS_CONTACTS) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    askUserToEnableContacts();
                    return;
                }
            }

            // all permissions granted
            populateContacts();
            showContactsOnScreen();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
