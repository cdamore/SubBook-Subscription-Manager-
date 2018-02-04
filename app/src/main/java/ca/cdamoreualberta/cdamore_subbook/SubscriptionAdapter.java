/*
 * SubscriptionAdapter
 *
 * February, 3, 2018
 *
 * Copyright 2018...
 */

package ca.cdamoreualberta.cdamore_subbook;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Represents an adapter for the subscriptions
 *
 * @author Colin D'Amore
 */

public class SubscriptionAdapter extends ArrayAdapter<Subscription> {

    private final Activity context;

    /**
     * Constructor for subscription adapter
     *
     * @param context activity
     * @param subscriptions current subscription
     */

    public SubscriptionAdapter(Activity context, ArrayList<Subscription> subscriptions) {
        super(context, R.layout.list_single, subscriptions);
        this.context = context;
    }

    /**
     * Overridden method to set values for name, date, and charge in the textview
     *
     * @param position position of current subscription
     * @param convertView the current virw (textview)
     * @param parent parent of textview
     * @return
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtName = (TextView) rowView.findViewById(R.id.txt_name);
        TextView txtDate = (TextView) rowView.findViewById(R.id.txt_date);
        TextView txtCharge= (TextView) rowView.findViewById(R.id.txt_charge);

        // Replace text with my own
        txtName.setText(getItem(position).getName());
        txtDate.setText(getItem(position).getDate());
        txtCharge.setText(getItem(position).getCharge());

        return rowView;
    }

}
