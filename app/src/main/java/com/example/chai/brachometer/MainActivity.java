package com.example.chai.brachometer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by chai on 26/06/2016.
 */
public class MainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View[] btns = new View[]{findViewById(R.id.btn_new_year),
            findViewById(R.id.btn_kippur),
            findViewById(R.id.btn_passover)};
        for (View v : btns){
            v.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_new_year:
            case R.id.btn_passover:
            case R.id.btn_kippur:
                Intent a = new Intent(this,ContactActivity.class);
                startActivity(a);
            default:
                break;
        }
    }
}
