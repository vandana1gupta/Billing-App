package com.example.mahesh.iintent;

import android.Manifest;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

    customer_database mydb;

    Button add_btn;
    EditText et_cust_name,et_item1,et_item2,et_item3,et_item4,et_item5;
    EditText et_amt1,et_amt2,et_amt3,et_amt4,et_amt5;
    Spinner spinner1,spinner2,spinner3,spinner4,spinner5,spinner_ser1;
    TextView date,time,tv_total;
    ImageButton img_back;

    String cust_name,no;
    String service,total;
    String item1,item2,item3,item4,item5;
    String qty1,qty2,qty3,qty4,qty5;
    String amt1,amt2,amt3,amt4,amt5;
    String str_time,str_date;

    String cust_namedb;
    String servicedb,totaldb;
    String item1db,item2db,item3db,item4db,item5db;
    String qty1db,qty2db,qty3db,qty4db,qty5db;
    String amt1db,amt2db,amt3db,amt4db,amt5db;
    String timedb,datedb;

    Double dtotal;
    Double damt1,damt2,damt3,damt4,damt5;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private static final String TAG = "PdfCreatorActivity";
    private File pdfFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        img_back = findViewById(R.id.create_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

         date = (TextView) findViewById(R.id.tv_date);
         time = (TextView) findViewById(R.id.tv_time);

        mydb = new customer_database(Main2Activity.this);


        SimpleDateFormat date_time = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault());
        String curr_date = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(new Date());

        //String current_date = java.text.DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
        date.setText(curr_date);

        String curr_time = new SimpleDateFormat("HH:mm",Locale.getDefault()).format(new Date());
        time.setText(curr_time);

        spinner1 = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

         spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.items, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner3 = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.items, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

         spinner4 = (Spinner) findViewById(R.id.spinner4);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.items, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);

         spinner5 = (Spinner) findViewById(R.id.spinner5);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.items, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);


         spinner_ser1 = (Spinner) findViewById(R.id.spinner_ser1);
        ArrayAdapter<CharSequence> adapter_ser = ArrayAdapter.createFromResource(this,
                R.array.service, android.R.layout.simple_spinner_item);
        adapter_ser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ser1.setAdapter(adapter_ser);

        tv_total = findViewById(R.id.tv_total);

        et_cust_name=findViewById(R.id.et_addcustname);

        et_item1=findViewById(R.id.et_item1);
        et_item2=findViewById(R.id.et_item2);
        et_item3=findViewById(R.id.et_item3);
        et_item4=findViewById(R.id.et_item4);
        et_item5=findViewById(R.id.et_item5);


        et_amt1=findViewById(R.id.et_amt1);
        et_amt2=findViewById(R.id.et_amt2);
        et_amt3=findViewById(R.id.et_amt3);
        et_amt4=findViewById(R.id.et_amt4);
        et_amt5=findViewById(R.id.et_amt5);

        //create_pdf = findViewById(R.id.btn_pdf);
        //total_btn = findViewById(R.id.btn_total);


        /*total_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double damt1 = Double.parseDouble(et_amt1.getText().toString());
                Double damt2 = Double.parseDouble(et_amt2.getText().toString());
                Double damt3 = Double.parseDouble(et_amt3.getText().toString());
                Double damt4 = Double.parseDouble(et_amt4.getText().toString());
                Double damt5 = Double.parseDouble(et_amt5.getText().toString());

                Double dtotal = damt1+damt2+damt3+damt4+damt5;

                total = String.format("%.2f",dtotal);

                tv_total = findViewById(R.id.tv_total);
                tv_total.setText(total);

            }
        });*/



        add_btn = findViewById(R.id.btn_add);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String am1 = et_amt1.getText().toString();
                String am2 = et_amt2.getText().toString();
                String am3 = et_amt3.getText().toString();
                String am4 = et_amt4.getText().toString();
                String am5 = et_amt5.getText().toString();


                /*damt1 = Double.parseDouble(am1);
                damt2 = Double.parseDouble(am2);
                damt3 = Double.parseDouble(am3);
                damt4 = Double.parseDouble(am4);
                damt5 = Double.parseDouble(am5);*/

                if (am1.isEmpty() && am2.isEmpty() && am3.isEmpty() && am4.isEmpty() && am5.isEmpty())
                {
                    damt1 = 0.0;
                    damt2 = 0.0;
                    damt3 = 0.0;
                    damt4 = 0.0;
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }

                else if (am1.isEmpty() && am3.isEmpty() && am4.isEmpty() && am5.isEmpty())
                {
                    damt1 = 0.0;
                    damt2 = Double.parseDouble(am2);
                    damt3 = 0.0;
                    damt4 = 0.0;
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am1.isEmpty() && am2.isEmpty() && am4.isEmpty() && am5.isEmpty())
                {
                    damt1 = 0.0;
                    damt2 = 0.0;
                    damt3 = Double.parseDouble(am3);
                    damt4 = 0.0;
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am1.isEmpty() && am2.isEmpty() && am3.isEmpty() && am5.isEmpty())
                {
                    damt1 = 0.0;
                    damt2 = 0.0;
                    damt3 = 0.0;
                    damt4 = Double.parseDouble(am4);
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am1.isEmpty() && am2.isEmpty() && am3.isEmpty() && am4.isEmpty())
                {
                    damt2 = 0.0;
                    damt3 = 0.0;
                    damt1 = 0.0;
                    damt4 = 0.0;
                    damt5 = Double.parseDouble(am5);

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }

                else if (am1.equals("") && am2.equals("") && am3.equals(""))
                {
                    damt1 = 0.0;
                    damt2 = 0.0;
                    damt3 = 0.0;
                    damt4 = Double.parseDouble(am4);
                    damt5 = Double.parseDouble(am5);

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am1.isEmpty() && am2.isEmpty() && am4.isEmpty())
                {
                    damt1 = 0.0;
                    damt2 = 0.0;
                    damt3 = Double.parseDouble(am3);
                    damt4 = 0.0;
                    damt5 = Double.parseDouble(am5);

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am1.isEmpty() && am2.isEmpty() && am5.isEmpty())
                {
                    damt1 = 0.0;
                    damt2 = 0.0;
                    damt3 = Double.parseDouble(am3);
                    damt4 = Double.parseDouble(am4);
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am1.isEmpty() && am3.isEmpty() && am4.isEmpty())
                {
                    damt1 = 0.0;
                    damt2 = Double.parseDouble(am2);
                    damt3 = 0.0;
                    damt4 = 0.0;
                    damt5 = Double.parseDouble(am5);

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am1.isEmpty() && am3.isEmpty() && am5.isEmpty())
                {
                    damt1 = 0.0;
                    damt2 = Double.parseDouble(am2);
                    damt3 = 0.0;
                    damt4 = Double.parseDouble(am4);
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am1.isEmpty() && am4.isEmpty() && am5.isEmpty())
                {
                    damt1 = 0.0;
                    damt2 = Double.parseDouble(am2);
                    damt3 = Double.parseDouble(am3);
                    damt4 = 0.0;
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }

                else if (am1.isEmpty() && am2.isEmpty())
                {
                    damt1 = 0.0;
                    damt2 = 0.0;
                    damt3 = Double.parseDouble(am3);
                    damt4 = Double.parseDouble(am4);
                    damt5 = Double.parseDouble(am5);

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am1.equals("") && am3.equals(""))
                {
                    damt1 = 0.0;
                    damt2 = Double.parseDouble(am2);
                    damt3 = 0.0;
                    damt4 = Double.parseDouble(am4);
                    damt5 = Double.parseDouble(am5);

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am1.equals("") && am4.equals(""))
                {
                    damt1 = 0.0;
                    damt2 = Double.parseDouble(am2);
                    damt3 = Double.parseDouble(am3);
                    damt4 = 0.0;
                    damt5 = Double.parseDouble(am5);

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am1.equals("") && am5.equals(""))
                {
                    damt1 = 0.0;
                    damt2 = Double.parseDouble(am2);
                    damt3 = Double.parseDouble(am3);
                    damt4 = Double.parseDouble(am4);
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }


                else if (am2.isEmpty() && am3.isEmpty() && am4.isEmpty() && am5.isEmpty())
                {
                    damt1 = Double.parseDouble(am1);
                    damt2 = 0.0;
                    damt3 = 0.0;
                    damt4 = 0.0;
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am2.isEmpty() && am3.isEmpty() && am4.isEmpty())
                {
                    damt1 = Double.parseDouble(am1);
                    damt2 = 0.0;
                    damt3 = 0.0;
                    damt4 = 0.0;
                    damt5 = Double.parseDouble(am5);

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am2.isEmpty() && am3.isEmpty() && am5.isEmpty())
                {
                    damt1 = Double.parseDouble(am1);
                    damt2 = 0.0;
                    damt3 = 0.0;
                    damt4 = Double.parseDouble(am4);
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }

                else if (am2.isEmpty() && am4.isEmpty() && am5.isEmpty())
                {
                    damt1 = Double.parseDouble(am1);
                    damt2 = 0.0;
                    damt3 = Double.parseDouble(am3);
                    damt4 = 0.0;
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }

                else if (am2.equals("") && am3.equals(""))
                {
                    damt1 = Double.parseDouble(am1);
                    damt2 = 0.0;
                    damt3 = 0.0;
                    damt4 = Double.parseDouble(am4);
                    damt5 = Double.parseDouble(am5);

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am2.equals("") && am4.equals(""))
                {
                    damt1 = Double.parseDouble(am1);
                    damt2 = 0.0;
                    damt3 = Double.parseDouble(am3);
                    damt4 = 0.0;
                    damt5 = Double.parseDouble(am5);

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am2.equals("") && am5.equals(""))
                {
                    damt1 = Double.parseDouble(am1);
                    damt2 = 0.0;
                    damt3 = Double.parseDouble(am3);
                    damt4 = Double.parseDouble(am4);
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }

                else if (am3.isEmpty() && am4.isEmpty() && am5.isEmpty())
                {
                    damt1 = Double.parseDouble(am1);
                    damt2 = Double.parseDouble(am2);
                    damt3 = 0.0;
                    damt4 = 0.0;
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }

                else if (am3.equals("") && am4.equals(""))
                {
                    damt1 = Double.parseDouble(am1);
                    damt2 = Double.parseDouble(am2);
                    damt3 = 0.0;
                    damt4 = 0.0;
                    damt5 = Double.parseDouble(am5);

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                else if (am3.equals("") && am5.equals(""))
                {
                    damt1 = Double.parseDouble(am1);
                    damt2 = Double.parseDouble(am2);
                    damt3 = 0.0;
                    damt4 = Double.parseDouble(am4);
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }

                else if (am4.equals("") && am5.equals(""))
                {
                    damt1 = Double.parseDouble(am1);
                    damt2 = Double.parseDouble(am2);
                    damt3 = Double.parseDouble(am3);
                    damt4 = 0.0;
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }

                else if (am1.isEmpty())
                {
                    damt1 = 0.0;
                    damt2 = Double.parseDouble(am2);
                    damt3 = Double.parseDouble(am3);
                    damt4 = Double.parseDouble(am4);
                    damt5 = Double.parseDouble(am5);
                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);

                }
                else if (am2.isEmpty())
                {
                    damt2 = 0.0;
                    damt1 = Double.parseDouble(am1);
                    damt3 = Double.parseDouble(am3);
                    damt4 = Double.parseDouble(am4);
                    damt5 = Double.parseDouble(am5);


                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);

                }
                else if (am3.isEmpty())
                {
                    damt3 = 0.0;

                    damt1 = Double.parseDouble(am1);
                    damt2 = Double.parseDouble(am2);
                    damt4 = Double.parseDouble(am4);
                    damt5 = Double.parseDouble(am5);


                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);

                }
                else if (am4.isEmpty())
                {

                    damt4 = 0.0;

                    damt1 = Double.parseDouble(am1);
                    damt2 = Double.parseDouble(am2);
                    damt3 = Double.parseDouble(am3);
                    damt5 = Double.parseDouble(am5);

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);

                }
                else if (am5.isEmpty())
                {
                    damt1 = Double.parseDouble(am1);
                    damt2 = Double.parseDouble(am2);
                    damt3 = Double.parseDouble(am3);
                    damt4 = Double.parseDouble(am4);
                    damt5 = 0.0;

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }

                if (!am1.isEmpty() && !am2.isEmpty() && !am3.isEmpty() && !am4.isEmpty() && !am5.isEmpty())
                {
                    damt1 = Double.parseDouble(am1);
                    damt2 = Double.parseDouble(am2);
                    damt3 = Double.parseDouble(am3);
                    damt4 = Double.parseDouble(am4);
                    damt5 = Double.parseDouble(am5);

                    dtotal = damt1 + damt2 + damt3 + damt4 + damt5;
                    total = String.format("%.2f", dtotal);
                    tv_total.setText(total);
                }
                
                
                

                mydb = new customer_database(Main2Activity.this);

                 cust_name = et_cust_name.getText().toString();

                Cursor create_data= mydb.getItemID(cust_name);
                if(create_data.getCount()==0){

                    cust_name = et_cust_name.getText().toString();

                    item1 = et_item1.getText().toString();
                    item2 = et_item2.getText().toString();
                    item3 = et_item3.getText().toString();
                    item4 = et_item4.getText().toString();
                    item5 = et_item5.getText().toString();

                    amt1 = et_amt1.getText().toString();
                    amt2 = et_amt2.getText().toString();
                    amt3 = et_amt3.getText().toString();
                    amt4 = et_amt4.getText().toString();
                    amt5 = et_amt5.getText().toString();

                    qty1 = spinner1.getSelectedItem().toString();
                    qty2 = spinner2.getSelectedItem().toString();
                    qty3 = spinner3.getSelectedItem().toString();
                    qty4 = spinner4.getSelectedItem().toString();
                    qty5 = spinner5.getSelectedItem().toString();


                    str_date = date.getText().toString();
                    str_time = time.getText().toString();

                    service = spinner_ser1.getSelectedItem().toString();


                    AddData();


               }
                else
                {
                    toastMessage("Already Created");
                }


            }
        });


