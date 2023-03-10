package com.example.carlocation.controls.inteface;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carlocation.R;
import com.example.carlocation.model.ParkEvent;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private ArrayList<ParkEvent> parkEvents;

    public recyclerAdapter(ArrayList<ParkEvent> parkEvents) {
        this.parkEvents = parkEvents;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nameText;

        public MyViewHolder(final View view) {
            super(view);
            this.nameText = view.findViewById(R.id.textView2);
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
