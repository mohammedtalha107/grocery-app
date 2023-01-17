package com.example.madproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;


public class PostAdapter extends FirebaseRecyclerAdapter<Post, PostAdapter.PastViewHolder> {

    private OnListItemClick onListItemClick;
    public PostAdapter(@NonNull FirebaseRecyclerOptions<Post> options, PostListActivity onListItemClick) {
        super(options);
        this.onListItemClick= (OnListItemClick) onListItemClick;
    }


    @Override
    protected void onBindViewHolder(@NonNull PastViewHolder holder, int i, @NonNull Post post) {
        holder.name.setText(post.getName());
        holder.price.setText("Rs."+post.getPrice()+" /kg");
        //holder.img.setText(post.getPurl());
        String url=post.getPurl();
        Picasso.get().load(url).into(holder.img);
    }

    @NonNull
    @Override
    public PastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post, parent, false);
        return new PastViewHolder(view);
    }

    class PastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name,price;
        ImageView img;


        public PastViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.imgurl);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onListItemClick.onItemClick(getItem(getAdapterPosition()),getAdapterPosition());

        }
    }

    public interface OnListItemClick
    {
        void onItemClick(Post snapshot, int position);
    }

}
