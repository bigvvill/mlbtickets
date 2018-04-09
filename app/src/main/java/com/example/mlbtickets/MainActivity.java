package com.example.mlbtickets;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String game;
    String ticket;
    String park;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        final Spinner games = (Spinner) findViewById(R.id.gameSelect);
        final TextView ticketLabel = (TextView) findViewById(R.id.ticketSelectLabel);
        final Spinner tickets = (Spinner) findViewById(R.id.ticketSelect);
        final TextView ticketInfo = (TextView) findViewById(R.id.ticketInfo) ;

        games.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (games.getSelectedItemPosition() != 0) {
                    game = games.getSelectedItem().toString();
                    ticketLabel.setVisibility(View.VISIBLE);
                    tickets.setVisibility(View.VISIBLE);

                    if (games.getSelectedItemPosition() > 10) {
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.twins, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        tickets.setAdapter(adapter);
                        park = "Target Field";

                    } else if (games.getSelectedItemPosition() > 6) {
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.reds, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        tickets.setAdapter(adapter);
                        park = "Great American Ballpark";

                    } else if (games.getSelectedItemPosition() > 3) {
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.cardinals, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        tickets.setAdapter(adapter);
                        park = "Busch Stadium";

                    } else {
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.brewers, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        tickets.setAdapter(adapter);
                        park = "Sun Trust Park";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tickets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (tickets.getSelectedItemPosition() != 0) {
                    ticket = tickets.getSelectedItem().toString();
                    ticketInfo.setVisibility(View.VISIBLE);
                    ticketInfo.setText("Ballpark: " + park.toUpperCase() + "\n" + game + "\n" + ticket);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    // creates menu in toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
