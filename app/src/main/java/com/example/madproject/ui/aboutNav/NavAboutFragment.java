package com.example.madproject.ui.aboutNav;

import androidx.arch.core.executor.TaskExecutor;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madproject.R;

public class NavAboutFragment extends Fragment {

    private navaboutViewModel mViewModel;
    private TextView disp;
    public static NavAboutFragment newInstance() {
        return new NavAboutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.navabout_fragment, container, false);

        disp=root.findViewById(R.id.love);
        disp.setText("Made with <3 by AV");
        return root;
    }

}