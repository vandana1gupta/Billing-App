package com.example.mahesh.iintent;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    customer_database customer_database;

    private ListView mListView;
    ImageButton img_back;


    String cust_name;
    String service,total;
    String item1,item2,item3,item4,item5;
    String qty1,qty2,qty3,qty4,qty5;
    String amt1,amt2,amt3,amt4,amt5;
    String str_time,str_date;


    String cust_namedb,id;
    String servicedb,totaldb;
    String item1db,item2db,item3db,item4db,item5db;
    String qty1db,qty2db,qty3db,qty4db,qty5db;
    String amt1db,amt2db,amt3db,amt4db,amt5db;
    String timedb,datedb;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        mListView = (ListView) findViewById(R.id.listView);
        customer_database = new customer_database(this);


        img_back = findViewById(R.id.list_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = customer_database.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add_bill it to the ArrayList
            listData.add(data.getString(1));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();

                //Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = customer_database.getItemID(name); //get the id associated with that name
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }

                if(itemID > -1){
                   // Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(ListDataActivity.this, update_bill.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",name);
                    //startActivity(editScreenIntent);

                    Cursor res = customer_database.getdata2(itemID);
                    if(res.getCount()==0){
                        Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_LONG).show();
                        return;
                    }

                    StringBuffer buffer = new StringBuffer();
                    while(res.moveToNext()){

                        buffer.append("id :"+res.getString(0)+"\n");
                        buffer.append("cust_name :"+res.getString(1)+"\n");

                        buffer.append("str_date :"+res.getString(2)+"\n");
                        buffer.append("str_time :"+res.getString(3)+"\n");
                        buffer.append("service1 :"+res.getString(4)+"\n");

                        buffer.append("item1 :"+res.getString(5)+"\n");
                        buffer.append("qty1 :"+res.getString(6)+"\n");
                        buffer.append("amt1 :"+res.getString(7)+"\n");

                        buffer.append("item2 :"+res.getString(8)+"\n");
                        buffer.append("qty2 :"+res.getString(9)+"\n");
                        buffer.append("amt2 :"+res.getString(10)+"\n");

                        buffer.append("item3 :"+res.getString(11)+"\n");
                        buffer.append("qty3 :"+res.getString(12)+"\n");
                        buffer.append("amt3 :"+res.getString(13)+"\n");

                        buffer.append("item4 :"+res.getString(14)+"\n");
                        buffer.append("qty4 :"+res.getString(15)+"\n");
                        buffer.append("amt4 :"+res.getString(16)+"\n");

                        buffer.append("item5 :"+res.getString(17)+"\n");
                        buffer.append("qty5 :"+res.getString(18)+"\n");
                        buffer.append("amt5 :"+res.getString(19)+"\n");

                        buffer.append("total :"+res.getString(20)+"\n");

                        id = res.getString(0);
                        cust_name = res.getString(1);

                        str_date = res.getString(2);
                        str_time = res.getString(3);
                        service = res.getString(4);

                        item1 = res.getString(5);
                        item2 = res.getString(8);
                        item3 = res.getString(11);
                        item4 = res.getString(14);
                        item5 = res.getString(17);

                        qty1 = res.getString(6);
                        qty2 = res.getString(9);
                        qty3 = res.getString(12);
                        qty4 = res.getString(15);
                        qty5 = res.getString(18);

                        amt1 = res.getString(7);
                        amt2 = res.getString(10);
                        amt3 = res.getString(13);
                        amt4 = res.getString(16);
                        amt5 = res.getString(19);

                        total = res.getString(20);
                        //toastMessage(service);

                    }

                    editScreenIntent.putExtra("id2",id);
                    editScreenIntent.putExtra("name2",cust_name);

                    editScreenIntent.putExtra("date",str_date);
                    editScreenIntent.putExtra("time",str_time);
                    editScreenIntent.putExtra("service1",service);

                    editScreenIntent.putExtra("item1",item1);
                    editScreenIntent.putExtra("item2",item2);
                    editScreenIntent.putExtra("item3",item3);
                    editScreenIntent.putExtra("item4",item4);
                    editScreenIntent.putExtra("item5",item5);


                    editScreenIntent.putExtra("qty1",qty1);
                    editScreenIntent.putExtra("qty2",qty2);
                    editScreenIntent.putExtra("qty3",qty3);
                    editScreenIntent.putExtra("qty4",qty4);
                    editScreenIntent.putExtra("qty5",qty5);

                    editScreenIntent.putExtra("amt1",amt1);
                    editScreenIntent.putExtra("amt2",amt2);
                    editScreenIntent.putExtra("amt3",amt3);
                    editScreenIntent.putExtra("amt4",amt4);
                    editScreenIntent.putExtra("amt5",amt5);

                    editScreenIntent.putExtra("total",total);

                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}