package com.example.chai.brachometer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by chai on 26/06/2016.
 */
public class SendActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Intent prevActivitySender = getIntent();

        findViewById(R.id.btn_send).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String massge;
                        for(Contact contact : ContatAdapter._chosenContactsSet){
                            massge = String.format(((EditText) findViewById(R.id.txt_massge)).getText().toString(), contact.name);
                            SmsManager.getDefault().sendTextMessage(contact.phone,null,massge,null,null);
                        }
                        Toast.makeText(SendActivity.this,"נשלח",Toast.LENGTH_SHORT).show();
                        Intent goToMain = new Intent(SendActivity.this, MainActivity.class);
                        goToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(goToMain);
                        finish();
                    }
                }
        );
    }
}
