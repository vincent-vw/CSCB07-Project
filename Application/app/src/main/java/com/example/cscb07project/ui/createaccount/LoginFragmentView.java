package com.example.cscb07project.ui.createaccount;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentLoginBinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

public class LoginFragmentView extends Fragment {
    private LoginFragmentPresenter presenter;
    private FragmentLoginBinding binding;
    private EditText userText;
    private EditText passText;

    // create login fragment
    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        presenter = new LoginFragmentPresenter(this, new LoginFragmentModel());
        View root = binding.getRoot();
        userText = root.findViewById(R.id.login_username_edit_text);
        passText = root.findViewById(R.id.login_password_edit_text);
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // login_student_check & login_admin_check
        CheckBox studentCheck = view.findViewById(R.id.login_student_check);
        CheckBox adminCheck = view.findViewById(R.id.login_admin_check);
        studentCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    adminCheck.setChecked(false);
                    adminCheck.setEnabled(false);
                }
                else {
                    adminCheck.setEnabled(true);
                }
            }
        });
        adminCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    studentCheck.setChecked(false);
                    studentCheck.setEnabled(false);
                }
                else {
                    studentCheck.setEnabled(true);
                }
            }
        });

        // login_button
        view.findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get user input
                String username = userText.getText().toString();
                String password = passText.getText().toString();
                userText.setText("");
                passText.setText("");

                if (studentCheck.isChecked()) {
                    presenter.checkStudentsDB(username, password);
                }
                else if (adminCheck.isChecked()) {
                    presenter.checkAdminsDB(username, password);
                }
                else {
                    Toast announcement = Toast.makeText(getActivity(),
                            "Please select students or admin!", Toast.LENGTH_SHORT);
                    announcement.show();
                }
            }
        });

        // no_account_button
        view.findViewById(R.id.no_account_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = LoginFragmentViewDirections.actionNavLoginToNavSignUp();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }

    public FragmentLoginBinding getBinding() {
        return binding;
    }
}
