package com.example.carlocation.controls.inteface;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carlocation.MainActivity;
import com.example.carlocation.R;
import com.example.carlocation.model.ParkEvent;

import java.util.ArrayList;


public class HistoryActivity extends AppCompatActivity {
    private ArrayList<ParkEvent> parkEvents;
    private RecyclerView recyclerView;
    private Button saved;
    private Button backToMain;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView=findViewById(R.id.recyclerHistoryRV);
        saved=findViewById(R.id.savedListBtn);
        backToMain=findViewById(R.id.backToMainBtn);

        backToMain.setOnClickListener(view -> {
            Intent intent= new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}
