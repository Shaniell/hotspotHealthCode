package com.hotspothealthcode.hotspothealthcode.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hotspothealthcode.hotspothealthcode.R;

import hotspothealthcode.BL.AtmosphericConcentration.results.OutputResult;
import hotspothealthcode.BL.AtmosphericConcentration.results.ResultField;
import hotspothealthcode.controllers.Controller;

/**
 * Created by Giladl on 05/02/2016.
 */
public class OutputDetailsFragment extends Fragment
{
    private OutputResult outputResult;

    private TextView modelType;
    private TextView stabilityType;
    private TextView windSpeed;
    private TextView windDirection;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_output_details, container, false);

        this.modelType = (TextView)rootView.findViewById(R.id.tvModelTypeValue);
        this.stabilityType = (TextView)rootView.findViewById(R.id.tvStabilityTypeValue);
        this.windSpeed = (TextView)rootView.findViewById(R.id.tvWindSpeedValue);
        this.windDirection = (TextView)rootView.findViewById(R.id.tvWindDirectionValue);

        this.outputResult = Controller.getOutputResultInstance();

        this.modelType.setText(this.outputResult.getValue(ResultField.MODEL_TYPE).toString());
        this.stabilityType.setText(this.outputResult.getValue(ResultField.STABILITY_TYPE).toString());
        this.windSpeed.setText(this.outputResult.getValue(ResultField.WIND_SPEED).toString());
        this.windDirection.setText(this.outputResult.getValue(ResultField.WIND_DIRECTION).toString());

        return rootView;
    }
}
