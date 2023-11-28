package com.example.cscb07project.ui.viewcomplaints;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Map;

public class ViewComplaintsFragment extends Fragment {
    private ViewComplaintsViewModel viewComplaintsViewModel;
    private FragmentViewComplaintsBinding binding;
    private View root;
    private ListView complaints_list;
    private Button load_conplaints_button;
    private Button mark_as_viewed_button;
    private TextView prompt;
    private TextView complaint_text;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //initialize
        viewComplaintsViewModel = new ViewModelProvider(this).get(ViewComplaintsViewModel.class);
        binding = FragmentViewComplaintsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        complaints_list = root.findViewById(R.id.view_complaint_list);
        load_conplaints_button = root.findViewById(R.id.view_complaint_load_button);
        mark_as_viewed_button = root.findViewById(R.id.view_complaint_view_button);
        prompt = root.findViewById(R.id.view_complaint_prompt);
        complaint_text = root.findViewById(R.id.view_complaint_text);

        //complaints
        List<String> complaintsPreviewList = new ArrayList<>();
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, complaintsPreviewList);
        complaints_list.setAdapter(arrayAdapter);

        complaints_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("1", complaintsPreviewList.get(position));
                initializeViewSingleComplaintPage(viewComplaintsViewModel.getComplaintManager().getComplaint(Integer.parseInt(complaintsPreviewList.get(position).split(":")[0])));
            }
        });

        //button

        load_conplaints_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaintsPreviewList.clear();
                //TODO no direct way of dealing with async calls :(
                // viewComplaintsViewModel.getComplaintManager().refreshComplaints();
                Map<Integer, Complaint> complaintsMap = viewComplaintsViewModel.getComplaintManager().getAllComplaints();
                for (int key : complaintsMap.keySet()) {
                    Complaint complaint = complaintsMap.get(key);
                    //TODO good preview string?
                    complaintsPreviewList.add(key + ":" + complaint.getUsername());
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });

        mark_as_viewed_button.setOnClickListener(new View.OnClickListener() {
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
        complaints_list.getLayoutParams().height = 2 * getScreenHeight() / 3;
        complaints_list.getLayoutParams().width = 2 * getScreenWidth() / 3;
        complaints_list.requestLayout();
        complaints_list.setVisibility(View.VISIBLE);
        load_conplaints_button.setVisibility(View.VISIBLE);
        //prompt.setVisibility(View.VISIBLE);

        complaint_text.requestLayout();
        complaint_text.setVisibility(View.INVISIBLE);
        mark_as_viewed_button.setVisibility(View.INVISIBLE);
    }

    public void initializeViewSingleComplaintPage(Complaint complaint) {
        complaints_list.getLayoutParams().height = 0;
        complaints_list.getLayoutParams().width = 0;
        complaints_list.requestLayout();
        complaints_list.setVisibility(View.INVISIBLE);
        load_conplaints_button.setVisibility(View.INVISIBLE);
        //prompt.setVisibility(View.INVISIBLE);

        //TODO toString() as text
        complaint_text.setText(complaint.toString());
        complaint_text.requestLayout();
        complaint_text.setVisibility(View.VISIBLE);
        mark_as_viewed_button.setVisibility(View.VISIBLE);
    }

    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
