package com.hotspothealthcode.hotspothealthcode;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hotspothealthcode.hotspothealthcode.FileActivities.SaveFileActivity;
import com.hotspothealthcode.hotspothealthcode.fragments.OutputDetailsFragment;
import com.hotspothealthcode.hotspothealthcode.fragments.OutputMapFragment;
import com.hotspothealthcode.hotspothealthcode.fragments.OutputTableFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import hotspothealthcode.BL.AtmosphericConcentration.MeteorologicalConditions;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStability;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStabilityType;
import hotspothealthcode.BL.AtmosphericConcentration.results.ConcentrationPoint;
import hotspothealthcode.BL.AtmosphericConcentration.results.ConcentrationResult;
import hotspothealthcode.BL.AtmosphericConcentration.results.OutputResult;
import hotspothealthcode.BL.AtmosphericConcentration.results.ResultField;
import hotspothealthcode.controllers.Controller;

public class OutputActivity extends AppCompatActivity {

    private final int TABS_NUM = 3;

    private OutputDetailsFragment outputDetailsFragment;
    private OutputTableFragment outputResultsFragment;
    private OutputMapFragment outputMapFragment;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private OutputResult outputResult;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        Toolbar toolbar = (Toolbar) findViewById(R.id.output_toolbar);
        setSupportActionBar(toolbar);

        this.outputResult = Controller.getOutputResultInstance();

        this.outputDetailsFragment = new OutputDetailsFragment();
        this.outputResultsFragment = new OutputTableFragment();
        this.outputMapFragment = new OutputMapFragment();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.output_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.output_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_output, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            Intent directoryChooserIntent = new Intent(getApplicationContext(), SaveFileActivity.class);

            startActivity(directoryChooserIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:
                {
                    return outputDetailsFragment;
                }

                case 1:
                {
                    return outputResultsFragment;
                }
                case 2:
                {
                    return outputMapFragment;
                }
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return TABS_NUM;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {

                case 0:
                    return "Details";

                case 1:
                    return "Results";

                case 2:
                    return "Map";
            }
            return null;
        }
    }
}
