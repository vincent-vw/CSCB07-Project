package com.example.cscb07project.ui.requirement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cscb07project.R;

public class Q2_comp extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions2_comp);

        Button buttonMin = findViewById(R.id.button_min);

        buttonMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Q3A_comp.class);
                startActivity(intent);
            }
        });

        Button buttonMaj = findViewById(R.id.button_maj_spec);

        buttonMaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Q3B_comp.class);
                startActivity(intent);
            }
        });

    }
}
