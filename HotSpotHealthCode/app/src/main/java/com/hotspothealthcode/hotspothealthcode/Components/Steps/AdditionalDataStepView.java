package com.hotspothealthcode.hotspothealthcode.Components.Steps;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.hotspothealthcode.hotspothealthcode.R;

import hotspothealthcode.BL.AtmosphericConcentration.AtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.MeteorologicalConditions;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStability;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStabilityType;
import hotspothealthcode.BL.AtmosphericConcentration.TerrainType;
import hotspothealthcode.BL.Models.Weather;
import hotspothealthcode.controllers.Controller;

/**
 * Created by Giladl on 16/01/2016.
 */
public class AdditionalDataStepView extends StepView
{
    private Spinner terrainType;
    private EditText sampleTime;
    private EditText refereneHeight;
    private EditText surfaceRoughnessHeight;

    public AdditionalDataStepView(Context context, int stepNumber, String title, int contentViewId) {
        super(context, stepNumber, title, contentViewId);

        this.initControl(context);
    }

    public AdditionalDataStepView(Context context) {
        super(context);
    }

    public AdditionalDataStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdditionalDataStepView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void initControl(Context context)
    {
        // Get all fields
        this.terrainType = (Spinner)findViewById(R.id.spTerrainType);
        this.sampleTime = (EditText)findViewById(R.id.etSampleTime);
        this.refereneHeight = (EditText)findViewById(R.id.etReferenceHeight);
        this.surfaceRoughnessHeight = (EditText)findViewById(R.id.etSurfaceRoughnessHeight);

        // Set default values
        this.sampleTime.setText("10");
        this.refereneHeight.setText("10");
        this.surfaceRoughnessHeight.setText("3");

        // Fill terrain types
        this.terrainType.setAdapter(new ArrayAdapter<TerrainType>(context,
                android.R.layout.simple_list_item_1,
                Controller.getTerrainTypes()));
    }

    @Override
    protected boolean validateData() {

        return ((!this.sampleTime.getText().toString().matches("")) &&
                (!this.refereneHeight.getText().toString().matches("")) &&
                (!this.surfaceRoughnessHeight.getText().toString().matches("")));
    }

    @Override
    public void setFieldsToCalculate(AtmosphericConcentration calcConcentration)
    {
        calcConcentration.setSampleTime(Integer.parseInt(this.sampleTime.getText().toString()));
        calcConcentration.setReferenceHeight(Double.parseDouble(this.refereneHeight.getText().toString()));
        calcConcentration.setSurfaceRoughnessHeight(Double.parseDouble(this.surfaceRoughnessHeight.getText().toString()));
        calcConcentration.setTerrainType((TerrainType)this.terrainType.getSelectedItem());
    }
}
