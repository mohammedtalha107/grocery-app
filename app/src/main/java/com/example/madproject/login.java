package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    public static final String EXTRA_TEXT="com.example.MADproject.example.EXTRA_TEXT";

    EditText user,password;
    Button login;
    TextView reg;
    ProgressBar pb;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);




        DisplayMetrics disp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(disp);
        int width = disp.widthPixels;
        int height = disp.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);


        user=findViewById(R.id.username);
        password=findViewById(R.id.password);
        pb=findViewById(R.id.progress2);
        login=findViewById(R.id.login);
        reg=findViewById(R.id.register);
        fAuth=FirebaseAuth.getInstance();
        final Context context= this;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=user.getText().toString().trim();
                String pass=password.getText().toString().trim();

                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.RESULT_UNCHANGED_SHOWN);

                if(TextUtils.isEmpty(email)) {
                    user.setError("Email/username is left empty");
                    return;
                }

                if(TextUtils.isEmpty(pass)) {
                    password.setError("password is left empty");
                    return;
                }


                pb.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {

                            if(fAuth.getCurrentUser().isEmailVerified())
                            {

                            startActivity(new Intent(getApplicationContext(),navdraw.class));
                            finish();
                            //pb.setVisibility(View.INVISIBLE);
                            Toast.makeText(login.this, "login successfully", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"Verify your email address to login",Snackbar.LENGTH_LONG);
                                snackbar.show();
                                //Toast.makeText(login.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                                pb.setVisibility(View.INVISIBLE);
                            }
                        }
                        else {

                            Toast.makeText(login.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.INVISIBLE);

                        }

                    }
                });

            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG1","clicked register button");
                Intent intent=new Intent(login.this,Register.class);
                startActivity(intent);
                finish();

            }
        });



    }






}