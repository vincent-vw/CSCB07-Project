package com.example.cscb07project.ui.requirements;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cscb07project.R;

public class Q2Comp extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questions2_comp, container, false);

        Button buttonMin = view.findViewById(R.id.button_min);

        buttonMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_nav_questions2_comp_to_nav_questions3a_comp);
            }
        });

        Button buttonMaj = view.findViewById(R.id.button_maj_spec);

        buttonMaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_nav_questions2_comp_to_nav_questions3b_comp);
            }
        });

        return view;
    }
}
