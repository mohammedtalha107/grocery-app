package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";

    EditText username,fullname,password,add;
    Button register;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    ProgressBar progressbar;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        username=findViewById(R.id.username2);
        password=findViewById(R.id.password2);
        fullname=findViewById(R.id.name);
        add=findViewById(R.id.address);
        register=findViewById(R.id.reg);
        fstore=FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();
        progressbar=findViewById(R.id.progressbar);

        if(fAuth.getCurrentUser()!=null) {
            startActivity(new Intent(getApplicationContext(),login.class));
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email=username.getText().toString().trim();
                String pass=password.getText().toString().trim();
               final String fname=fullname.getText().toString();
                final String addr=add.getText().toString().trim();



                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.RESULT_UNCHANGED_SHOWN);

                if(TextUtils.isEmpty(fname)) {
                    fullname.setError("Name is left empty");
                    return;
                }

                if(TextUtils.isEmpty(email)) {
                username.setError("Email is left empty");
                return;
                }

                if(TextUtils.isEmpty(pass)) {
                password.setError("password is left empty");
                return;
                }

                if(TextUtils.isEmpty(addr)) {
                    add.setError("Address is left empty");
                    return;
                }

                if(password.length()<6)
                {
                    password.setError("Pass has to more than 6 characters");
                }



                progressbar.setVisibility(View.VISIBLE);



                fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            FirebaseUser user1=fAuth.getCurrentUser();
                            user1.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this, "Verification email sent", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this, "failed to send verification email", Toast.LENGTH_SHORT).show();

                                }
                            });


                           // Toast.makeText(Register.this, "User created successfully", Toast.LENGTH_SHORT).show();
                            userID=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fstore.collection("users").document(userID);
                            Map<String,Object> user=new HashMap<>();
                            user.put("Fullname",fname);
                            user.put("EmailId",email);
                            user.put("Address",addr);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Log.d(TAG,"onSuccess :account created for "+userID);
                                }
                            });


                            startActivity(new Intent(getApplicationContext(),login.class));
                            finish();

                        }
                        else {

                            Toast.makeText(Register.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });



    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),login.class));
        finish();

    }
}