/*
        create_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cust_name = et_cust_name.getText().toString();


                Cursor res = mydb.getdata(cust_name);
                if(res.getCount()==0){
                    Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_LONG).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){

                    buffer.append("id :"+res.getString(0)+"\n");
                    buffer.append("name :"+res.getString(1)+"\n");

                    buffer.append("date :"+res.getString(2)+"\n");
                    buffer.append("time :"+res.getString(3)+"\n");
                    buffer.append("service :"+res.getString(4)+"\n");

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


                    no = res.getString(0);
                    cust_namedb = res.getString(1);

                    datedb = res.getString(2);
                    timedb = res.getString(3);
                    servicedb = res.getString(4);

                    item1db = res.getString(5);
                    item2db = res.getString(8);
                    item3db = res.getString(11);
                    item4db = res.getString(14);
                    item5db = res.getString(17);

                    qty1db = res.getString(6);
                    qty2db = res.getString(9);
                    qty3db = res.getString(12);
                    qty4db = res.getString(15);
                    qty5db = res.getString(18);


                    amt1db = res.getString(7);
                    amt2db = res.getString(10);
                    amt3db = res.getString(13);
                    amt4db = res.getString(16);
                    amt5db = res.getString(19);




                    totaldb = res.getString(20);



                }



                if (et_cust_name.getText().toString().isEmpty()){
                    et_cust_name.setError("This field is empty");
                    et_cust_name.requestFocus();
                    return;
                }
                try {
                    createPdfWrapper();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }

        });
*/


    }

    public void AddData() {
        boolean insertData = mydb.addData2(cust_name,str_date,str_time,service,item1,qty1,amt1,item2,qty2,amt2,item3,qty3,amt3,
                                            item4,qty4,amt4,item5,qty5,amt5,total);
        //boolean insertData = mydb.addData2(cust_name);
        if (insertData) {
            toastMessage( "SAVED" );
            //startActivity(new Intent(CreateAte.this,ListDataActivity.class));

        }
        else {
            toastMessage( "Something went wrong" );
        }
    }


    private void createPdfWrapper() throws FileNotFoundException,DocumentException{

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }else {
            createPdf();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {


        cust_name = et_cust_name.getText().toString();

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/JBE");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        cust_name = et_cust_name.getText().toString();
        pdfFile = new File(docsFolder.getAbsolutePath(), cust_name + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, output);

        Rectangle rect = new Rectangle(84, 60, 520, 800);
        writer.setBoxSize("art", rect);
        HeaderFooter event = new HeaderFooter();
        writer.setPageEvent(event);

        document.open();
        //document.add_bill(new Paragraph(et_cust_name.getText().toString()));

        Date currentime = Calendar.getInstance().getTime();
        SimpleDateFormat d = new SimpleDateFormat("EEEE");
        String day = d.format(currentime);

        Chunk c = new Chunk("Day : " + day);
        Paragraph p_day = new Paragraph(c);
        p_day.setAlignment(Paragraph.ALIGN_LEFT);

        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.BOLD, BaseColor.BLACK);

        Chunk c1 = new Chunk("JBE", f);
        Paragraph p1 = new Paragraph(c1);
        p1.setAlignment(Paragraph.ALIGN_CENTER);

        Chunk c2 = new Chunk("Electronics selling & repairing shop");

        Paragraph p2 = new Paragraph(c2);
        p2.setAlignment(Paragraph.ALIGN_CENTER);


        document.add(p_day);
        document.add(p1);
        document.add(p2);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));


        Chunk cno = new Chunk("No. ");
        Chunk cno2 = new Chunk(no);

        Phrase phrase_no=new Phrase();
        phrase_no.add(cno);
        phrase_no.add(cno2);
        Paragraph pno = new Paragraph();
        pno.add(phrase_no);
        pno.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(pno);



        Font fname = new Font(Font.FontFamily.TIMES_ROMAN, 18.0f, Font.BOLD, BaseColor.BLACK);
        Chunk cname = new Chunk("Customer Name :- ", fname);
        Font fname2 = new Font(Font.FontFamily.HELVETICA, 18.0f, Font.UNDERLINE, BaseColor.BLACK);
        Chunk cname2 = new Chunk(cust_name, fname2);
        Phrase phrase = new Phrase();
        phrase.add(cname);
        phrase.add(cname2);

        Paragraph pname = new Paragraph();
        pname.add(phrase);
        pname.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pname);


