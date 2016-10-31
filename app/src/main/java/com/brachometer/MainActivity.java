package com.brachometer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by chai on 26/06/2016.
 *
 * first activity the user sees
 */
public class MainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View[] btns = new View[]{
            findViewById(R.id.btn_new_year),
            findViewById(R.id.btn_kippur),
            findViewById(R.id.btn_other),
            findViewById(R.id.btn_xmas),
            findViewById(R.id.btn_hannukah),
            findViewById(R.id.btn_passover)};
        for (View btn : btns){
            btn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

        Intent nextActivity = new Intent(this, com.brachometer.ContactActivity.class);
        nextActivity.putExtra(getString(R.string.TAG_BUTTON_PRESSED), v.getId());
        startActivity(nextActivity);
    }
}
