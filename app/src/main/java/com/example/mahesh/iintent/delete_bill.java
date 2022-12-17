package com.example.mahesh.iintent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class delete_bill extends AppCompatActivity {

    Spinner spin_remove;
    Button btn_remove;
    customer_database customer_db;
    String cust_name;
    int id;
    ImageButton img_back;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_bill);

        img_back = findViewById(R.id.delete_back);

         customer_db = new customer_database(this);

        spin_remove = findViewById(R.id.remove_spinner);
        btn_remove = findViewById(R.id.btn_rem);

        loadSpinnerData();


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(new Intent(delete_bill.this,MainActivity.class));
            }
        });

        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spin_remove.getSelectedItem()==null){
                    toastMessage("No Customers");
                }else{
                    cust_name = spin_remove.getSelectedItem().toString();

                    Cursor dat= customer_db.getItemID(cust_name);
                    if(dat.getCount()==0){
                        toastMessage(cust_name+" is already removed");
                    }


                    String n = spin_remove.getSelectedItem().toString();
                    Cursor res = customer_db.getdata(n);
                    if(res.getCount()==0){
                        delData();
                        loadSpinnerData();
                        return;
                    }
                    while(res.moveToNext()){
                        id = res.getInt(0);
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(delete_bill.this);
                    builder.setMessage("Are You Sure You want to delete data");
                    builder.setCancelable(true);
                    builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            delData();
                            loadSpinnerData();
                        }
                    });
                    builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        });

    }


    private void loadSpinnerData() {
        customer_database db = new customer_database(getApplicationContext());

        List<String> labels = db.getalldata();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin_remove.setAdapter(dataAdapter);
    }

    public void delData() {
        boolean insertData = customer_db.deleteName2(spin_remove.getSelectedItem().toString());

        if (insertData) {
            toastMessage(cust_name+" removed");

        } else {
            toastMessage( "Something went wrong" );
        }
    }

    private void toastMessage(String message) {
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show();
    }
}
