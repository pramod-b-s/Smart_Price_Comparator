package com.google.pramodbs.smart_comparator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class cabactivity extends AppCompatActivity {

    ListView options;
    String[] strings = {"Ola", "Uber"};
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Toast.makeText(getApplicationContext(),"Best Cab prices !",Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabactivity);

        options = (ListView) findViewById(R.id.caboptions);
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, strings);

        options.setAdapter(adapter);

        options.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Toast.makeText(getApplicationContext(),""+strings[i],Toast.LENGTH_SHORT).show();
                if(i==0){

                }
                if(i==1){

                }
            }
        });

    }

}
