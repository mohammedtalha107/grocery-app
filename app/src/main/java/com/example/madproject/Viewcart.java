package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class Viewcart extends AppCompatActivity {
    public static final String totalamnt="com.example.MADproject.example.totalamnt";
    public static final String Singletotal="com.example.MADproject.example.Singletotal";
    public static final String items="com.example.MADproject.example.items";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcess,editcart;
    private TextView totalamount;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userid,total,totaltopass;
    private int totalint,grandtotal=0;
    private cartAdapter adapter;
    private ProgressBar pb;
    private CardView cv1;
    public static int i=1;
    String itemlist="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcart);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Your cart");

        recyclerView=(RecyclerView) findViewById(R.id.cartList);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        NextProcess=(Button) findViewById(R.id.cartnext);
        editcart=findViewById(R.id.editcartitm);

        final CardView.LayoutParams params=new CardView.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.MATCH_PARENT);
        params.gravity= Gravity.CENTER;
        params.topMargin=30;
        params.width= 700;
        params.height=150;


        totalamount=(TextView) findViewById(R.id.cartgrandtotal);
        pb=findViewById(R.id.pbcart);
        cv1=findViewById(R.id.cardView1);

        fAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();

        userid=fAuth.getCurrentUser().getUid();


        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("cart list");
        FirebaseRecyclerOptions<cartfirebase> options=new FirebaseRecyclerOptions.Builder<cartfirebase>()
                .setQuery(cartListRef.child("User cart view")
                        .child(userid).child("Products"),cartfirebase.class).build();

        adapter = new cartAdapter(options);
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
                     itemlist = itemlist + "\n" + String.valueOf(dataSnapshot1.child("ItemName").getValue()) + "("
                            + String.valueOf(dataSnapshot1.child("quantity").getValue()) + " kg)";

                    totalint=Integer.parseInt(total);
                     grandtotal=grandtotal+totalint;

                    Log.d("total", "total amount is: "+grandtotal);
                }

                editcart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(Viewcart.this, Vieweditcart.class);
                        startActivity(intent);
                    }
                });


                if(grandtotal>0) {

                    cart.tempval=0;
                    totalamount.setText("Grand total : Rs." + grandtotal);
                    editcart.setVisibility(View.VISIBLE);
                    NextProcess.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Log.d("confirm order", "clicked confirm");
                            Intent intent=new Intent(getApplicationContext(),confirmFinalOrder.class);
                            intent.putExtra(totalamnt, totaltopass);
                            intent.putExtra(Singletotal,total);
                            intent.putExtra(items,itemlist);
                            startActivity(intent);

                        }
                    });
                }
                else if(grandtotal==0){
                    totalamount.setText("Your cart is empty");
                    editcart.setVisibility(View.INVISIBLE);

                    NextProcess.setLayoutParams(params);
                    NextProcess.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);

                    NextProcess.setText("Start shopping");
                    NextProcess.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(getApplicationContext(),PostListActivity.class);
                            startActivity(intent);

                        }
                    });
                }
                totaltopass=Integer.toString(grandtotal);
                pb.setVisibility(View.INVISIBLE);
                cv1.setVisibility(View.VISIBLE);
                NextProcess.setVisibility(View.VISIBLE);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Viewcart.this, "Error in DB connectivity", Toast.LENGTH_SHORT).show();
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

    public void onBackPressed()
    {
        Intent intent=new Intent(getApplicationContext(),navdraw.class);
        startActivity(intent);
        finish();
        return;
    }
}