package com.example.dawak.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dawak.R;
import com.example.dawak.di.component.ActivityComponent;
import com.example.dawak.model.Pharmacy;
import com.example.dawak.ui.base.BaseFragment;
import com.example.dawak.ui.pharmacy.details.PharmacyDetailsActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Map;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MapFragment extends BaseFragment
        implements OnMapReadyCallback, OnSuccessListener<Location>, MapContract.View, GoogleMap.OnInfoWindowClickListener {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 101;

    private String title;

    private GoogleMap mMap;
    private LatLng userPosition;

    private FusedLocationProviderClient mFusedLocationClient;

    @Inject
    MapContract.Presenter<MapContract.View> presenter;

    @Inject
    Map<Marker, Pharmacy> pharmacyMap;

    public MapFragment() {
    }

    public static MapFragment newInstance(String title) {
        MapFragment fragment = new MapFragment();
        fragment.title = title;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    protected void setUp(View view) {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            getLocationPermission();

            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setInfoWindowAdapter(new MapInfoWindowAdapter(pharmacyMap, getActivity()));
            mMap.setOnInfoWindowClickListener(this);

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
            if (getBaseActivity().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    && getBaseActivity().hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION))
                updateLocationUI();
            else
                getBaseActivity().requestPermissionsSafely(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);

        }
    }

    private void getLocationPermission() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.length > 1) && (grantResults[0] +
                        grantResults[1]) == PackageManager.PERMISSION_GRANTED) {
                    updateLocationUI();
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void updateLocationUI() {
        mFusedLocationClient.getLastLocation().addOnSuccessListener(getBaseActivity(), this);

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    @Override
    public void onSuccess(Location location) {
        userPosition = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userPosition, 16.0f));
        presenter.showNearPharmacies(userPosition);
    }

    @Override
    public void showNearPharmacies(Pharmacy pharmacy) {
        if (pharmacy != null) {
            com.example.dawak.model.Location location = pharmacy.getLocation();
            if (location != null) {
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(location.getLat(), location.getLang()))
                        .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_pharmacy)));

                pharmacyMap.put(marker, pharmacy);
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Pharmacy pharmacy = pharmacyMap.get(marker);
        startActivity(PharmacyDetailsActivity.getStartIntent(getActivity(), pharmacy));
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_pharmacy);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
