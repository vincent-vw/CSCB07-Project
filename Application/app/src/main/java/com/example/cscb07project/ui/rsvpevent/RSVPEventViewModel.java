package com.example.cscb07project.ui.rsvpevent;

import androidx.lifecycle.ViewModel;

import com.example.cscb07project.ui.complaint.ComplaintManager;
import com.example.cscb07project.ui.event.EventManager;

public class RSVPEventViewModel extends ViewModel {
    private EventManager eventManager;

    public RSVPEventViewModel() {
        eventManager = EventManager.getInstance();
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
