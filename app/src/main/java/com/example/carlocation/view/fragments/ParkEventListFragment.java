package com.example.carlocation.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carlocation.R;
import com.example.carlocation.model.ParkEvent;
import com.example.carlocation.view.SelectListener;

public class ParkEventListFragment extends Fragment implements SelectListener<ParkEvent> {
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_saved, container, false);
        recyclerView=view.findViewById(R.id.recyclerSavedRV);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onItemClicked(ParkEvent parkEvent) {
        Toast.makeText(getContext(), "SELECTED : Clicked", Toast.LENGTH_SHORT).show();


    }
}
