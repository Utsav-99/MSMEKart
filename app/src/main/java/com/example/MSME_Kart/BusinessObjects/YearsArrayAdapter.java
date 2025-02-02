package com.example.MSME_Kart.BusinessObjects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import java.util.ArrayList;



public class YearsArrayAdapter extends ArrayAdapter<Years> {
    private Context context; // The current context
    private ArrayList<Years> listOfYears = null; // An ArrayList of years

    public YearsArrayAdapter(Context context, ArrayList<Years> listOfYears) {
        super(context, 0, listOfYears); // Inherit the features from the base Array adapter class using the super keyword.
        this.context = context;
        this.listOfYears = listOfYears;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) { // Gets the view.
        View listOfItems = convertView;

        if (listOfItems == null) { // If there is no items
            listOfItems = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false); // Inflate the list of items

            Years theYears = listOfYears.get(position); // Get the item position of the month
        }

        return listOfItems; // Return back the list of items
    }
}
