package com.example.barberbusiness;

import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.barberbusiness.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private TextView saveLocationText;
    private ImageView backBtn;
    private double latitude, longitude; // store the coordinates
    private Geocoder geocoder;
    List<Address> address; // store the address from the google map

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.design_default_color_secondary_variant));

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        saveLocationText = findViewById(R.id.saveLocationText);
        saveLocationText.setOnClickListener(saveLocationClick);
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        backBtn = findViewById(R.id.maps_backButton);
        backBtn.setOnClickListener(backBtnListener);

    }

    private View.OnClickListener backBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener saveLocationClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("address",  address.get(0).getAddressLine(0) + "/" + latitude + "/" + longitude);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng riyadh = new LatLng(24.726, 46.710);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(riyadh));
        mMap.setMinZoomPreference(11);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng));
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                try {
                     address = geocoder.getFromLocation(latitude,longitude,1);
                }catch (IOException e){
                    e.printStackTrace();
                }

                Log.d("googlemap", "onMapClick: latitude = " + latitude + " , longitude = " + longitude);
                Log.d("googlemap", "Address = " + address.get(0).getAddressLine(0));
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.remove();
                return true;
            }
        });
    }


}