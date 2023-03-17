package com.example.carlocation.view;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import static com.example.carlocation.MainActivity.MyPREFERENCES;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlocation.R;
import com.example.carlocation.controls.logic.ListCRUD;
import com.example.carlocation.controls.logic.SharedPreferencesManagerVehicle;
import com.example.carlocation.model.Vehicle;
import com.example.carlocation.model.VehicleList;


import yuku.ambilwarna.AmbilWarnaDialog;


public class AddVehicle extends Fragment {

    private Button save;
    private VehicleList vehicleList;
    private Button colorBtn;
    private TextView vehicleName;
    private TextView licencePlate;
    private TextView date;
    private TextView note;

    private String colorValue;
    private SharedPreferences sharedPreferences;
    private SharedPreferencesManagerVehicle manager;
    Vehicle vehicle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_vehilcle, container, false);
        save = view.findViewById(R.id.saveVehicleBtn);
        colorBtn = view.findViewById(R.id.selectColorBtn);
        vehicleName = view.findViewById(R.id.vehiclaNameT);
        licencePlate = view.findViewById(R.id.licencePlateT);
        date = view.findViewById(R.id.dateT);
        note = view.findViewById(R.id.noteT);
        ListCRUD<Vehicle> crud = new ListCRUD<>(getActivity(), "myVehiclesList.json");
        vehicleList = new VehicleList(crud.loadList());
        sharedPreferences = getContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        manager = new SharedPreferencesManagerVehicle(sharedPreferences,vehicle);

        clearHintOnFocus(vehicleName,"Vehicle name..");
        clearHintOnFocus(licencePlate,"Licence plate..");
        clearHintOnFocus(note,"Add note..");
        clearHintOnFocus(date,"Year..");




        save.setOnClickListener(view1 -> {
            if (isTextViewNotEmpty(vehicleName) && isTextViewNotEmpty(licencePlate) && isTextViewNotEmpty(date) &&
                    isTextViewNotEmpty(note)) {
                String name = String.valueOf(vehicleName.getText());
                String LP = String.valueOf(licencePlate.getText());
                String dat = String.valueOf(date.getText());
                String not = String.valueOf(note.getText());
                if(colorValue==null) colorValue= String.valueOf(colorBtn.getSolidColor());
                Vehicle vehicle = new Vehicle(name, LP, dat, not,colorValue, "");
                VehicleList vehicleList = new VehicleList(crud.loadList());
                vehicleList.create(vehicle);
                crud.updateList(vehicleList.getMyList());

                manager.setLocation(vehicle);
                manager.putToSharedPreferences();
                SaveDialogFragment dialog = new SaveDialogFragment();
                dialog.show(getFragmentManager(), "SaveDialogFragment");
                Intent intent=new Intent(getContext(), VehicleActivity.class);
                startActivity(intent);



            } else {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
        colorBtn.setOnClickListener(view1 -> {
            openColorPicker();

            //to do
        });


        return view;

    }

    private void openColorPicker() {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(getContext(), 0xff000000, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                colorBtn.setBackgroundColor(color);
                colorValue = String.valueOf(color);
                colorBtn.setText("Change");
            }
        });
        dialog.show();
    }

    private boolean isTextViewNotEmpty(TextView textView) {
        return textView != null && textView.getText() != null &&
                !textView.getText().toString().trim().isEmpty();
    }
    public void clearHintOnFocus(final TextView editText, final String hint) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editText.setHint("");
                } else {
                    editText.setHint(hint);
                }
            }
        });
    }

}