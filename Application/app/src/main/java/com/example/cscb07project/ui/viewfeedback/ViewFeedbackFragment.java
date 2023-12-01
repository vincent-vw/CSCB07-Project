package com.example.cscb07project.ui.viewfeedback;

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
import com.example.cscb07project.databinding.FragmentViewFeedbackBinding;
import com.example.cscb07project.ui.Feedback;

import java.util.ArrayList;
import java.util.List;

public class ViewFeedbackFragment extends Fragment {
    private ViewFeedbackViewModel viewFeedbackViewModel;
    private FragmentViewFeedbackBinding binding;
    private View root;
    private ListView feedback_list;
    private Button load_feedback_button;
    private Button mark_as_viewed_button;
    private TextView feedback_prompt;
    private TextView feedback_text;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //initialize
        viewFeedbackViewModel = new ViewModelProvider(this).get(ViewFeedbackViewModel.class);
        binding = FragmentViewFeedbackBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        feedback_list = root.findViewById(R.id.view_feedback_list);
        load_feedback_button = root.findViewById(R.id.view_feedback_load_button);
        mark_as_viewed_button = root.findViewById(R.id.view_feedback_view_button);
        feedback_prompt = root.findViewById(R.id.view_feedback_prompt);
        feedback_text = root.findViewById(R.id.view_feedback_text);

        //complaints
        List<String> feedbackPreviewList = new ArrayList<>();
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, feedbackPreviewList);
        feedback_list.setAdapter(arrayAdapter);

        feedback_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("1", feedbackPreviewList.get(position));
                initializeViewSingleFeedbackPage(viewFeedbackViewModel.getFeedbackManager().getFeedback(Integer.parseInt(feedbackPreviewList.get(position).split(":")[0]) - 1));
            }
        });

        //button

        load_feedback_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedbackPreviewList.clear();
                //TODO no direct way of dealing with async calls :(
                List<Feedback> complaintList = viewFeedbackViewModel.getFeedbackManager().getAllFeedbackSortedBySubmitTime();
                int feedbackCount = 1;
                for (Feedback feedback : complaintList) {
                    //TODO good preview string?
                    feedbackPreviewList.add(feedbackCount++ + ":" + feedback.getUsername()+"add dates, is viewed by admin etc.");
                }
                viewFeedbackViewModel.getFeedbackManager().refreshFeedback();
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
        feedback_list.getLayoutParams().height = 2 * getScreenHeight() / 3;
        feedback_list.getLayoutParams().width = 2 * getScreenWidth() / 3;
        feedback_list.requestLayout();
        feedback_list.setVisibility(View.VISIBLE);
        load_feedback_button.setVisibility(View.VISIBLE);
        //prompt.setVisibility(View.VISIBLE);

        feedback_text.requestLayout();
        feedback_text.setVisibility(View.INVISIBLE);
        mark_as_viewed_button.setVisibility(View.INVISIBLE);
    }

    public void initializeViewSingleFeedbackPage(Feedback feedback) {
        feedback_list.getLayoutParams().height = 0;
        feedback_list.getLayoutParams().width = 0;
        feedback_list.requestLayout();
        feedback_list.setVisibility(View.INVISIBLE);
        load_feedback_button.setVisibility(View.INVISIBLE);
        //prompt.setVisibility(View.INVISIBLE);

        //TODO toString() as text
        feedback_text.setText(feedback.toString());
        feedback_text.requestLayout();
        feedback_text.setVisibility(View.VISIBLE);
        mark_as_viewed_button.setVisibility(View.VISIBLE);
    }

    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
