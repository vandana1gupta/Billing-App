package com.example.mahesh.iintent;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText nm;
    TextView tvname;
    CardView create_bill,view_cust,del_data;
    ProgressBar progressBar;
    LinearLayout logo_layout;
    Button view_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.progress_bar);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        },1900);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animate();
                animate2();

            }
        },2000);



        /*Button add_cust = (Button) findViewById(R.id.add_mybtn);
        add_cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, addnameActivity.class);
                startActivity(intent);
            }
        });
*/

        create_bill =  findViewById(R.id.bill_btn);
        create_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        view_cust =  findViewById(R.id.btnview_cust);
        view_cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });

        /*view_data = (Button) findViewById(R.id.btnview_data);
        view_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity_data.class);
                startActivity(intent);
            }
        });*/

        del_data =  findViewById(R.id.btndelete_data);
        del_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, delete_bill.class);
                startActivity(intent);
            }
        });
    }

    public void animate()
    {
        LinearLayout dialog   = (LinearLayout)findViewById(R.id.linear_main);
        dialog.setVisibility(LinearLayout.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        animation.setDuration(900);
        dialog.setAnimation(animation);
        dialog.animate();
        animation.start();


    }

    public void animate2()
    {
        logo_layout = findViewById(R.id.linear_logo);
        logo_layout.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_logo);
        animation.setDuration(900);
        logo_layout.setAnimation(animation);
        logo_layout.animate();
        animation.start();


    }





    public void next(View view)
    {
        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
    }
}
