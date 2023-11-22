package com.example.cscb07project.ui.complaintForm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ComplaintFormViewModel extends ViewModel{
    private final MutableLiveData<String> mText;

    public ComplaintFormViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is complaint form fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
