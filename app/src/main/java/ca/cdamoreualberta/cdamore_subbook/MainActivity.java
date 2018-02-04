/*
 * MainActivity
 *
 * February, 3, 2018
 *
 * Copyright 2018...
 */

package ca.cdamoreualberta.cdamore_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * MainActivity to display list of subscriptions
 *
 * @author Colin D'Amore
 */

public class MainActivity extends AppCompatActivity {

    protected static final String NAME = "name";
    protected static final String DATE = "date";
    protected static final String CHARGE = "charge";
    protected static final String COMMENT = "comment";
    protected static final String POSITION = "position";

    private static final String FILENAME = "subscriptions.sav";

    private ListView oldSubscriptionList;

    private ArrayList<Subscription> SubscriptionList;
    private SubscriptionAdapter adapter;

    /**
     * Overridden method to set state of screen upon the opening of the activity
     *
     * @param savedInstanceState bundle of saved instance of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oldSubscriptionList = (ListView) findViewById(R.id.oldSubscriptionList);
    }

    /**
     * Add subscription to subscription list
     *
     * @param view current View object
     */

    public void addSubscription(View view) {
        Intent intent = new Intent(this, DisplayAddSubscription.class);
        startActivity(intent);
    }

    /**
     * Overridden method to customize actions upon the start of the activity
     */

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        adapter = new SubscriptionAdapter(MainActivity.this, SubscriptionList);
        oldSubscriptionList.setAdapter(adapter);
        oldSubscriptionList.setOnItemClickListener(new ListClickHandler());
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
     * Extra action that can be inputted when app is terminated
     */

    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Inherited class to customize what happens when list item is clicked
     */

    public class ListClickHandler implements OnItemClickListener{

        /**
         * Activate DisplayEditSubscription when list item is clicked
         *
         * @param adapter current adapter
         * @param view current view
         * @param position current position of subscription
         * @param arg3 excess argument
         */
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub
            Subscription subscription = (Subscription)adapter.getItemAtPosition(position);
            String name = subscription.getName();
            String date = subscription.getDate();
            String charge = subscription.getCharge().substring(1);
            String comment = subscription.getComment();


            Intent intent = new Intent(MainActivity.this, DisplayEditSubscription.class);
            intent.putExtra(NAME, name);
            intent.putExtra(DATE, date);
            intent.putExtra(CHARGE, charge);
            intent.putExtra(COMMENT, comment);
            intent.putExtra(POSITION, position);
            startActivity(intent);

        }
    }

}



