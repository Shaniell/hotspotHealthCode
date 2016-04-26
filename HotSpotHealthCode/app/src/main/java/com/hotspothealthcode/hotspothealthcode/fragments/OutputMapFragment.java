package com.hotspothealthcode.hotspothealthcode.fragments;


import android.graphics.Color;
import android.graphics.Path;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hotspothealthcode.hotspothealthcode.MapsActivity;
import com.hotspothealthcode.hotspothealthcode.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import hotspothealthcode.BL.AtmosphericConcentration.results.ConcentrationPoint;
import hotspothealthcode.BL.AtmosphericConcentration.results.ConcentrationResult;
import hotspothealthcode.BL.AtmosphericConcentration.results.OutputResult;
import hotspothealthcode.BL.AtmosphericConcentration.results.ResultField;
import hotspothealthcode.controllers.Controller;

/**
 * Created by Giladl on 05/02/2016.
 */
public class OutputMapFragment extends Fragment
{
    private OutputResult outputResult;
    private MapView mapView;
    private GoogleMap outputMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_output_map, container, false);

        // Get map view and map
        this.mapView = (MapView)rootView.findViewById(R.id.mapView);
        this.mapView.onCreate(savedInstanceState);

        this.outputResult = Controller.getOutputResultInstance();

        this.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                outputMap = googleMap;

                UiSettings uiSettings = outputMap.getUiSettings();

                uiSettings.setZoomControlsEnabled(true);
                uiSettings.setZoomGesturesEnabled(true);
                uiSettings.setScrollGesturesEnabled(true);
                uiSettings.setMapToolbarEnabled(false);

                drawResultsToMap();
            }
        });

        return rootView;
    }

    private void drawResultsToMap()
    {
        ArrayList<ConcentrationPoint> polygonPoints = new ArrayList<>();

        LatLng pos = (LatLng)this.outputResult.getValue(ResultField.LOCATION);

        double windDirection = Double.parseDouble(outputResult.getValue(ResultField.WIND_DIRECTION).toString());

        this.outputMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        this.outputMap.addMarker(new MarkerOptions()
                .position(pos)
                .title("Origin"));

        // Get close to location
        this.outputMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));

        this.outputMap.setInfoWindowAdapter(new PointInfoWindowAdapter(getActivity()));

        this.outputMap.addPolyline(new PolylineOptions()
                .add(pos)
                .add(new ConcentrationPoint(80000, 0, 0).toLatLng(pos, windDirection)));

        double dy = (double)this.outputResult.getValue(ResultField.DOWN_WIND_VIRTUAL_SOURCE);

        // Check if the source distance is 0
        if (dy == 0) {
            polygonPoints.add(new ConcentrationPoint(0, 0, 0));
            polygonPoints.add(new ConcentrationPoint(0, 0, 0));
        }
        else
        {
            polygonPoints.add(new ConcentrationPoint(-dy, 0, 0));
            polygonPoints.add(new ConcentrationPoint(-dy, 0, 0));
        }

        for (ConcentrationResult result: this.outputResult.getResults())
        {
            LatLng res = result.getPoint().toLatLng(pos, windDirection);

            Marker marker = this.outputMap.addMarker(new MarkerOptions()
                            .position(res)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .title("Coordinate")
                            .snippet(String.valueOf(result.getId())));

            polygonPoints.add(new ConcentrationPoint(result.getPoint().getX(), result.getCrossWindRadios(), 0));
            polygonPoints.add(new ConcentrationPoint(result.getPoint().getX(), -result.getCrossWindRadios(), 0));
        }

        int colorIndex = 0;

        for (int i = 0; i < polygonPoints.size() - 2; i += 2)
        {
            PolygonOptions polygonOptions = new PolygonOptions();

            polygonOptions.strokeColor(Color.argb(179, 255, 26 * colorIndex, 26 * colorIndex));
            polygonOptions.strokeWidth(3);
            polygonOptions.fillColor(Color.argb(179, 255, 26 * colorIndex, 26 * colorIndex));

            polygonOptions.add(polygonPoints.get(i).toLatLng(pos, windDirection));
            polygonOptions.add(polygonPoints.get(i + 2).toLatLng(pos, windDirection));
            polygonOptions.add(polygonPoints.get(i + 3).toLatLng(pos, windDirection));
            polygonOptions.add(polygonPoints.get(i + 1).toLatLng(pos, windDirection));

            this.outputMap.addPolygon(polygonOptions);

            colorIndex++;
        }
    }

    @Override
    public void onResume() {
        this.mapView.onResume();

        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        this.mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        this.mapView.onLowMemory();
    }

    /*public void drawEllipse(LatLng point) {

        LatLng pos = (LatLng)this.outputResult.getValue(ResultField.LOCATION);

        PolygonOptions op =  new PolygonOptions();

        LatLng center = new LatLng(Math.abs((point.latitude+pos.latitude)/2), Math.abs((point.longitude + pos.longitude)/2));
        LatLng center1 = new LatLng(pos.latitude+0.1, pos.longitude);
        LatLng center2 =  new LatLng(point.latitude+0.1, point.longitude);

        float [] results = new float[2];
        float [] results2 = new float[2];
        Location.distanceBetween(center.latitude, center.longitude, center1.latitude, center1.longitude, results);
        Location.distanceBetween(center.latitude, center.longitude, center1.latitude, center1.longitude, results2);
        LatLng latConv = new LatLng(results[0] * 10,results[1] * 10) ;
        LatLng lngConv = new LatLng(results2[0] * 10,results2[1] * 10) ;;
        List<LatLng> points;
        double Angle;
        for (Angle=0; Angle<360; Angle++){
            float x = (float) (center.latitude + ((10*Math.cos(Angle*(Math.PI/180)))/(float)latConv.latitude));
            float y = (float) (center.longitude + ((20*Math.sin(Angle*(Math.PI/180)))/lngConv.latitude));
            LatLng dataPoint = new LatLng(x,y);
            op.add(dataPoint);
        }

        op.strokeColor(Color.argb(99,99,0,0));
        op.strokeWidth(3);
        op.fillColor(Color.argb(50,99,0,0));

        outputMap.addPolygon(op);

    }

    public void drawTriangle(LatLng point, int MarkerDistanse) {

        LatLng pos = (LatLng)this.outputResult.getValue(ResultField.LOCATION);

        PolygonOptions op =  new PolygonOptions();

        op.add(pos);
        op.add(new LatLng(point.latitude + (MarkerDistanse*0.1), point.longitude + (MarkerDistanse*0.1)));
        op.add(new LatLng(point.latitude - (MarkerDistanse*0.1), point.longitude - (MarkerDistanse*0.1)));

        op.strokeColor(Color.argb(50,99,0,0));
        op.strokeWidth(3);
        op.fillColor(Color.argb(50, 99, 0, 0));

        outputMap.addPolygon(op);

    }

    // It's a rectangle for a while. beautiful rectangle.
    public void drawGoodTriangle(LatLng point, int MarkerDistanse) {

        LatLng pos = (LatLng)this.outputResult.getValue(ResultField.LOCATION);

        double angle = angleFromCoordinate(pos.latitude, pos.longitude, point.latitude, point.longitude);





        PolygonOptions op =  new PolygonOptions();

        op.add(pos);

        op.add(moveForward(angle, pos));
        op.add(moveForward(angle, point));
        op.add(moveBackwards(angle, point));
        op.add(moveBackwards(angle, pos));


        op.strokeColor(Color.argb(50, 99, 0, 0));
        op.strokeWidth(3);
        op.fillColor(Color.argb(50, 99, 0, 0));

        outputMap.addPolygon(op);

    }

    private double angleFromCoordinate(double lat1, double long1, double lat2,
                                       double long2) {

        double dLon = (long2 - long1);

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;
        brng = 360 - brng;

        return brng;
    }

    private LatLng move(double angle, LatLng originalPoint){

        double latitude = originalPoint.latitude +  (Math.sin(Math.toRadians(angle)) * 0.01);
        double longitude = originalPoint.longitude +  (Math.cos(Math.toRadians(angle)) * 0.01);

        LatLng location = new LatLng(latitude, longitude);

        return location;
    }

    private LatLng moveForward(double angle,LatLng originalPoint)
    {
        return move(angle, originalPoint);
    }

    private LatLng strafeLeft(double angle,LatLng originalPoint)
    {
        return move(angle + 90, originalPoint);
    }

    private LatLng strafeRight(double angle,LatLng originalPoint)
    {
        return  move(angle - 90, originalPoint);
    }

    private LatLng moveBackwards(double angle,LatLng originalPoint)
    {
        return  move(angle+180, originalPoint);
    }*/

}
