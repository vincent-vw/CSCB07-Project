package com.example.cscb07project.ui.requirement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cscb07project.R;

public class Q2_math extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions2_math);

        //change button id
        Button buttonMaj = findViewById(R.id.button_maj);

        buttonMaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Q3A_math.class);
                startActivity(intent);
            }
        });

        //change button id
        Button buttonSpec = findViewById(R.id.button_spec);

        buttonSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Q3B_math.class);
                startActivity(intent);
            }
        });

    }

}