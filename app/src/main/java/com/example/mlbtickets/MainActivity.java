package com.example.mlbtickets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    DecimalFormat currency = new DecimalFormat("$###,##0.00");
    String game;
    String ticket;
    String park;
    int numTicket;
    String priceInfo;
    double price;
    String name;
    String cardType;
    String zip;
    String cardNum;
    String expDate;
    String secCode;

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
        final TextView ticketInfo = (TextView) findViewById(R.id.ticketInfo);
        final Spinner numTickets = (Spinner) findViewById(R.id.numTicketsSelect);
        final TextView numTicketsLabel = (TextView) findViewById(R.id.numTicketsLabel);
        final TextView paymentLabel = (TextView) findViewById(R.id.paymentLabel);
        final TextView nameLabel = (TextView) findViewById(R.id.nameLabel);
        final EditText name = (EditText) findViewById(R.id.name);
        final TextView cardTypeLabel = (TextView) findViewById(R.id.cardTypeLabel);
        final Spinner cardType = (Spinner) findViewById(R.id.cardType);
        final TextView cardNumberLabel = (TextView) findViewById(R.id.cardNumberLabel);
        final EditText cardNumber = (EditText) findViewById(R.id.cardNumber);
        final TextView expDateLabel = (TextView) findViewById(R.id.expDateLabel);
        final EditText expDate = (EditText) findViewById(R.id.expDate);
        final TextView secCodeLabel = (TextView) findViewById(R.id.secCodeLabel);
        final EditText secCode = (EditText) findViewById(R.id.secCode);
        final TextView zipLabel = (TextView) findViewById(R.id.zipLabel);
        final EditText zip = (EditText) findViewById(R.id.zip);
        final Button purchaseButton = (Button) findViewById(R.id.purchaseButton);




        numTickets.setSelection(0);

        games.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (games.getSelectedItemPosition() != 0) {
                    game = games.getSelectedItem().toString();
                    ticketLabel.setVisibility(View.VISIBLE);
                    tickets.setVisibility(View.VISIBLE);
                    tickets.setSelection(0);
                    ticketInfo.setText("");

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
                numTicket = Integer.parseInt(numTickets.getSelectedItem().toString());

                if (tickets.getSelectedItemPosition() != 0) {
                        ticket = tickets.getSelectedItem().toString();
                        priceInfo = ticket.substring(1);
                        int spacePos = priceInfo.indexOf(" ");
                        if (spacePos > 0) {
                            priceInfo = priceInfo.substring(0, spacePos);
                        }
                        price = Double.parseDouble(priceInfo);
                        price = price * numTicket;

                        ticketInfo.setVisibility(View.VISIBLE);
                        ticketInfo.setText("\nBallpark: " + park.toUpperCase() + "\n" + game +
                                "\n" + ticket + " X " + numTicket + "\nTotal: " +
                                currency.format(price));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //validate input


                Intent i = new Intent(MainActivity.this, DisplayActivity.class);
                startActivityForResult(i, 1);
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
