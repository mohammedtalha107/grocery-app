package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
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
import com.hbb20.CountryCodePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.annotation.Nullable;

public class confirmFinalOrder extends AppCompatActivity {

    private EditText phone,houseno,cityname,statename,pincode;
    private Button placeorder;
    private TextView grandtotal;
    public String saveCurrentDate,saveCurrentTime,userid,Fullname,Emailid,grandtot,singletot,datentime;
    public String phoneno,houseno1,cityname1,statename1,pincode1;
    public String itemlist,quantitylist;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    public DatabaseReference orderListRef;
    public HashMap<String,Object> ordermap;
    private ProgressBar pb;
    private Button plc,cnl;
    private ImageView closeicon;
    private String item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        getSupportActionBar().setTitle("Confirm your order");

        final Intent intent = getIntent();
         grandtot = intent.getStringExtra(Viewcart.totalamnt);
         singletot=intent.getStringExtra(Viewcart.Singletotal);
         item=intent.getStringExtra(Viewcart.items);


        phone=findViewById(R.id.phonenumber);
        houseno=findViewById(R.id.houseno);
        cityname=findViewById(R.id.city);
        statename=findViewById(R.id.state);
        pincode=findViewById(R.id.postalcode);
        grandtotal=findViewById(R.id.cartgrandtotal);
        placeorder=findViewById(R.id.place);
        pb=findViewById(R.id.confirmpb);
        grandtotal.setText("Your grand total is Rs."+grandtot);



        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userid=fAuth.getCurrentUser().getUid();

        final DocumentReference documentReference=fStore.collection("users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                Fullname= documentSnapshot.getString("Fullname");
                Emailid=documentSnapshot.getString("EmailId");

            }
        });





        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                phoneno = phone.getText().toString().replace(" ", "");
                houseno1 = houseno.getText().toString().trim();
                cityname1 = cityname.getText().toString().trim();
                statename1 = statename.getText().toString().trim();
                pincode1 = pincode.getText().toString().trim();

                Log.d("place order", "clicked place order ");

                if(phoneno.equals("")||phoneno.length()<10)
                {
                    phone.setError("Invalid phone number");

                }
                else if(houseno1.equals("")) {
                    houseno.setError("house number is left empty");

                }
                else if(cityname1.equals("")) {
                    cityname.setError("city name is left empty");

                }
                else if(statename1.equals("")) {
                    statename.setError("state name is left empty");

                }
                else if(pincode1.equals("")) {
                    pincode.setError("pincode is left empty");

                }
                else
                {


                //dialogbox
                final AlertDialog.Builder alert = new AlertDialog.Builder(confirmFinalOrder.this);
                View mview = getLayoutInflater().inflate(R.layout.confirmorderdialog, null);

                plc = mview.findViewById(R.id.dialogplace);
                cnl = mview.findViewById(R.id.dialogcancel);
                closeicon = mview.findViewById(R.id.dialogclose);
                alert.setView(mview);

                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                cnl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();

                    }
                });
                closeicon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();

                    }
                });

                plc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();

                        //from here

                        pb.setVisibility(View.VISIBLE);



                        //obtaining the details from cart to paste them in orders
                        final DatabaseReference cart_ref = FirebaseDatabase.getInstance().getReference()
                                .child("cart list")
                                .child("User cart view")
                                .child(userid)
                                .child("Products");

                        Calendar calendar=Calendar.getInstance();
                        SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd, yyyy");
                        saveCurrentDate=currentdate.format(calendar.getTime());

                        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss a");
                        saveCurrentTime=currenttime.format(calendar.getTime());

                        datentime=saveCurrentDate+" "+saveCurrentTime;


                        itemlist = "";
                        quantitylist = "";
//add

                        cart_ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot ds) {


                                for(DataSnapshot snapshot: ds.getChildren()){
                                    itemlist = itemlist + "\n" + String.valueOf(snapshot.child("ItemName").getValue()) + "("
                                            + String.valueOf(snapshot.child("quantity").getValue()) + " kg)";


                                    Log.d("itemsInDatachange", "Items are : "+itemlist);
                                  //  Log.d("total", "total amount is: "+grandtotal);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                        final DatabaseReference order_ref=FirebaseDatabase.getInstance().getReference()
                                .child("order list")
                                .child(userid)
                                .child(datentime);
                        final HashMap<String,Object> ordersmap=new HashMap<>();

                        ordersmap.put("Username",Fullname);
                        ordersmap.put("email",Emailid);
                        ordersmap.put("Items", item);
                        ordersmap.put("grandtotal",grandtot);
                        ordersmap.put("phone no",phoneno);
                        ordersmap.put("HouseNo",houseno1);
                        ordersmap.put("city",cityname1);
                        ordersmap.put("state",statename1);
                        ordersmap.put("pincode",pincode1);
                        ordersmap.put("Date_of_adding",saveCurrentDate);
                        ordersmap.put("Time_of_order",saveCurrentTime);
                        ordersmap.put("Status","Not shipped");



                                order_ref
                                .updateChildren(ordersmap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                                        FirebaseDatabase.getInstance().getReference()
                                                                .child("cart list")
                                                                .child("User cart view")
                                                                .child(userid)
                                                                .removeValue()
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful())
                                                                        {
                                                                            pb.setVisibility(View.INVISIBLE);
                                                                            Intent intent=new Intent(confirmFinalOrder.this,orderSuccess.class);
                                                                            startActivity(intent);
                                                                           // Toast.makeText(confirmFinalOrder.this, "Your order has been placed", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    }
                                                                });



                                        }
                                    }
                                });
//till here
                    }
                });


                //till here

                alertDialog.show();

            }

        }
        });


    }




}