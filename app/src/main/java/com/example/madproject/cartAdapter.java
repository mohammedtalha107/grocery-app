package com.example.madproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.madproject.PostListActivity.EXTRA_TEXT;
import static java.security.AccessController.getContext;


public class cartAdapter extends FirebaseRecyclerAdapter<cartfirebase, cartAdapter.PastViewHolder> {
    private Context context;
    private int grandtotalprice=0;


    public cartAdapter(@NonNull FirebaseRecyclerOptions<cartfirebase> options) {
        super(options);
        //this.onListItemClick1=onListItemClick1;

    }


    @Override
    protected void onBindViewHolder(@NonNull PastViewHolder holder, final int i, @NonNull final cartfirebase post) {

        FirebaseAuth fAuth;
        final String userid;

        holder.name.setText(post.getItemName());
        String singleprice = post.getPrice();
        int singlepriceint = Integer.parseInt(singleprice);
        int quantityint = Integer.parseInt(post.getQuantity());
        int total = singlepriceint * quantityint;

        fAuth = FirebaseAuth.getInstance();
        userid = fAuth.getCurrentUser().getUid();

        holder.price.setText("Rs." + total + " (" + singleprice + "/ kg)");
        holder.quantity.setText(post.getQuantity() + " kg");
        String url = post.getimgUrl();
        Picasso.get().load(url).into(holder.img);

/*
        holder.deleteBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                 FirebaseDatabase.getInstance().getReference()
                        .child("cart list")
                        .child("User cart view")
                        .child(userid)
                        .child("Products")
                        .child(getRef(i).getKey())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Log.d("ivalue", "i value is now 0 ");
                            }

                        });
            }
        });*/

    }




    @NonNull
    @Override
    public PastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cartsingleitem, parent, false);
        return new PastViewHolder(view);




    }

    class PastViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, quantity;
        ImageView img,deleteBut;


        public PastViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemcartname);
            price = itemView.findViewById(R.id.cartprice);
            quantity = itemView.findViewById(R.id.cartquantity);
            img = itemView.findViewById(R.id.cartimgurl);
            deleteBut=itemView.findViewById(R.id.cartdelete);


        }

    }


}

