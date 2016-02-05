package com.hotspothealthcode.hotspothealthcode.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.hotspothealthcode.hotspothealthcode.R;

import org.json.JSONArray;
import org.json.JSONException;

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

        View rootView = inflater.inflate(R.layout.fragment_output_map, container, false);

        // Get passed arguments
        Bundle args = this.getArguments();

        // Create concentration results array
        try
        {
            this.results = new ArrayList<ConcentrationResult>();

            JSONArray array = new JSONArray(args.getString("results"));

            // Convert to array list of concentration results
            for (int i = 0; i < array.length(); i++)
            {
                ConcentrationResult result = new ConcentrationResult(array.getJSONObject(i));

                this.results.add(result);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }
}
