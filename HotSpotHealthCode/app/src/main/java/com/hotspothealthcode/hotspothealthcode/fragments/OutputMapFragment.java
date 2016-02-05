package com.hotspothealthcode.hotspothealthcode.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hotspothealthcode.hotspothealthcode.R;

import java.util.ArrayList;

import hotspothealthcode.BL.AtmosphericConcentration.ConcentrationResult;

/**
 * Created by Giladl on 05/02/2016.
 */
public class OutputMapFragment extends Fragment
{
    private ArrayList<ConcentrationResult> results;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();

        //this.results = (ArrayList<ConcentrationResult>)args.get("results");

        View rootView = inflater.inflate(R.layout.fragment_output_map, container, false);

        return rootView;
    }
}
