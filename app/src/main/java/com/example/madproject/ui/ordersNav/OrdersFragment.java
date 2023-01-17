package com.example.madproject.ui.ordersNav;

import androidx.lifecycle.Observer;
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
import com.example.madproject.orders;
import com.example.madproject.ui.home.HomeViewModel;

public class OrdersFragment extends Fragment {

    private ordersViewModel ordersViewModel;
    private Button orders;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ordersViewModel =
                ViewModelProviders.of(this).get(ordersViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_orders_activity, container, false);
        // final TextView textView = root.findViewById(R.id.text_home);
        orders=root.findViewById(R.id.navorder);
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), orders.class);
                startActivity(intent);
            }
        });
        return root;
    }


}