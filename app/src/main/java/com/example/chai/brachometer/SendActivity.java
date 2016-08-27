package com.example.chai.brachometer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
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
        String massege = "";
        switch (prevActivitySender.getIntExtra("originButton",0)){
            case R.id.btn_passover:
            case R.id.btn_new_year:
                massege = "אהלן %s,\n חג שמח לך ולבני משפחתך!";
                break;
            case R.id.btn_kippur:
                massege ="%s אני מאחל לך צום קל ומועיל";
                break;
            default:
                break;
        }
        ((EditText )findViewById(R.id.txt_massge)).setText(massege);
        findViewById(R.id.btn_send).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String massge;
                        for(Contact contact : ContactChooseAdapter._chosenContacts){
                            massge = String.format(((EditText) findViewById(R.id.txt_massge)).getText().toString(), contact.getFirstName());
                            SmsManager.getDefault().sendTextMessage(contact.getPhone(),null,massge,null,null);
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
