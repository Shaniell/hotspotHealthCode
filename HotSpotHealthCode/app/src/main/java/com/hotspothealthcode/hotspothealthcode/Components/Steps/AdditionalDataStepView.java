package com.hotspothealthcode.hotspothealthcode.Components.Steps;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.hotspothealthcode.hotspothealthcode.R;

import hotspothealthcode.BL.AtmosphericConcentration.AtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.TerrainType;
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
    private EditText Dfx;

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
        this.Dfx = (EditText)findViewById(R.id.etDfx);

        // Set default values
        this.sampleTime.setText("10");
        this.refereneHeight.setText("10");
        this.surfaceRoughnessHeight.setText("3");
        this.Dfx.setText("0.025");

        // Fill terrain types
        this.terrainType.setAdapter(new ArrayAdapter<TerrainType>(context,
                R.layout.spinner_text_view,
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
        calcConcentration.setTerrainType((TerrainType) this.terrainType.getSelectedItem());
        calcConcentration.setDfx(Double.parseDouble(this.Dfx.getText().toString()));
    }
}
