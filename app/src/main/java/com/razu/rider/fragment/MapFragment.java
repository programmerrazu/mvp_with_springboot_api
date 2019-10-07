package com.razu.rider.fragment;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.razu.rider.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment fragmentMaps;
    private GoogleMap maps;
    private Boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final float MAP_ZOOM = 14.0f;
    private View btnLocation;
    private FloatingActionButton fabBtnLocation;
    private CardView cardViewSearchContainer;

    public MapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        fabBtnLocation = (FloatingActionButton) view.findViewById(R.id.fab_btn_location);
        cardViewSearchContainer = (CardView) view.findViewById(R.id.card_view_search_container);
        cardViewSearchContainer.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_fragment_container, new LocationSearchFragment(), "LocationSearchFragment")
                        .addToBackStack(null)
                        .commit();
                // fabBtnLocation.setVisibility(View.GONE);
                //  cardViewSearchContainer.setVisibility(View.GONE);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fragmentMaps = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_maps);
        assert fragmentMaps != null;
        fragmentMaps.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        maps = googleMap;
        maps.setTrafficEnabled(false);
        maps.setIndoorEnabled(false);
        maps.setBuildingsEnabled(false);
        maps.getUiSettings().setZoomControlsEnabled(false);
        getLocationPermission();
        getLocations();
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void getLocations() {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng pickupLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    maps.moveCamera(CameraUpdateFactory.newLatLngZoom(pickupLatLng, MAP_ZOOM));
                    maps.setMyLocationEnabled(true);
                    customiseBtnLocation();
                }
            }
        });
    }

    private void customiseBtnLocation() {
        if (maps == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                btnLocation = ((View) fragmentMaps.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                if (btnLocation != null) {
                    btnLocation.setVisibility(View.GONE);
                }
                fabBtnLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (maps != null) {
                            if (btnLocation != null) {
                                btnLocation.callOnClick();
                            }
                        }
                    }
                });
            } else {
                getLocationPermission();
            }
        } catch (SecurityException e) {

        }
    }
}