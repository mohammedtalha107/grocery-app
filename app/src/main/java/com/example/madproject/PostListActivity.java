package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.GONE;

public class PostListActivity extends AppCompatActivity implements PostAdapter.OnListItemClick {
    public static final String EXTRA_TEXT="com.example.MADproject.example.EXTRA_TEXT";                   //initialize the strings that as to be passed to new activity
    public static final String EXTRA_TEXT1="com.example.MADproject.example.EXTRA_TEXT1";
    public static final String EXTRA_TEXT2="com.example.MADproject.example.EXTRA_TEXT2";
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private ProgressBar pb;
    private FloatingActionButton cartfab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shop");

        pb=findViewById(R.id.shoppb);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final DatabaseReference shopListRef= FirebaseDatabase.getInstance().getReference().child("Post");
        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Post"), Post.class)
                        .build();

        adapter = new PostAdapter(options,this);
        recyclerView.setAdapter(adapter);
//starting
        shopListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot5) {

                pb.setVisibility(GONE);
                cartfab.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PostListActivity.this, "Error in DB connectivity", Toast.LENGTH_SHORT).show();
            }
        });

//ending

            cartfab=findViewById(R.id.fab2);
        cartfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Viewcart.class));
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


    @Override
    public void onItemClick(Post snapshot, int position) {
        Log.d("tag2","Clicked the item no "+position+" and name is "+snapshot.getName());
        //add intent here
        Intent intent = new Intent(this, cart.class);            //go to new activity, passing str1 and str2 to new activity.
        intent.putExtra(EXTRA_TEXT, snapshot.getName());
        intent.putExtra(EXTRA_TEXT1, snapshot.getPrice());
        intent.putExtra(EXTRA_TEXT2, snapshot.getPurl());
        startActivity(intent);
    }

  public void onBackPressed()
    {
        Intent intent=new Intent(getApplicationContext(),navdraw.class);
        startActivity(intent);
        finish();
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item=menu.findItem(R.id.searchitem);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                searchItem(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                searchItem(s);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    private void searchItem(String s) {

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Post").orderByChild("search").startAt(s).endAt(s+"\uf8ff"), Post.class)
                        .build();

        adapter=new PostAdapter(options,this);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}