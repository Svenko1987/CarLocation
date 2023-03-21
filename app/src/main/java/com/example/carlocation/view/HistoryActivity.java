package com.example.carlocation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carlocation.MainActivity;
import com.example.carlocation.R;
import com.example.carlocation.controls.logic.ListCRUD;
import com.example.carlocation.model.ParkEvent;
import com.example.carlocation.model.ParkEventsList;


public class HistoryActivity extends AppCompatActivity {
    private ParkEventsList parkEventsList;
    private RecyclerView recyclerView;
    private Button saved;
    private Button backToMain;
    private boolean mode=true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ListCRUD crud = new ListCRUD(HistoryActivity.this,"myList.json");
        ListCRUD crudSaved = new ListCRUD(HistoryActivity.this,"mySavedList.json");
        parkEventsList = new ParkEventsList(crud.loadList());

        ParkEvent testPark = parkEventsList.read(1);
        //Log.d(TAG, "Povuceno iz liste: " + testPark.getDate());

        recyclerView=findViewById(R.id.recyclerSavedRV);
        saved=findViewById(R.id.savedListBtn);
        backToMain=findViewById(R.id.backToMainBtn);

        setAdapterHistory();
        saved.setOnClickListener(view -> {
            Intent intent= new Intent(this, SavedActivity.class);
            startActivity(intent);
        });
        backToMain.setOnClickListener(view -> {
            Intent intent= new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
    private void setAdapterHistory(){
        recyclerParkEventAdapter adapter=new recyclerParkEventAdapter(parkEventsList);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void setAdapterSaved(){
        recyclerParkEventAdapter adapter=new recyclerParkEventAdapter(parkEventsList);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
}
