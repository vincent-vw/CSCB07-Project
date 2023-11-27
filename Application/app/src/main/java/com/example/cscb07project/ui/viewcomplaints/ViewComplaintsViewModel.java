package com.example.cscb07project.ui.viewcomplaints;

import androidx.lifecycle.ViewModel;

public class ViewComplaintsViewModel extends ViewModel {
    private ComplaintManager complaintManager;

    public ViewComplaintsViewModel() {
        complaintManager = ComplaintManager.getInstance();
    }

    public ComplaintManager getComplaintManager() {
        return complaintManager;
    }
}
