package com.example.cscb07project.ui.requirements;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentRequireBinding;

public class RequireFragment extends Fragment{
    private FragmentRequireBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRequireBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Find the views
        Button buttoncomp = root.findViewById(R.id.button_comp);
        Button buttonmath = root.findViewById(R.id.button_math);
        Button buttonstat = root.findViewById(R.id.button_stat);

        buttoncomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_nav_require_to_nav_questions2_comp);
            }
        });
        buttonmath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_nav_require_to_nav_questions2_math);
            }
        });
        buttonstat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_nav_require_to_nav_questions2_stat);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}