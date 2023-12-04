package com.example.cscb07project.ui.viewfeedback;

import androidx.lifecycle.ViewModel;

import com.example.cscb07project.ui.feedback.FeedbackManager;

public class ViewFeedbackViewModel extends ViewModel {
    private FeedbackManager feedbackManager;

    public ViewFeedbackViewModel() {
        feedbackManager = FeedbackManager.getInstance();
    }

    public FeedbackManager getFeedbackManager() {
        return feedbackManager;
    }
}
