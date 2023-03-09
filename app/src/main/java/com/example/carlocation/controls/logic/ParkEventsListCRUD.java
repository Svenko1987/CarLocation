package com.example.carlocation.controls.logic;

import android.content.Context;
import android.util.Log;

import com.example.carlocation.model.ParkEvent;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ParkEventsListCRUD {
    private static final String TAG = ParkEventsListCRUD.class.getSimpleName();
    private static final String FILE_NAME = "myList.json";
    private Gson gson;
    private Context context;

    public ParkEventsListCRUD(Context context) {
        this.context = context.getApplicationContext();
        gson = new Gson();
    }

    public boolean saveList(ArrayList<ParkEvent> myList) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            String json = gson.toJson(myList);
            fos.write(json.getBytes());
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error saving list to internal storage", e);
            return false;
        }
    }

    public ArrayList<ParkEvent> loadList() {
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();
            Log.d(TAG, "loadList: LIST LOADED");
            return gson.fromJson(json, new ArrayList<ParkEvent>().getClass());
        } catch (IOException e) {
            //Log.e(TAG, "Error loading list from internal storage", e);
            return new ArrayList<>();
        }
    }

    public boolean deleteList() {
        return context.deleteFile(FILE_NAME);
    }

    public boolean updateList(ArrayList<ParkEvent> myList) {
        Log.d(TAG, "updateList: List Updated");
        return saveList(myList);
    }
}

