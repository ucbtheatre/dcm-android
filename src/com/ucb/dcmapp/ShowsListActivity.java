package com.ucb.dcmapp;

import java.util.*;

import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.app.ListActivity;


public class ShowsListActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        String[] brocabs = {"Brotocol","Brobot","Theodore Broosevelt"};
        ArrayList<String> brocabList = new ArrayList<String>(Arrays.asList(brocabs));
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,brocabList));
    }
}
