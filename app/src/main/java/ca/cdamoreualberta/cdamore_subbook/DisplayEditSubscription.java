/*
 * DisplayEditSubscription
 *
 * February, 3, 2018
 *
 * Copyright 2018...
 */

package ca.cdamoreualberta.cdamore_subbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Activity to display screen where user can edit and delete the selected subscription
 *
 * @author Colin D'Amore
 */

public class DisplayEditSubscription extends AppCompatActivity {

    private static final String FILENAME = "subscriptions.sav";

    private ArrayList<Subscription> SubscriptionList;
    private ArrayAdapter<Subscription> adapter;

    private EditText nameText;
    private EditText dateText;
    private EditText chargeText;
    private EditText commentText;
    private TextView invalidText;

    /**
     * Overridden method to set state of screen upon the opening of the activity
     *
     * @param savedInstanceState bundle of saved instance of activity
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        Intent intent = getIntent();
        String curName = intent.getStringExtra(MainActivity.NAME);
        String curDate = intent.getStringExtra(MainActivity.DATE);
        String curCharge = intent.getStringExtra(MainActivity.CHARGE);
        String curComment = intent.getStringExtra(MainActivity.COMMENT);
        final Integer position = intent.getIntExtra(MainActivity.POSITION, 0);

        nameText = (EditText) findViewById(R.id.update_name);
        dateText = (EditText) findViewById(R.id.update_date);
        chargeText = (EditText) findViewById(R.id.update_charge);
        commentText = (EditText) findViewById(R.id.update_comment);
        invalidText = (TextView) findViewById(R.id.invalid_format2);

        nameText.setText(curName);
        dateText.setText(curDate);
        chargeText.setText(curCharge);
        commentText.setText(curComment);

        Button updateButton = (Button) findViewById(R.id.update_subscription);
        Button deleteButton = (Button) findViewById(R.id.delete_subscription);

        updateButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                String invalidName = "Name must be no more then 20 characters";
                String invalidDate = "Date must be in format 'yyyy-mm-dd'";
                String invalidComment = "Comment must be no more then 30 characters";
                String nameBlank = "Name field cannot be blank";

                String name = nameText.getText().toString();
                String date = dateText.getText().toString();
                String chargeS = chargeText.getText().toString();
                if (chargeS.equals("")) {chargeS = "0";}
                Double chargeD = Double.parseDouble(chargeS);
                DecimalFormat df = new DecimalFormat("#.00");
                String charge = df.format(chargeD);
                String comment = commentText.getText().toString();


                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                /* Determine if user inputted the correct values */

                if (name.length() > 20) {
                    invalidText.setText(invalidName);
                    invalidText.setVisibility(View.VISIBLE);
                    /* Hides keyboard */
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                } else if (date.length() != 10 || date.charAt(4) != '-' || date.charAt(7) != '-') {
                    invalidText.setText(invalidDate);
                    invalidText.setVisibility(View.VISIBLE);
                    /* Hides keyboard */
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                } else if (comment.length() > 30) {
                    invalidText.setText(invalidComment);
                    invalidText.setVisibility(View.VISIBLE);
                    /* Hides keyboard */
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                } else if (name.length() < 1) {
                    invalidText.setText(nameBlank);
                    invalidText.setVisibility(View.VISIBLE);
                    /* Hides keyboard */
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                } else {

                    /* Update subscription if input was valid */

                    Subscription newSubscription = new Subscription(name, date, charge, comment);

                    SubscriptionList.set(position, newSubscription);

                    adapter.notifyDataSetChanged();

                    saveInFile();

                    Intent intent = new Intent(DisplayEditSubscription.this, MainActivity.class);
                    startActivity(intent);

                }

            }

        });

        deleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                Subscription sub1 = SubscriptionList.get(position);

                SubscriptionList.remove(sub1);

                adapter.notifyDataSetChanged();

                saveInFile();

                Intent intent = new Intent(DisplayEditSubscription.this, MainActivity.class);
                startActivity(intent);

            }

        });
    }

    /**
     * Overridden method to customize actions upon the start of the activity
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<Subscription>(this,
                R.layout.list_single, SubscriptionList);
    }

    /**
     * Load subscriptions from file: FILENAME
     */

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();

            SubscriptionList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            SubscriptionList = new ArrayList<Subscription>();
        }
    }

    /**
     * Save subscription to subscription list
     */

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();

            gson.toJson(SubscriptionList, out);


            out.flush();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Extra action that can be inputted when app is terminated
     */

    protected void onDestroy() {
        super.onDestroy();
    }

}
