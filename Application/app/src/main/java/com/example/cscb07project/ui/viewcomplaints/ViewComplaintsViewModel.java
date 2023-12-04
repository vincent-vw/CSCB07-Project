package com.example.cscb07project.ui.viewcomplaints;

import androidx.lifecycle.ViewModel;

import com.example.cscb07project.ui.complaint.ComplaintManager;

public class ViewComplaintsViewModel extends ViewModel {
    private ComplaintManager complaintManager;

    public ViewComplaintsViewModel() {
        complaintManager = ComplaintManager.getInstance();
    }

    public ComplaintManager getComplaintManager() {
        return complaintManager;
    }
}
