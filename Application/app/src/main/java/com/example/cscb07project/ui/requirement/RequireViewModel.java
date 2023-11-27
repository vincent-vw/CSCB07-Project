package com.example.cscb07project.ui.requirement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RequireViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RequireViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is POSt Requirement fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}