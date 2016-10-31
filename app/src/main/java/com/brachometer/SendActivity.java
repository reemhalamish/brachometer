package com.brachometer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by chai on 26/06/2016.
 * activity to send the messages
 */
public class SendActivity extends Activity {
    EditText _txtMsgInput;
    String _specialChar;
    ToggleButton _tgbMultiplyLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Intent prevActivitySender = getIntent();
        _specialChar = getString(R.string.special_chr);
        String massege;



        switch (prevActivitySender.getIntExtra(getString(R.string.TAG_BUTTON_PRESSED),0)){
            case R.id.btn_passover:
            case R.id.btn_new_year:
                massege = getString(R.string.holliday_wish);
                break;
            case R.id.btn_kippur:
                massege =getString(R.string.fast_wish);
                break;
            case R.id.btn_hannukah:
                massege =getString(R.string.hannukah_wish);
                break;
            case R.id.btn_xmas:
                massege =getString(R.string.xmas_wish);
                break;
            default:
                massege = "";
        }
        _txtMsgInput = (EditText)findViewById(R.id.txt_massge);
        _txtMsgInput.setText(massege);
        _tgbMultiplyLast = (ToggleButton) findViewById(R.id.tgb_send);

        findViewById(R.id.btn_send).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(SendActivity.this)
                                .setTitle(getString(R.string.confirm_send_title))
                                .setMessage(String.format(getString(R.string.confirm_send_msg), ContactChooseAdapter._chosenContacts.size()))
                                .setCancelable(true)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sendMasseges();
                                    }
                                }).create().show();
                    }
                }
        );

        Button btnAddSpecialChr = (Button) findViewById(R.id.btn_special_chr);
        btnAddSpecialChr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _txtMsgInput.getText().insert(_txtMsgInput.getSelectionStart(), _specialChar);
            }
        });
    }

    private void sendMasseges() {
        String massge;
        for(Contact contact : ContactChooseAdapter._chosenContacts){
            String firstName = contact.getFirstName();
            if (_tgbMultiplyLast.isChecked()) {
                char lastLetter = firstName.charAt(firstName.length() - 1);
                firstName = new StringBuilder(firstName).append(lastLetter).append(lastLetter).toString();
            }
            massge = _txtMsgInput.getText().toString().replace(_specialChar, firstName);
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
