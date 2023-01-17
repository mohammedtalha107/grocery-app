package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;

public class Vieweditcart extends AppCompatActivity {
    public static final String totalamnt="com.example.MADproject.example.totalamnt";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcess1;
    private TextView empty;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userid,total,totaltopass;
    private int totalint,grandtotal=0;
    private Editcart adapter;
    private ProgressBar pb;
    private CardView cv1;
    public static int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vieweditcart);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit your cart");

        recyclerView=(RecyclerView) findViewById(R.id.cartList);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        empty= findViewById(R.id.emptyornot);

        pb=findViewById(R.id.pbcart);
        cv1=findViewById(R.id.cardView1);

        fAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();

        userid=fAuth.getCurrentUser().getUid();


        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("cart list");
        FirebaseRecyclerOptions<cartfirebase> options=new FirebaseRecyclerOptions.Builder<cartfirebase>()
                .setQuery(cartListRef.child("User cart view")
                        .child(userid).child("Products"),cartfirebase.class).build();

        adapter = new Editcart(options);
        recyclerView.setAdapter(adapter);


        final DatabaseReference cartListRef2= FirebaseDatabase.getInstance().getReference()
                .child("cart list")
                .child("User cart view")
                .child(userid)
                .child("Products");

        cartListRef2.addValueEventListener(new ValueEventListener() {
            @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    total=String.valueOf(dataSnapshot1.child("Total").getValue());
                    totalint=Integer.parseInt(total);
                    grandtotal=grandtotal+totalint;

                    Log.d("total", "total amount is: "+grandtotal);
                }



                if(grandtotal==0) {

                    empty.setText("Your cart is empty");
                }

                else
                {
                    empty.setVisibility(GONE);
                }

                pb.setVisibility(View.INVISIBLE);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Vieweditcart.this, "Error in DB connectivity", Toast.LENGTH_SHORT).show();
            }
        });




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

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent=new Intent(getApplicationContext(),Viewcart.class);
                startActivity(intent);
                finish();
                Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void onBackPressed()
    {
        Intent intent=new Intent(getApplicationContext(),Viewcart.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
        return;
    }


}