package com.example.mlbtickets;

import android.content.Context;
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
import android.widget.Toast;

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
    String total;
    final int YEAR_MIN = 18;
    final int YEAR_MAX = 27;
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
                        total = currency.format(price);

                        ticketInfo.setVisibility(View.VISIBLE);
                        ticketInfo.setText("\nBallpark: " + park.toUpperCase() + "\n" + game +
                                "\n" + ticket + " X " + numTicket + "\nTotal: " + total);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, DisplayActivity.class);
                final TextView paymentLabel = (TextView) findViewById(R.id.paymentLabel);
                final TextView nameLabel = (TextView) findViewById(R.id.nameLabel);
                EditText name = (EditText) findViewById(R.id.name);
                final TextView cardTypeLabel = (TextView) findViewById(R.id.cardTypeLabel);
                Spinner cardType = (Spinner) findViewById(R.id.cardType);
                final TextView cardNumberLabel = (TextView) findViewById(R.id.cardNumberLabel);
                EditText cardNumber = (EditText) findViewById(R.id.cardNumber);
                final TextView expDateLabel = (TextView) findViewById(R.id.expDateLabel);
                EditText expDate = (EditText) findViewById(R.id.expDate);
                final TextView secCodeLabel = (TextView) findViewById(R.id.secCodeLabel);
                EditText secCode = (EditText) findViewById(R.id.secCode);
                final TextView zipLabel = (TextView) findViewById(R.id.zipLabel);
                EditText zip = (EditText) findViewById(R.id.zip);

                String displayName;
                String expDateString;
                String displayCardNumber ="";
                String displayZip = "";
                String displaySecCode = "";
                String displayCardType;
                boolean error = false;
                String errorText = "";
                int month;
                int year;




                //validate input

                // validate name
                if (name.getText().toString().matches("")) {
                    error = true;
                    errorText += getString(R.string.nameError);
                }

                displayName = name.getText().toString();

                // validate date
                if (expDate.getText().toString().matches("")) {
                    error = true;
                    errorText += getString(R.string.expDateError);
                } else if (expDate.getText().toString().length() < 5) {
                    error = true;
                    errorText += getString(R.string.expDateLengthError);
                } else if (!expDate.getText().toString().substring(2,3).equals("/")) {
                    error = true;
                    errorText += getString(R.string.expDateLengthError);

                } else {
                    expDateString = expDate.getText().toString();
                    month = Integer.parseInt(expDateString.substring(0, 2));
                    year = Integer.parseInt(expDateString.substring(3, 5));
                    if (month < 1 || month > 12) {
                        error = true;
                        errorText += getString(R.string.monthError);
                    } else if (year < YEAR_MIN || year > YEAR_MAX) {
                        error = true;
                        errorText += getString(R.string.yearError);
                    }
                }

                //validate credit card number
                if (cardNumber.getText().toString().matches("")) {
                    error = true;
                    errorText += getString(R.string.cardError);
                } else if (cardNumber.getText().toString().length() < 16) {
                    error = true;
                    errorText += getString(R.string.cardError);
                } else {
                    displayCardNumber = cardNumber.getText().toString();
                    displayCardNumber = displayCardNumber.substring(displayCardNumber.length() - 4);
                }

                //validate zip code
                if (zip.getText().toString().matches("")) {
                    error = true;
                    errorText += getString(R.string.zipError);
                } else if (zip.getText().toString().length() < 5) {
                    error = true;
                    errorText += getString(R.string.zipError);
                } else {
                    displayZip = zip.getText().toString(); // not used here but will be used for processing
                }

                //validate security code
                if (secCode.getText().toString().matches("")) {
                    error = true;
                    errorText += getString(R.string.secCodeError);
                } else if (secCode.getText().toString().length() < 3) {
                    error = true;
                    errorText += getString(R.string.secCodeError);
                } else {
                    displaySecCode = zip.getText().toString(); // not used here but will be used for processing
                }

                //validate card type
                if (cardType.getSelectedItemPosition() == 0) {
                    error = true;
                    errorText += getString(R.string.cardTypeError);
                }

                displayCardType = cardType.getSelectedItem().toString();

                // if errors display them and reset flag and text
                if (error) {
                    Toast toast = Toast.makeText(getApplicationContext(), errorText, Toast.LENGTH_SHORT);
                    toast.show();
                } else {

                    i.putExtra("passedText", "**** CUSTOMER RECEIPT ****\n\nCustomer Name: " + displayName + "\nCard Type: " + displayCardType +
                            "    CC#: " + displayCardNumber + "\n\n" + game + "\n" + ticket +
                            "    # Tickets: " + numTicket + "\n\nAmount due and paid: " +
                            total);

                    startActivityForResult(i, 1);
                }


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
