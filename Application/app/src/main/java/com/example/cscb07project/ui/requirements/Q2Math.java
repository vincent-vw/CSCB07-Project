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

public class Q2Math extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questions2_math, container, false);

        Button buttonMaj = view.findViewById(R.id.button_maj);

        buttonMaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_nav_questions2_math_to_nav_questions3a_math);
            }
        });

        Button buttonSpec = view.findViewById(R.id.button_spec);

        buttonSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_nav_questions2_math_to_nav_questions3b_math);
            }
        });

        return view;
    }

}