package com.example.chai.brachometer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by chai on 26/06/2016.
 * activity to send the messages
 */
public class SendActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Intent prevActivitySender = getIntent();
        String massege;
        final String specialChar = getString(R.string.special_chr);
        switch (prevActivitySender.getIntExtra(getString(R.string.TAG_BUTTON_PRESSED),0)){
            case R.id.btn_passover:
            case R.id.btn_new_year:
                massege = getString(R.string.holliday_wish);
                break;
            case R.id.btn_kippur:
                massege =getString(R.string.fast_wish);
                break;
            default:
                massege = "";
        }
        final EditText txtMsgInput = (EditText )findViewById(R.id.txt_massge);
        txtMsgInput.setText(massege);
        findViewById(R.id.btn_send).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String massge;
                        for(Contact contact : ContactChooseAdapter._chosenContacts){
                            massge = txtMsgInput.getText().toString().replace(specialChar, contact.getFirstName());
                            SmsManager.getDefault().sendTextMessage(contact.getPhone(),null,massge,null,null);
                        }
                        ContactChooseAdapter._chosenContacts.clear();
                        Toast.makeText(SendActivity.this, R.string.sent,Toast.LENGTH_SHORT).show();
                        // todo maybe for a lot of people make a toast "sent to %d people"
                        Intent goToMain = new Intent(SendActivity.this, MainActivity.class);
                        goToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(goToMain);
                        finish();
                    }
                }
        );

        Button btnAddSpecialChr = (Button) findViewById(R.id.btn_special_chr);
        btnAddSpecialChr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMsgInput.getText().insert(txtMsgInput.getSelectionStart(), specialChar);
            }
        });
    }
}
