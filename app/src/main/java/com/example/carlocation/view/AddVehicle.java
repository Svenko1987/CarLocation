package com.example.carlocation.view;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlocation.MainActivity;
import com.example.carlocation.R;
import com.example.carlocation.controls.logic.ListCRUD;
import com.example.carlocation.model.ParkEvent;
import com.example.carlocation.model.Vehicle;
import com.example.carlocation.model.VehicleList;

import yuku.ambilwarna.AmbilWarnaDialog;


public class AddVehicle extends Fragment {

    private ImageView vehicleImage;
    private Button save;
    private VehicleList vehicleList;
    private Button colorBtn;
    private TextView vehicleName;
    private TextView licencePlate;
    private TextView manufacturer;
    private TextView date;
    private TextView note;

    private String colorValue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_vehilcle, container, false);
        //vehicleImage = view.findViewById(R.id.imageView2);
        save = view.findViewById(R.id.saveVehicleBtn);
        colorBtn = view.findViewById(R.id.selectColorBtn);
        vehicleName = view.findViewById(R.id.vehiclaNameT);
        licencePlate = view.findViewById(R.id.licencePlateT);
        manufacturer = view.findViewById(R.id.manufacturerT);
        date = view.findViewById(R.id.dateT);
        note = view.findViewById(R.id.noteT);
        // vehicleImage.setBackgroundResource(R.drawable.pngegg);
        ListCRUD<Vehicle> crud = new ListCRUD<>(getActivity(), "myVehiclesList.json");
        vehicleList = new VehicleList(crud.loadList());


        save.setOnClickListener(view1 -> {
            if (isTextViewNotEmpty(vehicleName) && isTextViewNotEmpty(licencePlate) &&
                    isTextViewNotEmpty(manufacturer) && isTextViewNotEmpty(date) &&
                    isTextViewNotEmpty(note)) {
                String name = String.valueOf(vehicleName.getText());
                String LP = String.valueOf(licencePlate.getText());
                String man = String.valueOf(manufacturer.getText());
                String dat = String.valueOf(date.getText());
                String not = String.valueOf(note.getText());
                Vehicle vehicle = new Vehicle(name, LP, man, dat, not, colorValue, "");
                VehicleList vehicleList = new VehicleList(crud.loadList());
                vehicleList.create(vehicle);
                crud.updateList(vehicleList.getMyList());
                Log.d(TAG, "onCreateView: SAVED IN LIST");
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

}