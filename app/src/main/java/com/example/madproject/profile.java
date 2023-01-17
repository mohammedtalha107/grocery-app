package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class profile extends AppCompatActivity {
    TextView Fullname,Emailid,Address;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        Fullname = findViewById(R.id.Name);
        Emailid= findViewById(R.id.emailid);
        Address=findViewById(R.id.address2);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userid=fAuth.getCurrentUser().getUid();

        final DocumentReference documentReference=fStore.collection("users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Address.setText("Address : "+ documentSnapshot.getString("Address"));
                Fullname.setText("Name : " + documentSnapshot.getString("Fullname"));
                Emailid.setText("Email id : "+documentSnapshot.getString("EmailId"));
            }
        });




    }
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(),navdraw.class));
        finish();
    }
}