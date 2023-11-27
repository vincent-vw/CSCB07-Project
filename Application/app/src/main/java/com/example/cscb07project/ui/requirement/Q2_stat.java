package com.example.cscb07project.ui.requirement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cscb07project.R;

public class Q2_stat extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions2_stat);

        //change button id
        Button buttonMin = findViewById(R.id.button_min_stat);

        buttonMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Q3A_stat.class);
                startActivity(intent);
            }
        });

        //change button id
        Button buttonMaj = findViewById(R.id.button_maj_stat);

        buttonMaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Q3B_stat.class);
                startActivity(intent);
            }
        });

        //change button id
        Button buttonSpec = findViewById(R.id.button_spec_stat);

        buttonSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Q3C_stat.class);
                startActivity(intent);
            }
        });
    }
}
