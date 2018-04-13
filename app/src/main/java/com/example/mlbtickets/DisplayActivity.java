package com.example.mlbtickets;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by willi on 4/10/2018.
 */

public class DisplayActivity extends AppCompatActivity {

    String newText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        // set up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView output = (TextView) findViewById(R.id.displayText);
        newText = getIntent().getStringExtra("passedText");
        output.setText(newText);

        // button listeners
        Button textButton = (Button) findViewById(R.id.txtButton);
        textButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                EditText phoneNum = (EditText) findViewById(R.id.phoneNum);

                sendSMSMessage(phoneNum.getText().toString());
            }

            protected void sendSMSMessage(String phoneNumber) {

                Intent intent = new Intent("android.intent.action.VIEW");
                /** creates an sms uri */
                Uri data = Uri.parse("sms:");
                intent.setData(data);

                intent.putExtra("address", phoneNumber);
                intent.putExtra("sms_body", newText);

                startActivity(intent);
            }

        });
    }




}
