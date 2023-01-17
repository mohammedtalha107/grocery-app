package com.example.madproject.ui.cartNav;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.madproject.PostListActivity;
import com.example.madproject.R;
import com.example.madproject.Viewcart;

public class CartFragment extends Fragment {

    private cartViewModel mViewModel;
    private Button cart;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.cart_fragment, container, false);

        cart=root.findViewById(R.id.navcart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Viewcart.class);
                startActivity(intent);
            }
        });
        return root;
    }
}