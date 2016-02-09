package com.hotspothealthcode.hotspothealthcode;

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

import com.hotspothealthcode.hotspothealthcode.fragments.OutputMapFragment;
import com.hotspothealthcode.hotspothealthcode.fragments.OutputTableFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import hotspothealthcode.BL.AtmosphericConcentration.results.ConcentrationPoint;
import hotspothealthcode.BL.AtmosphericConcentration.results.ConcentrationResult;
import hotspothealthcode.BL.AtmosphericConcentration.results.OutputResult;
import hotspothealthcode.controllers.Controller;

public class OutputActivity extends AppCompatActivity {

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

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.output_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.output_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // TODO: GET REAL RESULTS
        this.outputResult = Controller.getOutputResultInstance();

        ArrayList<ConcentrationResult> tempResults = new ArrayList<>();

        ConcentrationResult result = new ConcentrationResult(new ConcentrationPoint(5000, 1000.5, 1.5),
                                                                                    1234567,
                                                                                    380);

        ConcentrationResult result2 = new ConcentrationResult(new ConcentrationPoint(20000, 2000.5, 1.8),
                                                                                    1234567,
                                                                                    400);

        tempResults.add(result);
        tempResults.add(result2);

        this.outputResult.setResults(tempResults);
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
        if (id == R.id.action_settings) {
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

            if (position == 0) {
                Fragment outputMapFragment = new OutputMapFragment();

                return outputMapFragment;
            }
            else {
                Fragment outputTableFragment = new OutputTableFragment();

                return outputTableFragment;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Map";
                case 1:
                    return "Table";
            }
            return null;
        }
    }
}
