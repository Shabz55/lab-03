package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);
    }
    private AddCityDialogListener listener;
    private City editCity;

    private static final String CITY_KEY = "city_key";
    static AddCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable(CITY_KEY, city);
        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;

        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");

        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view .findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (getArguments() != null){
            editCity = (City) getArguments().getSerializable(CITY_KEY);
        }
        if (editCity != null) {
            return builder
                    .setView(view)
                    .setTitle("Edit")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Confirm", (dialog, which) -> {
                        editCity.setName(editCityName.getText().toString());
                        editCity.setProvince(editProvinceName.getText().toString());


                    })
                    .create();
        } else {
            return builder
                    .setView(view)
                    .setTitle("Add a City")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Add", (dialog, which) -> {
                        String cityName = editCityName.getText().toString();
                        String provinceName = editProvinceName.getText().toString();
                        listener.addCity(new City(cityName, provinceName));

                    })
                    .create();
        }
    }
}
