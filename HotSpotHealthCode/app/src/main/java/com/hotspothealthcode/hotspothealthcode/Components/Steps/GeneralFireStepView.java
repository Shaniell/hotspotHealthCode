package com.hotspothealthcode.hotspothealthcode.Components.Steps;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.hotspothealthcode.hotspothealthcode.R;

import hotspothealthcode.BL.AtmosphericConcentration.AtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.FireAtmosphericConcentration;
import hotspothealthcode.BL.Models.Weather;
import hotspothealthcode.controllers.Controller;

/**
 * Created by Giladl on 16/01/2016.
 */
public class GeneralFireStepView extends StepView
{
    private Weather weather;

    private EditText materialAtRisk;
    private EditText releaseRadios;

    private TabHost tabHost;
    private EditText cloudTop;
    private EditText heatEmission;
    private EditText airTempEnter;
    private EditText fuelVolume;
    private EditText airTempCalc;
    private EditText burnDuration;

    public GeneralFireStepView(Context context, int stepNumber, String title,  int contentViewId, AtmosphericConcentration calcConcentration) {
        super(context, stepNumber, title, contentViewId, calcConcentration);

        this.initControl();
    }

    public GeneralFireStepView(Context context) {
        super(context);
    }

    public GeneralFireStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GeneralFireStepView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void initControl()
    {
        // create the TabHost that will contain the Tabs
        this.tabHost = (TabHost)findViewById(android.R.id.tabhost);
        this.tabHost.setup();

        TabSpec tab1 = this.tabHost.newTabSpec("CloudTop");
        TabSpec tab2 = this.tabHost.newTabSpec("EnterEmission");
        TabSpec tab3 = this.tabHost.newTabSpec("CalcEmission");

        tab1.setIndicator("Cloud Top");
        tab1.setContent(R.id.tbCloudTop);

        tab2.setIndicator("Enter Emission");
        tab2.setContent(R.id.tbEnterEmissionRate);

        tab3.setIndicator("Calc Emission");
        tab3.setContent(R.id.tbCalcHeatEmission);

        this.tabHost.addTab(tab1);
        this.tabHost.addTab(tab2);
        this.tabHost.addTab(tab3);

        // Get all fields
        this.cloudTop = (EditText)this.tabHost.findViewById(R.id.etCloudTop);
        this.heatEmission = (EditText)this.tabHost.findViewById(R.id.etHeatEmission);
        this.airTempEnter = (EditText)this.tabHost.findViewById(R.id.etAirTempEnter);
        this.fuelVolume = (EditText)this.tabHost.findViewById(R.id.etFuelVolume);
        this.airTempCalc = (EditText)this.tabHost.findViewById(R.id.etAirTempCalc);
        this.burnDuration = (EditText)this.tabHost.findViewById(R.id.etBurnDuration);

        this.materialAtRisk = (EditText)findViewById(R.id.etMaterialAtRisk);
        this.releaseRadios = (EditText)findViewById(R.id.etReleaseRadios);

        this.weather = Controller.getCurrentWeather();

        this.setDefaultWeatherData();

        // Disable tab focus (so the keyboard wont pop up when view loads)
        this.tabHost.clearFocus();
    }

    private void setDefaultWeatherData()
    {
        this.airTempEnter.setText(String.valueOf(this.weather.getTemperature()));
        this.airTempCalc.setText(String.valueOf(this.weather.getTemperature()));
    }

    @Override
    protected boolean validateData() {

        boolean emptyFieldsInTab = true;

        // Get current selected tab
        int tabId = this.tabHost.getCurrentTab();

        switch (tabId)
        {
            case 0:
            {
                emptyFieldsInTab = !this.cloudTop.getText().toString().matches("");

                break;
            }

            case 1:
            {
                emptyFieldsInTab = (!this.heatEmission.getText().toString().matches("")) &&
                                   (!this.airTempEnter.getText().toString().matches(""));

                break;
            }

            case 2:
            {
                emptyFieldsInTab = (!this.fuelVolume.getText().toString().matches("")) &&
                                   (!this.airTempCalc.getText().toString().matches("")) &&
                                   (!this.burnDuration.getText().toString().matches(""));

                break;
            }
        }

        return ((emptyFieldsInTab) &&
                (!this.materialAtRisk.getText().toString().matches("")) &&
                (!this.releaseRadios.getText().toString().matches("")));
    }

    @Override
    protected void setFieldsToCalculate() {
        FireAtmosphericConcentration concentration = (FireAtmosphericConcentration)this.calcConcentration;

        concentration.setSourceTerm(Double.parseDouble(this.materialAtRisk.getText().toString()));
        concentration.setReleaseRadios(Double.parseDouble(this.releaseRadios.getText().toString()));

        // Check which tab is selected
        switch (this.tabHost.getCurrentTab())
        {
            // Enter cloud top
            case 0:
            {
                concentration.setCloudTop(Double.parseDouble(this.cloudTop.getText().toString()));

                break;
            }
            // Enter emission tab
            case 1:
            {
                concentration.setEmissionRate(Double.parseDouble(this.heatEmission.getText().toString()));
                concentration.setAirTemp(Double.parseDouble(this.airTempEnter.getText().toString()));

                break;
            }
            // calc emission tab
            case 2:
            {
                concentration.setFuelVolume(Double.parseDouble(this.fuelVolume.getText().toString()));
                concentration.setAirTemp(Double.parseDouble(this.airTempCalc.getText().toString()));
                concentration.setBurnDuration(Integer.parseInt(this.burnDuration.getText().toString()));

                break;
            }
        }
    }
}
