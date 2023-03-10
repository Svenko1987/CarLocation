package com.example.carlocation.controls.inteface;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carlocation.MainActivity;
import com.example.carlocation.R;
import com.example.carlocation.controls.logic.ParkEventsListCRUD;
import com.example.carlocation.model.ParkEvent;
import com.example.carlocation.model.ParkEventsList;

import java.util.ArrayList;


public class HistoryActivity extends AppCompatActivity {
    private ParkEventsList parkEventsList;
    private RecyclerView recyclerView;
    private Button saved;
    private Button backToMain;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ParkEventsListCRUD crud = new ParkEventsListCRUD(HistoryActivity.this);
        parkEventsList = new ParkEventsList(crud.loadList());

        ParkEvent testPark = parkEventsList.read(1);
        Log.d(TAG, "Povuceno iz liste: " + testPark.getDate());

        recyclerView=findViewById(R.id.recyclerHistoryRV);
        saved=findViewById(R.id.savedListBtn);
        backToMain=findViewById(R.id.backToMainBtn);

        setAdapter();

        backToMain.setOnClickListener(view -> {
            Intent intent= new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
    private void setAdapter(){
        recyclerAdapter adapter=new recyclerAdapter(parkEventsList);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
