package com.hotspothealthcode.hotspothealthcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.LatLng;
import com.hotspothealthcode.hotspothealthcode.Components.Steps.StepView;

import java.util.HashMap;
import java.util.Map;

import hotspothealthcode.BL.AtmosphericConcentration.AtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.PlumeAtmosphericConcentration;
import hotspothealthcode.controllers.Controller;

public abstract class StepperActivity extends AppCompatActivity {

    protected AtmosphericConcentration calcConcentration;

    protected LinearLayout stepLinearView;
    protected HashMap<Integer, StepView> steps;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper);

        this.toolbar = (Toolbar) findViewById(R.id.stepper_toolbar);
        setSupportActionBar(this.toolbar);

        //Todo: remove this
        Controller.init(new LatLng(40.8516701, -93.2599318));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stepper_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_continue_to_output:

                this.continueToOutput();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    protected abstract void initCalculationObject();

    private void continueToOutput()
    {
        boolean areStepsValid = true;

        for (Map.Entry<Integer, StepView> entry: this.steps.entrySet())
        {
            if(!entry.getValue().isValid())
            {
                areStepsValid = false;

                break;
            }
        }

        if(areStepsValid)
        {
            createCalculationObject();

            Controller.setCalcConcentration(this.calcConcentration);

            // Go to output activity
            Intent outpotActivityIntent = new Intent(getApplicationContext(), OutputActivity.class);

            startActivity(outpotActivityIntent);
        }
    }

    private void createCalculationObject()
    {
        this.initCalculationObject();

        // Get all the data from ths steps
        for(Map.Entry<Integer, StepView> entry: this.steps.entrySet())
        {
            entry.getValue().setFieldsToCalculate(this.calcConcentration);
        }
    }
}
