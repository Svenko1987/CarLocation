package com.example.carlocation.inteface;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.carlocation.MainActivity;
import com.example.carlocation.R;
import com.example.carlocation.controls.logic.ListCRUD;
import com.example.carlocation.model.ParkEvent;
import com.example.carlocation.model.ParkEventsList;

public class SavedActivity extends AppCompatActivity {
    private ParkEventsList parkEventsList;
    private RecyclerView recyclerView;
    private Button backToMain;
    private Button history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        ListCRUD crud = new ListCRUD(SavedActivity.this,"myList.json");
        parkEventsList = new ParkEventsList(crud.loadList());

        ParkEvent testPark = parkEventsList.read(1);
        Log.d(TAG, "Povuceno iz liste: " + testPark.getDate());


        recyclerView=findViewById(R.id.recyclerSavedRV);
        backToMain=findViewById(R.id.backToMainFromSavedBtn);
        history=findViewById(R.id.historyListBtn);
        setAdapter();

        history.setOnClickListener(view -> {
            Intent intent=new Intent(this,HistoryActivity.class);
            startActivity(intent);
        });
        backToMain.setOnClickListener(view -> {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        });


    }
    private void setAdapter(){
        recyclerParkEventAdapter adapter=new recyclerParkEventAdapter(parkEventsList);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}