package com.example.cscb07project.ui.viewcomplaints;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentViewComplaintsBinding;
import com.example.cscb07project.ui.complaint.Complaint;

import java.util.ArrayList;
import java.util.List;

public class ViewComplaintsFragment extends Fragment {
    private ViewComplaintsViewModel viewComplaintsViewModel;
    private FragmentViewComplaintsBinding binding;
    private View root;
    private ListView complaintsList;
    private Button loadComplaintsButton;
    private Button markAsViewedButton;
    private TextView complaintText;
    private Complaint currentComplaint = null;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (MainActivity.user.getIdentity().equals("student")) {
            View view = inflater.inflate(R.layout.fragment_deny_access, container, false);
            return view;
        }

        //initialize
        viewComplaintsViewModel = new ViewModelProvider(this).get(ViewComplaintsViewModel.class);
        binding = FragmentViewComplaintsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        complaintsList = root.findViewById(R.id.view_complaint_list);
        loadComplaintsButton = root.findViewById(R.id.view_complaint_load_button);
        markAsViewedButton = root.findViewById(R.id.view_complaint_view_button);
        complaintText = root.findViewById(R.id.view_complaint_text);

        //complaints
        List<String> complaintsPreviewList = new ArrayList<>();
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, complaintsPreviewList);
        complaintsList.setAdapter(arrayAdapter);

        complaintsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentComplaint = viewComplaintsViewModel.getComplaintManager().getComplaint(Integer.parseInt(complaintsPreviewList.get(position).split(":")[0]) - 1);
                initializeViewSingleComplaintPage();
            }
        });

        //button

        loadComplaintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaintsPreviewList.clear();
                List<Complaint> complaintList = viewComplaintsViewModel.getComplaintManager().getAllComplaintsSortedBySubmitTime();
                int complaintCount = 1;
                for (Complaint complaint : complaintList) {
                    complaintsPreviewList.add((complaintCount++) + ": " + complaint.previewComplaintAsString());
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });

        markAsViewedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeLoadComplaintsListPage();
            }
        });

        initializeLoadComplaintsListPage();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initializeLoadComplaintsListPage() {
        complaintsList.requestLayout();
        complaintsList.setVisibility(View.VISIBLE);
        loadComplaintsButton.setVisibility(View.VISIBLE);

        complaintText.requestLayout();
        complaintText.setVisibility(View.INVISIBLE);
        markAsViewedButton.setVisibility(View.INVISIBLE);
    }

    public void initializeViewSingleComplaintPage() {
        complaintsList.requestLayout();
        complaintsList.setVisibility(View.INVISIBLE);
        loadComplaintsButton.setVisibility(View.INVISIBLE);

        complaintText.setText(currentComplaint.viewComplaintAsString());
        complaintText.requestLayout();
        complaintText.setVisibility(View.VISIBLE);
        markAsViewedButton.setVisibility(View.VISIBLE);
    }
}
