package com.example.cscb07project.ui.requirements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cscb07project.R;

public class Q2Stat extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questions2_stat, container, false);

        Button buttonMaj = view.findViewById(R.id.button_maj_stat);

        buttonMaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_nav_questions2_stat_to_nav_questions3a_stat);
            }
        });

        Button buttonSpec = view.findViewById(R.id.button_spec_stat);

        buttonSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_nav_questions2_stat_to_nav_questions3b_stat);
            }
        });

        return view;
    }
}
