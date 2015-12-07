package com.hotspothealthcode.hotspothealthcode.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hotspothealthcode.hotspothealthcode.R;

/**
 * Created by Giladl on 07/12/2015.
 */
public class GeneralPlumeSourceTermFragment extends HotspotFragment
{
    public static GeneralPlumeSourceTermFragment Instantiate(int sectionNumber)
    {
        GeneralPlumeSourceTermFragment fragment = new GeneralPlumeSourceTermFragment();

        Bundle args = new Bundle();

        args.putInt(ARG_SECTION_NUMBER, sectionNumber);

        fragment.setArguments(args);

        return fragment;
    }

    public GeneralPlumeSourceTermFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_general_plume_source_term, container, false);

        return rootView;
    }
}
