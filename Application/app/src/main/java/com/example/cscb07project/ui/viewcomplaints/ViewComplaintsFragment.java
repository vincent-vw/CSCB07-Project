package com.example.cscb07project.ui.viewcomplaints;

import android.content.res.Resources;
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

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentViewComplaintsBinding;
import com.example.cscb07project.ui.Complaint;

import java.util.ArrayList;
import java.util.List;

public class ViewComplaintsFragment extends Fragment {
    private ViewComplaintsViewModel viewComplaintsViewModel;
    private FragmentViewComplaintsBinding binding;
    private View root;
    private ListView complaintsList;
    private Button loadConplaintsButton;
    private Button markAsViewedButton;
    private TextView prompt;
    private TextView complaintText;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //initialize
        viewComplaintsViewModel = new ViewModelProvider(this).get(ViewComplaintsViewModel.class);
        binding = FragmentViewComplaintsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        complaintsList = root.findViewById(R.id.view_complaint_list);
        loadConplaintsButton = root.findViewById(R.id.view_complaint_load_button);
        markAsViewedButton = root.findViewById(R.id.view_complaint_view_button);
        prompt = root.findViewById(R.id.view_complaint_prompt);
        complaintText = root.findViewById(R.id.view_complaint_text);

        //complaints
        List<String> complaintsPreviewList = new ArrayList<>();
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, complaintsPreviewList);
        complaintsList.setAdapter(arrayAdapter);

        complaintsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                initializeViewSingleComplaintPage(viewComplaintsViewModel.getComplaintManager().getComplaint(Integer.parseInt(complaintsPreviewList.get(position).split(":")[0]) - 1));
            }
        });

        //button

        loadConplaintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaintsPreviewList.clear();
                //TODO no direct way of dealing with async calls :(
                List<Complaint> complaintList = viewComplaintsViewModel.getComplaintManager().getAllComplaintsSortedBySubmitTime();
                int complaintCount = 1;
                for (Complaint complaint : complaintList) {
                    complaintsPreviewList.add((complaintCount++) + ":" + complaint.previewComplaintAsString());
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });

        markAsViewedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO mark as viewed update, update database
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
        complaintsList.getLayoutParams().height = 2 * getScreenHeight() / 3;
        complaintsList.getLayoutParams().width = 2 * getScreenWidth() / 3;
        complaintsList.requestLayout();
        complaintsList.setVisibility(View.VISIBLE);
        loadConplaintsButton.setVisibility(View.VISIBLE);
        //prompt.setVisibility(View.VISIBLE);

        complaintText.requestLayout();
        complaintText.setVisibility(View.INVISIBLE);
        markAsViewedButton.setVisibility(View.INVISIBLE);
    }

    public void initializeViewSingleComplaintPage(Complaint complaint) {
        complaintsList.getLayoutParams().height = 0;
        complaintsList.getLayoutParams().width = 0;
        complaintsList.requestLayout();
        complaintsList.setVisibility(View.INVISIBLE);
        loadConplaintsButton.setVisibility(View.INVISIBLE);
        //prompt.setVisibility(View.INVISIBLE);

        //TODO toString() as text
        complaintText.setText(complaint.viewComplaintAsString());
        complaintText.requestLayout();
        complaintText.setVisibility(View.VISIBLE);
        markAsViewedButton.setVisibility(View.VISIBLE);
    }

    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
