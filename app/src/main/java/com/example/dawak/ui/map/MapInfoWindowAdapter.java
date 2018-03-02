package com.example.dawak.ui.map;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.dawak.R;
import com.example.dawak.model.Pharmacy;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Map;

/**
 * Created by Mohamed Fakhry on 27/02/2018.
 */

public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    Map<Marker, Pharmacy> pharmacyMap;
    private final View container;

    public MapInfoWindowAdapter(Map<Marker, Pharmacy> pharmacyMap, Activity activity) {
        this.pharmacyMap = pharmacyMap;
        this.container = activity.getLayoutInflater().inflate(R.layout.map_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        Pharmacy pharmacy = pharmacyMap.get(marker);
        if (pharmacy != null) {
            TextView pharmacyNameTV = container.findViewById(R.id.pharmacyNameTV);
            pharmacyNameTV.setText(pharmacy.getName());
            if (pharmacy.getPhone() != null) {
                TextView pharmacyPhoneTV = container.findViewById(R.id.pharmacyPhoneTV);
                pharmacyPhoneTV.setText(pharmacy.getPhone());
            }
        }
        return container;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
