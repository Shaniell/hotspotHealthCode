package com.hotspothealthcode.hotspothealthcode.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hotspothealthcode.hotspothealthcode.R;

import org.json.JSONException;

import hotspothealthcode.BL.AtmosphericConcentration.results.ConcentrationPoint;
import hotspothealthcode.BL.AtmosphericConcentration.results.ConcentrationResult;
import hotspothealthcode.BL.AtmosphericConcentration.results.OutputResult;
import hotspothealthcode.controllers.Controller;

/**
 * Created by Giladl on 05/02/2016.
 */
public class OutputMapFragment extends Fragment
{
    private OutputResult outputResult;
    private GoogleMap outputMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_output_map, container, false);

        // Get map view and map
        MapView mapView = (MapView)rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();

        this.outputMap = mapView.getMap();
        UiSettings uiSettings = this.outputMap.getUiSettings();

        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setMapToolbarEnabled(false);

        this.outputResult = Controller.getOutputResultInstance();

        this.drawResultsToMap();

        return rootView;
    }

    private void drawResultsToMap()
    {
        // TODO: MOVE TO REAL POSITION
        LatLng pos = new LatLng(40.8516701, -93.2599318);

        this.outputMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        this.outputMap.addMarker(new MarkerOptions()
                                .position(pos)
                                .title("Origin"));

        this.outputMap.setInfoWindowAdapter(new PointInfoWindowAdapter(getActivity()));

        this.outputMap.addPolyline(new PolylineOptions()
                .add(pos)
                .add(new ConcentrationPoint(100000, 0, 0).toLatLng(pos, 70)));

        for (ConcentrationResult result: this.outputResult.getResults())
        {
            LatLng res = result.getPoint().toLatLng(pos, 70);

            try
            {
                Marker marker = this.outputMap.addMarker(new MarkerOptions()
                        .position(res)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title("Coordinate")
                        .snippet(result.toJSON().toString()));
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        new MarkerOptions()
//                .position(MELBOURNE)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))

        /*LatLng res = this.results.get(1).getPoint().toLatLng(pos, 70);

        MarkerOptions markerOptions2 = new MarkerOptions();

        markerOptions2.position(res);
        markerOptions2.title("dest");

        this.outputMap.addMarker(markerOptions2);

        PolylineOptions polylineOptions = new PolylineOptions();

        polylineOptions.add(pos, res);

        this.outputMap.addPolyline(polylineOptions);*/
    }
}
