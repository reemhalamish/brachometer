package com.example.chai.brachometer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EditContactActivity extends AppCompatActivity {
    private class ContactAdapter extends ArrayAdapter<Contact> {
        private class HorizontalLinearRow extends LinearLayout{
            TextView _defaultTextView;
            Contact _contact;

            public HorizontalLinearRow(Context context,Contact contact) {
                super(context);
                _contact = contact;
                super.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                super.setOrientation(HORIZONTAL);
            }

            void setDefault(String s, TextView tv) {
                _contact.setFirstName(s);
                if (_defaultTextView != null) {
                    _defaultTextView.setBackgroundColor(Color.WHITE);// need to get default color TODO
                    _defaultTextView.setTextColor(Color.BLACK);  // need to get default color TODO
                }


                _defaultTextView = tv;
                _defaultTextView.setText(s);
                _defaultTextView.setBackgroundColor(Color.BLUE);
                _defaultTextView.setTextColor(Color.WHITE);

            }

        }
        public ContactAdapter(Context context, int resource, ArrayList<Contact> objects) {
            super(context, resource,objects);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Contact contact = getItem(position);
            final HorizontalLinearRow root = new HorizontalLinearRow (getContext(),contact);
            for(final String s: contact.getSplitedName()) {
                final TextView view = new TextView(getContext());
                view.setText(s);
                view.setPadding(5,0,5,0);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        root.setDefault(s,view);
                    }
                });
                root.addView(view);
            }
            root.getChildAt(0).performClick();
            return root;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ListView lv = (ListView) findViewById(R.id.lv_contacts);
        lv.setAdapter(new ContactAdapter(this,0, ContactChooseAdapter._chosenContacts));
        findViewById(R.id.btn_finish_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prevActivitySender = getIntent();
                Intent nextActivity = new Intent(EditContactActivity.this,SendActivity.class);
                nextActivity.putExtras(prevActivitySender.getExtras());
                startActivity(nextActivity);

            }
        });
    }
}
