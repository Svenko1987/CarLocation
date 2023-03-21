package com.example.carlocation.view;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.carlocation.MainActivity;
import com.example.carlocation.R;
import com.example.carlocation.controls.logic.ListCRUD;
import com.example.carlocation.model.ParkEventsList;

public class SavedActivity extends AppCompatActivity {
    private ParkEventsList parkEventsList;
    private RecyclerView recyclerView;
    private TextView savedTV;
    private Button history;
    private boolean mode = true;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        ListCRUD crud = new ListCRUD(SavedActivity.this, "mySavedList.json");
        parkEventsList = new ParkEventsList(crud.loadList());


        recyclerView = findViewById(R.id.recyclerSavedRV);
        Button backToMain = findViewById(R.id.backToMainFromSavedBtn);
        history = findViewById(R.id.historyListBtn);
        savedTV = findViewById(R.id.savedLocationsL);
        if (mode) {
            setAdapterSaved();
        } else {
            setAdapterHistory();
        }


        history.setOnClickListener(view -> {
            if (mode) {
                setAdapterHistory();
                mode = false;
            } else {
                setAdapterSaved();
                mode = true;
            }
            history.setText(mode ? "History" : "Saved");
            savedTV.setText(mode ? "Saved locations" : "All locations");

        });
        backToMain.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });


    }

    private void setAdapterSaved() {
        recyclerParkEventAdapter adapter = new recyclerParkEventAdapter(parkEventsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setAdapterHistory() {
        recyclerParkEventAdapter adapter = new recyclerParkEventAdapter(parkEventsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}