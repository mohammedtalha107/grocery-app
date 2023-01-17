package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class orderSuccess extends AppCompatActivity {

    private TextView items;
    private Button ordersbut,shopbut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        items=findViewById(R.id.itemdetails);
        ordersbut=findViewById(R.id.gotoorders);
        shopbut=findViewById(R.id.gotoshop);


        Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Your order will be shipped soon :)",Snackbar.LENGTH_LONG);
        snackbar.show();
        snackbar.setDuration(8000);

        ordersbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(orderSuccess.this,orders.class);
                startActivity(intent);
                finish();
            }
        });
        shopbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(orderSuccess.this,PostListActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}