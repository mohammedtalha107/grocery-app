package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.view.View.GONE;

public class orders extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private String userid;
    private RecyclerView rv1;
    private orderAdapter adapter;
    private ProgressBar pborders;
    private TextView count1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Your orders");

        Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Note : Orders that are shipped cannot be cancelled",Snackbar.LENGTH_LONG);
        snackbar.show();
        snackbar.setDuration(6000);

        fAuth= FirebaseAuth.getInstance();
        userid=fAuth.getCurrentUser().getUid();
        pborders=findViewById(R.id.pborder);
        count1=findViewById(R.id.count);
        rv1=findViewById(R.id.orderlist);
        rv1.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<ordersPost> options = new FirebaseRecyclerOptions.Builder<ordersPost>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("order list")
                                .child(userid)
                                , ordersPost.class)
                                .build();

         adapter=new orderAdapter(options);
         rv1.setAdapter(adapter);


        FirebaseDatabase.getInstance().getReference().child("order list").child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                pborders.setVisibility(GONE);
                int childrenCount=(int) dataSnapshot.getChildrenCount();

                if(childrenCount==0)
                {
                    count1.setText("No orders :(");
                }



            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(orders.this, "Error in DB connectivity", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(orders.this,navdraw.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}