//special font sizes
        Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
        Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
//create a paragraph
        Paragraph paragraph = new Paragraph("");


        //specify column widths
        float[] columnWidths = {1.5f, 7f, 2f, 3f};
        //create PDF table with the given widths
        PdfPTable table = new PdfPTable(columnWidths);
        // set table width a percentage of the page width
        table.setPaddingTop(20.0f);
        table.setWidthPercentage(90f);

        //insert column headings
        insertCell(table, "No", Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, "Item Name", Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, "Quantity", Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, "Amount", Element.ALIGN_CENTER, 1, bfBold12);
        table.setHeaderRows(1);

        insertCell(table, "1", Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, item1db, Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, qty1db, Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, amt1db, Element.ALIGN_CENTER, 1, bfBold12);
        table.setHeaderRows(1);

        insertCell(table, "2", Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, item2db, Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, qty2db, Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, amt2db, Element.ALIGN_CENTER, 1, bfBold12);
        table.setHeaderRows(1);

        insertCell(table, "3", Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, item3db, Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, qty3db, Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, amt3db, Element.ALIGN_CENTER, 1, bfBold12);
        table.setHeaderRows(1);

        insertCell(table, "4", Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, item4db, Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, qty4db, Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, amt4db, Element.ALIGN_CENTER, 1, bfBold12);
        table.setHeaderRows(1);

        insertCell(table, "5", Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, item5db, Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, qty5db, Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, amt5db, Element.ALIGN_CENTER, 1, bfBold12);
        table.setHeaderRows(1);



        //insert an empty row
        insertCell(table, "", Element.ALIGN_LEFT, 2, bfBold12);

        insertCell(table, "Total", Element.ALIGN_CENTER, 1, bfBold12);
        insertCell(table, total, Element.ALIGN_CENTER, 1, bfBold12);

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        //add_bill the PDF table to the paragraph
        paragraph.add(table);
        // add_bill the paragraph to the document
        document.add(paragraph);
        document.close();

    }

    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if(text.trim().equalsIgnoreCase("")){
            cell.setMinimumHeight(10f);
        }
        //add_bill the call to the table
        table.addCell(cell);

    }

    private void previewPdf() {

        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");

            startActivity(intent);
        }else{
            Toast.makeText(this,"Download a PDF Viewer to see the generated PDF",Toast.LENGTH_SHORT).show();
        }
    }

    public class HeaderFooter extends PdfPageEventHelper {
        // EditText mContentEditText = (EditText) findViewById(R.id.edit_text_content);
        Date currentime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dt = new SimpleDateFormat("hh:mm a");
        String formattedate = df.format(currentime);
        String formattetime = dt.format(currentime);

        public void onStartPage(PdfWriter writer, Document document) {
            Rectangle rect = writer.getBoxSize("art");
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Date : " + formattedate), rect.getLeft(), rect.getTop(), 0);

            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Paragraph("Time : " + formattetime), rect.getRight(), rect.getTop(), 0);
            // ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Date :- "+mContentEditText.getText().toString()), rect.getRight(), rect.getTop(),0);

        }

        public void onEndPage(PdfWriter writer, Document document) {
            Rectangle rect = writer.getBoxSize("art");
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Page 1"), rect.getRight(), rect.getBottom(), 0);
        }

    }




    private void toastMessage(String message) {
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show();
    }
}
