package com.example.mahesh.iintent;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class addnameActivity extends AppCompatActivity {

    Button add_custbtn;
    EditText etcust_name;
    DatabaseHelper databaseHelper;
    String cname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addname);

        databaseHelper = new DatabaseHelper(addnameActivity.this);


        etcust_name=(EditText) findViewById(R.id.et_custname);
        add_custbtn = (Button) findViewById(R.id.add_btn);
        add_custbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseHelper = new DatabaseHelper(addnameActivity.this);

                cname = etcust_name.getText().toString().trim();


                if(cname.length()==0){
                    toastMessage("Enter Name");
                }else {
                    Cursor dat= databaseHelper.getItemID(cname);
                    if(dat.getCount()==0){

                        AddData();
                        etcust_name.setText("");
                    }
                    else{
                        toastMessage(cname+" ALREADY EXIST");
                    }
                }

            }
        });

    }

    public void AddData() {
        boolean insertData = databaseHelper.addData(cname);

        if (insertData) {
            toastMessage( cname+" ADDED" );

        } else {
            toastMessage( "Something went wrong" );
        }
    }

    private void toastMessage(String message) {
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show();
    }

    public void animate(View view){
        LinearLayout dialog   = (LinearLayout)findViewById(R.id.dialog);
        dialog.setVisibility(LinearLayout.VISIBLE);
        Animation animation   =    AnimationUtils.loadAnimation(this, R.anim.anim);
        animation.setDuration(500);
        dialog.setAnimation(animation);
        dialog.animate();
        animation.start();
    }
}