package com.example.chai.brachometer;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by chai on 26/06/2016.
 */
public class SendActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        findViewById(R.id.btn_send).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String massge;
                        //TODO: insert pone numbers
                        massge = String.format(((EditText) findViewById(R.id.txt_massge)).getText().toString(), "חי");
//                        SmsManager.getDefault().sendTextMessage(phone,null,massge,null,null);
                        Toast.makeText(SendActivity.this,"נשלח",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        );
    }
}
