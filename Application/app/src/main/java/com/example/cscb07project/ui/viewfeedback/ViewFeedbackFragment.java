package com.example.cscb07project.ui.viewfeedback;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private ListView feedbackList;

    private ListView eventRatingsList;
    private Button loadFeedbackButton;
    private Button markAsViewedButton;
    private TextView feedbackPrompt;
    private TextView feedbackText;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //initialize
        viewFeedbackViewModel = new ViewModelProvider(this).get(ViewFeedbackViewModel.class);
        binding = FragmentViewFeedbackBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        feedbackList = root.findViewById(R.id.view_feedback_list);
        eventRatingsList = root.findViewById(R.id.event_ratings_list);
        loadFeedbackButton = root.findViewById(R.id.view_feedback_load_button);
        markAsViewedButton = root.findViewById(R.id.view_feedback_view_button);
        feedbackPrompt = root.findViewById(R.id.view_feedback_prompt);
        feedbackText = root.findViewById(R.id.view_feedback_text);

        //feedback
        List<String> feedbackPreviewList = new ArrayList<>();
        ArrayAdapter feedbackArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, feedbackPreviewList);
        feedbackList.setAdapter(feedbackArrayAdapter);

        List<String> eventRatingsPreviewList = new ArrayList<>();
        ArrayAdapter eventRatingsArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, eventRatingsPreviewList);
        eventRatingsList.setAdapter(eventRatingsArrayAdapter);

        feedbackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                initializeViewSingleFeedbackPage(viewFeedbackViewModel.getFeedbackManager().getFeedback(Integer.parseInt(feedbackPreviewList.get(position).split(":")[0]) - 1));
            }
        });

        //button

        loadFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventRatingsPreviewList.clear();
                eventRatingsPreviewList.addAll(viewFeedbackViewModel.getFeedbackManager().getAllEventsSummaryAsList());
                eventRatingsArrayAdapter.notifyDataSetChanged();

                feedbackPreviewList.clear();
                List<Feedback> complaintList = viewFeedbackViewModel.getFeedbackManager().getAllFeedbackSortedBySubmitTime();
                int feedbackCount = 1;
                for (Feedback feedback : complaintList) {
                    feedbackPreviewList.add((feedbackCount++) + ":" + feedback.previewFeedbackAsString());
                }
                viewFeedbackViewModel.getFeedbackManager().refreshFeedback();
                feedbackArrayAdapter.notifyDataSetChanged();
            }
        });

        markAsViewedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO mark as viewed update, update database
                initializeLoadFeedbackListPage();
            }
        });

        initializeLoadFeedbackListPage();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initializeLoadFeedbackListPage() {
        //feedbackList.getLayoutParams().height = 2 * getScreenHeight() / 3;
        //feedbackList.getLayoutParams().width = 2 * getScreenWidth() / 3;
        feedbackList.requestLayout();
        feedbackList.setVisibility(View.VISIBLE);

        //eventRatingsList.getLayoutParams().height = 2 * getScreenHeight() / 3;
        //eventRatingsList.getLayoutParams().width = 2 * getScreenWidth() / 3;
        eventRatingsList.requestLayout();
        eventRatingsList.setVisibility(View.VISIBLE);

        loadFeedbackButton.setVisibility(View.VISIBLE);

        //prompt.setVisibility(View.VISIBLE);
        feedbackText.requestLayout();
        feedbackText.setVisibility(View.INVISIBLE);
        markAsViewedButton.setVisibility(View.INVISIBLE);
    }

    public void initializeViewSingleFeedbackPage(Feedback feedback) {
        //feedbackList.getLayoutParams().height = 0;
        //feedbackList.getLayoutParams().width = 0;
        feedbackList.requestLayout();
        feedbackList.setVisibility(View.INVISIBLE);

        //eventRatingsList.getLayoutParams().height = 0;
        //eventRatingsList.getLayoutParams().width = 0;
        eventRatingsList.requestLayout();
        eventRatingsList.setVisibility(View.INVISIBLE);

        loadFeedbackButton.setVisibility(View.INVISIBLE);

        //prompt.setVisibility(View.INVISIBLE);
        feedbackText.setText(feedback.viewFeedbackAsString());
        feedbackText.requestLayout();
        feedbackText.setVisibility(View.VISIBLE);
        markAsViewedButton.setVisibility(View.VISIBLE);
    }

    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
