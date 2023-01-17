package com.example.madproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class orderAdapter extends FirebaseRecyclerAdapter<ordersPost, orderAdapter.orderViewHolder> {

    private Context context;
    private Button plc,cnl;
    private ImageView closeicon;
    private TextView tv1,tv2;
    private String currentstatus;

    public orderAdapter(@NonNull FirebaseRecyclerOptions<ordersPost> options) {
        super(options);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onBindViewHolder(@NonNull orderViewHolder holder, final int i, @NonNull ordersPost post) {

        FirebaseAuth fAuth;
        final String userid;
        final String[] key = new String[1];
        key[0] =getRef(i).getKey();

        fAuth = FirebaseAuth.getInstance();
        userid = fAuth.getCurrentUser().getUid();

        holder.dateoforder.setText("On "+post.getDate_of_adding());
        holder.items.setText("Orders :"+post.getItems());
        holder.total.setText("Grand total : Rs."+post.getGrandtotal());
        holder.status.setText("Status :"+post.getStatus());

        currentstatus=post.getStatus();
        if(!currentstatus.equals("Not shipped")){
            holder.edit.setVisibility(View.INVISIBLE);

        }

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("orderedit", "clicked");
                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                View mview = LayoutInflater.from(context).inflate(R.layout.confirmorderdialog, null);



                plc = mview.findViewById(R.id.dialogplace);
                cnl = mview.findViewById(R.id.dialogcancel);
                closeicon = mview.findViewById(R.id.dialogclose);
                tv1 = mview.findViewById(R.id.dialogtext);
                tv2 = mview.findViewById(R.id.modeofpay);


                plc.setText("yes");
                cnl.setText("no");
                tv1.setText("Do u want to cancel this order?");
                tv2.setVisibility(View.INVISIBLE);

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

                        FirebaseDatabase.getInstance().getReference()
                        .child("order list")
                        .child(userid)
                        .child(key[0])
                        .setValue(null)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                Log.d("ivalue", "i value is"+i);
                                Toast.makeText(context, "Order cancelled", Toast.LENGTH_SHORT).show();

                            }

                        });

            }
        });

                alertDialog.show();

            }
        });

    }


    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context=parent.getContext();

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.singleorderitem, parent, false);


        return new orderViewHolder(view);

    }

    class orderViewHolder extends RecyclerView.ViewHolder{

        TextView dateoforder,items,total,status;
        ImageView edit;

        public orderViewHolder(@NonNull View itemView) {
            super(itemView);

            dateoforder=itemView.findViewById(R.id.orderdate);
            items=itemView.findViewById(R.id.itemlist);
            total=itemView.findViewById(R.id.cartprice);
            status=itemView.findViewById(R.id.shipstatus);
            edit = itemView.findViewById(R.id.editorder);


        }
    }
}
