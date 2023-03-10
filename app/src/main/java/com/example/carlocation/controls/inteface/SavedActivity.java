package com.example.carlocation.controls.inteface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.carlocation.MainActivity;
import com.example.carlocation.R;

public class SavedActivity extends AppCompatActivity {
    private Button backToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        backToMain=findViewById(R.id.backToMainFromSavedBtn);
        backToMain.setOnClickListener(view -> {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        });


    }
}