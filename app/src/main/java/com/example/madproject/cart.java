package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Nullable;

import static android.view.View.GONE;

public class cart extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String fullname,email,userid,itemname,itemprice,itemurl,saveCurrentDate,saveCurrentTime,newemail,tot;
    TextView dispname,dispprice;
    ImageView dispimg;
    ElegantNumberButton quantityButton;
    Button additemtocart;
    int total;
    static int tempval=0;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userid = fAuth.getCurrentUser().getUid();

        dispname = (TextView) findViewById(R.id.cartitem);
        dispprice = (TextView) findViewById(R.id.cartprice);
        dispimg = (ImageView) findViewById(R.id.cartpic);
        quantityButton = (ElegantNumberButton) findViewById(R.id.quantity);
        additemtocart = (Button) findViewById(R.id.addtocart);
        progress=findViewById(R.id.addtocartpb);

        final Intent intent = getIntent();
        itemname = intent.getStringExtra(PostListActivity.EXTRA_TEXT);
        itemprice = intent.getStringExtra(PostListActivity.EXTRA_TEXT1);
        itemurl = intent.getStringExtra(PostListActivity.EXTRA_TEXT2);

        dispname.setText(itemname);
        dispprice.setText("Rs." + itemprice + " /kg");
        Picasso.get().load(itemurl).into(dispimg);
        final DocumentReference documentReference=fStore.collection("users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                fullname= documentSnapshot.getString("Fullname");
                email=documentSnapshot.getString("EmailId");
                newemail=email.replace(".",",");
            }
        });


        additemtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToCartList();
                progress.setVisibility(View.VISIBLE);
            }
        });
    }

            private void addingToCartList()
            {
                Calendar calendar=Calendar.getInstance();
                SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd, yyyy");
                saveCurrentDate=currentdate.format(calendar.getTime());

                SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime=currenttime.format(calendar.getTime());

                final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("cart list");
                final HashMap<String,Object> cartmap=new HashMap<>();

                int qint=Integer.parseInt(quantityButton.getNumber());
                int pint=Integer.parseInt(itemprice);
                total=  qint*pint;
                tot=Integer.toString(total);

                cartmap.put("Username",fullname);
                cartmap.put("email",newemail);
                cartmap.put("userId",userid);
                cartmap.put("ItemName",itemname);
                cartmap.put("Price",itemprice);
                cartmap.put("quantity",quantityButton.getNumber());
                cartmap.put("Total",tot);
                cartmap.put("Date_of_adding",saveCurrentDate);
                cartmap.put("Time_of_adding",saveCurrentTime);
                cartmap.put("imgUrl",itemurl);

                cartListRef.child("User cart view").child(userid)
                        .child("Products").child(itemname)
                        .updateChildren(cartmap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {

                                                 progress.setVisibility(View.INVISIBLE);
                                                Toast.makeText(cart.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(cart.this,PostListActivity.class);
                                                startActivity(intent);
                                                finish();
                                                tempval = 1;

                                }

                            }
                        });
            }




}

