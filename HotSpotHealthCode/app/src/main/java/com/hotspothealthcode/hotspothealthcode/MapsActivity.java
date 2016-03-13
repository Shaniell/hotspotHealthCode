package com.hotspothealthcode.hotspothealthcode;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import hotspothealthcode.controllers.Controller;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener  {

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
    Button btnNext;

    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //txtSearchBox = (AutoCompleteTextView) findViewById(R.id.txtSearchPlace);

        btnNext = (Button)findViewById(R.id.btnNext);
        btnNext.setVisibility(View.GONE);

        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mapsActivity = new Intent(getApplicationContext(), MapsActivity.class);

                Controller.init(SelectedLocation);

                startActivity(NextIntent);
            }
        });

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
                SelectedLocation = place.getLatLng();
                mMap.addMarker(new MarkerOptions().position(SelectedLocation).title(place.getName().toString()));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SelectedLocation, 15));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(SelectedLocation, 15));
                //Log.i("a", "Place: " + place.getName());

                btnNext.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                //Log.i("a", "An error occurred: " + status);
            }
        });

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.maps_toolbar);
        toolbar.setTitle("Hotspot Health Code");

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                SelectedLocation = place.getLatLng();
                mMap.addMarker(new MarkerOptions().position(SelectedLocation).title(place.getName().toString()));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SelectedLocation, 15));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(SelectedLocation, 15));
                //Log.i("a", "Place: " + place.getName());

                btnNext.setVisibility(View.VISIBLE);
            }
        }
    }

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
