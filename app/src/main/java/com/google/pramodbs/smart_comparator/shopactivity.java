package com.google.pramodbs.smart_comparator;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class shopactivity extends AppCompatActivity {

    ListView options;
    String[] strings = {
            "Amazon",
            "Flipkart",
            "PayTM"
    };
    Integer[] imageId={
            R.drawable.amazon,
            R.drawable.flipkart,
            R.drawable.paytm
    };
    String[] results = {
            "",
            "",
            ""
    };
    //ArrayAdapter adapter;
    Button srch,rst;
    EditText text;
    String pdt,amazonresult,flipkartresult,paytmresult;
    String amazonprice,flipkartprice,paytmprice,info,amazontitle,flipkarttitle,paytmtitle;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Toast.makeText(getApplicationContext(),"Get THE Best Deal !",Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopactivity);

        options = (ListView) findViewById(R.id.shopoptions);
        srch=(Button)findViewById(R.id.search);
        rst=(Button)findViewById(R.id.reset);
        text=(EditText)findViewById(R.id.text) ;

        //adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, strings);
       // adapter.setDropDownViewResource();

        //options.setAdapter(adapter);

        CustomList adapter = new CustomList(shopactivity.this, strings, imageId);
        //options=(ListView)findViewById(R.id.options);
        options.setAdapter(adapter);

        options.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(shopactivity.this, "Redirecting to  " + strings[+ position], Toast.LENGTH_SHORT).show();

                if(position==0){
                    Uri uri = Uri.parse("http://www.amazon.in/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords=" + info ); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                if(position==1){
                    Uri uri = Uri.parse("https://www.flipkart.com/search?q=" + info + "&otracker=start&as-show=on&as=off"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                if(position==2){
                    Uri uri = Uri.parse("https://paytm.com/shop/search?q=" + info + "&from=organic&child_site_id=1&site_id=1&category=6241"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }

            }
        });

        srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info=text.getText().toString();
                new doit().execute();
            }
        });

        rst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("");
                for(int y=0;y<3;y++) {
                    switch (y) {
                        case 0:
                            strings[0]= "Amazon";
                            break;
                        case 1:
                            strings[1]= "Flipkart";
                            break;
                        case 2:
                            strings[2]= "PayTM";
                            break;
                    }
                }
                CustomList adapter = new CustomList(shopactivity.this, strings, imageId);
                options.setAdapter(adapter);
            }
        });

    }

    private class doit extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            info=text.getText().toString();
            super.onPreExecute();
            progressDialog=new ProgressDialog(shopactivity.this);
            progressDialog.setTitle("Breathe in.. Breathe out..");
            progressDialog.setMessage("Loading ...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
            //Toast.makeText(shopactivity.this, "", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document amazondocument = Jsoup.connect("http://www.amazon.in/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords="+info).get();
                //Document amazondocument = Jsoup.connect("http://www.amazon.in/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords="+info).get();
                amazontitle=amazondocument.title();

                String amazonresult = amazondocument.select("a[class=a-size-small a-color-secondary]").toString();
                //String result=amz.toString();

            } catch (IOException e) {
                //Toast.makeText(getApplicationContext(),"Amazon error",Toast.LENGTH_LONG);
                e.printStackTrace();
            }

            try{

                Document flipkartdocument = Jsoup.connect("https://www.flipkart.com/search?q="+info+"&otracker=start&as-show=on&as=off").get();
                flipkarttitle=flipkartdocument.title();

                //Elements flpkrt = flipkartdocument.select("#priceblock_ourprice");
                //flipkartprice=flpkrt.html();

            }catch (IOException e){
                //Toast.makeText(getApplicationContext(),"Flipkart error",Toast.LENGTH_LONG);
                e.printStackTrace();
            }

            try{

                Document paytmdocument = Jsoup.connect("https://paytm.com/shop/search?q="+info+"&from=organic&child_site_id=1&site_id=1&category=6241").get();
                paytmtitle=paytmdocument.title();

                 String paytmresult = paytmdocument.select("span[class=_1kMS]").toString();
                //paytmprice=paytm.html();

            }catch (IOException e){
                //Toast.makeText(getApplicationContext(),"PayTM error",Toast.LENGTH_LONG);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.hide();
            for(String x:strings){
                switch (x){
                    case "Amazon":
                        strings[0]+="  "+amazontitle;
                        break;
                    case "Flipkart":
                        strings[1]+="  "+flipkarttitle;
                        break;
                    case "PayTM":
                        strings[2]+="  "+paytmtitle;
                        break;
                }
            }

            CustomList adapter = new CustomList(shopactivity.this, strings, imageId);
            options.setAdapter(adapter);
            Toast.makeText(shopactivity.this, "There you go !", Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }



}

//unresolved search view doubt

/*search=(SearchView)findViewById(R.id.srch);
        CharSequence query = search.getQuery();
*?
        /*search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.makeText(getBaseContext(), String.valueOf(hasFocus),Toast.LENGTH_SHORT).show();
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getBaseContext(), query,Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    */

//options.setAdapter(ladapt);

        /*ladapt=new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return null;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        };*/

        /*options.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Toast.makeText(getApplicationContext(),""+strings[i],Toast.LENGTH_SHORT).show();
                if(i==0){
                    //options.text
                    /*Uri uri = Uri.parse("amzn://apps/android?"); //amzn://apps/android? //com.amazon.mobile.shopping://www.amazon.com/products/
                    //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    //startActivity(intent);
                }
                if(i==1){

                }
                if(i==2){

                }
            }
        });*/

