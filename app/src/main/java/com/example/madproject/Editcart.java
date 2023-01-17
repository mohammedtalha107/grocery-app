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
import android.widget.EditText;
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


public class Editcart extends FirebaseRecyclerAdapter<cartfirebase, Editcart.PastViewHolder> {
    private Context context;
    private int grandtotalprice = 0;


    public Editcart(@NonNull FirebaseRecyclerOptions<cartfirebase> options) {
        super(options);
        //this.onListItemClick1=onListItemClick1;

    }

    @Override
    protected void onBindViewHolder(@NonNull PastViewHolder holder, final int i, @NonNull cartfirebase post) {
        FirebaseAuth fAuth;
        final String userid;
        final String[] key = new String[1];
        key[0] =getRef(i).getKey();

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


        holder.deleteBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseDatabase.getInstance().getReference()
                        .child("cart list")
                        .child("User cart view")
                        .child(userid)
                        .child("Products")
                        .child(key[0])
                        .setValue(null)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show();
                                Log.d("ivalue", "i value is"+i);
                            }

                        });


            }
        });
    }


    @NonNull
    @Override
    public Editcart.PastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.editcart, parent, false);

        context=parent.getContext();

        return new Editcart.PastViewHolder(view);



    }

    class PastViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, quantity;
        ImageView img, deleteBut;


        public PastViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemcartname);
            price = itemView.findViewById(R.id.cartprice);
            quantity = itemView.findViewById(R.id.cartquantity);
            img = itemView.findViewById(R.id.cartimgurl);
            deleteBut = itemView.findViewById(R.id.cartdelete);


        }
    }
}

