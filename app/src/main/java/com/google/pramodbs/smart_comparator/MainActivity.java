package com.google.pramodbs.smart_comparator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    ListView options;
    String[] strings = {"Shop Online","Book a Cab", "Book a Hotel"};

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent shopintent=new Intent(MainActivity.this,shopactivity.class);
        final Intent cabintent=new Intent(MainActivity.this,cabactivity.class);
        final Intent hotelintent=new Intent(MainActivity.this,hotelactivity.class);


        options = (ListView) findViewById(R.id.options);
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, strings);

        options.setAdapter(adapter);

        options.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                if(i==0){
                    MainActivity.this.startActivity(shopintent);
                }
                if(i==1){
                    MainActivity.this.startActivity(cabintent);
                }
                if(i==2){
                    MainActivity.this.startActivity(hotelintent);
                }
            }
        });

    }
}
