package com.example.madproject.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("Do u wanna logout?");
    }

    public LiveData<String> getText() {
        return mText;
    }
}