package com.example.cscb07project.ui.account;

import com.example.cscb07project.R;
import com.example.cscb07project.ui.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class LoginFragmentView extends Fragment {
    private LoginFragmentPresenter presenter;
    private EditText userText;
    private EditText passText;
    private RadioGroup radioGroup;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        presenter = new LoginFragmentPresenter(this, new LoginFragmentModel());

        userText = (EditText) view.findViewById(R.id.login_username_edit_text);
        passText = (EditText) view.findViewById(R.id.login_password_edit_text);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup_login);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userText.getText().toString().trim();
                String password = passText.getText().toString().trim();
                userText.setText("");
                passText.setText("");
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                presenter.signIn(username, password, checkedRadioButtonId);
            }
        });

        view.findViewById(R.id.no_account_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_login_to_nav_sign_up);
            }
        });
    }

    public void outputToast(String text) {
        Toast announcement = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        announcement.show();
    }

    public void signInSuccessful(User user) {
        TextView textView = (TextView) getActivity().findViewById(R.id.text_username);
        textView.setText(user.getUsername());
    }
}
