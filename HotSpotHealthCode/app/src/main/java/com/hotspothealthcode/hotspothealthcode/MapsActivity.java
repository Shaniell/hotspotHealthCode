package com.hotspothealthcode.hotspothealthcode;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import hotspothealthcode.controllers.Controller;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    AutoCompleteTextView txtSearchBox;
    private GoogleApiClient mGoogleApiClient;
    PlaceAutocompleteFragment autocompleteFragment;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    AutocompleteFilter typeFilter;
    PendingResult<AutocompletePredictionBuffer> result;
    Bundle extras;
    Intent NextIntent;
    LatLng SelectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //txtSearchBox = (AutoCompleteTextView) findViewById(R.id.txtSearchPlace);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                SelectedLocation = new LatLng(place.getLatLng().latitude,place.getLatLng().longitude);
                mMap.addMarker(new MarkerOptions().position(SelectedLocation).title(place.getName().toString()));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SelectedLocation, 15));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(SelectedLocation, 15));
                //Log.i("a", "Place: " + place.getName());

                Intent mapsActivity = new Intent(getApplicationContext(), MapsActivity.class);
                
                Controller.init(SelectedLocation);

                startActivity(NextIntent);

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                //Log.i("a", "An error occurred: " + status);
            }
        });

        extras = getIntent().getExtras();
        if (extras != null) {
            NextIntent = (Intent)extras.get("NextIntent");
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

        }

        mMap.setMyLocationEnabled(true);
        Object a = mMap.getMyLocation();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        txtSearchBox.setText("NO CONNECTION!");
        txtSearchBox.setEnabled(false);
    }
}
