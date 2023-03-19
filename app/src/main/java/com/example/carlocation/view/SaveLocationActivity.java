package com.example.carlocation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.carlocation.R;

public class SaveLocationActivity extends AppCompatActivity {

    private Button relocate;
    private Button save;
    private Button camera;
    private Button cancel;

    private TextView currentLocation;
    private TextView note;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_location);

        relocate= findViewById(R.id.resetLocationBtn2);
        save= findViewById(R.id.saveLocBtn);
        cancel= findViewById(R.id.cancelB);
        currentLocation=findViewById(R.id.locationET4);
        note=findViewById(R.id.noteT2);









    }
}