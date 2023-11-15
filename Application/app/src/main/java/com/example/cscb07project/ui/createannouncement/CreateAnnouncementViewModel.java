package com.example.cscb07project.ui.createannouncement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateAnnouncementViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public CreateAnnouncementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is create announcement fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
