package com.hotspothealthcode.hotspothealthcode.Components.Steps;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.hotspothealthcode.hotspothealthcode.R;

import hotspothealthcode.BL.AtmosphericConcentration.MeteorologicalConditions;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStabilityType;
import hotspothealthcode.controllers.Controller;

/**
 * Created by Giladl on 16/01/2016.
 */
public class MeteorologyStepView extends StepView
{
    private EditText windSpeed;
    private EditText windDirection;
    private CheckBox calcStability;
    private Spinner solarInfo;
    private Spinner stability;

    public MeteorologyStepView(Context context, int stepNumber, String title, int contentViewId, OnClickListener continueBtnHandler) {
        super(context, stepNumber, title, contentViewId, continueBtnHandler);

        this.initControl(context);
    }

    public MeteorologyStepView(Context context) {
        super(context);
    }

    public MeteorologyStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeteorologyStepView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void initControl(Context context)
    {
        // Get all fields
        this.windDirection = (EditText)findViewById(R.id.etWindDirection);
        this.windSpeed = (EditText)findViewById(R.id.etWindSpeed);
        this.calcStability = (CheckBox)findViewById(R.id.cbCalcStability);
        this.solarInfo = (Spinner)findViewById(R.id.spSolarInfo);
        this.stability = (Spinner)findViewById(R.id.spStability);

        this.solarInfo.setEnabled(false);
        this.stability.setEnabled(true);

        // Fill stability types
        this.stability.setAdapter(new ArrayAdapter<PasquillStabilityType>(context,
                android.R.layout.simple_list_item_1,
                Controller.getStabilityTypes()));

        // Fill Meteorological Conditions
        this.solarInfo.setAdapter(new ArrayAdapter<MeteorologicalConditions>(context,
                android.R.layout.simple_list_item_1,
                Controller.getMeteorologicalConditions()));

        // Set on item select listener
        this.solarInfo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(calcStability.isChecked())
                {
                    calcStability();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.windSpeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (calcStability.isChecked()) {
                    calcStability();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

                // Set calcStability click listener
        this.calcStability.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    solarInfo.setEnabled(true);
                    stability.setEnabled(false);

                    // Calc stability
                    calcStability();
                } else {
                    solarInfo.setEnabled(false);
                    solarInfo.setSelection(0);

                    stability.setEnabled(true);
                }
            }
        });
    }

    private void calcStability()
    {
        String windSpeedStr = windSpeed.getText().toString();

        // If the string is not empty
        if (!windSpeedStr.matches("")) {

            // Calc stability
            PasquillStabilityType type = Controller.calcStability(Double.parseDouble(windSpeedStr),
                    (MeteorologicalConditions) solarInfo.getSelectedItem());

            int position = ((ArrayAdapter<PasquillStabilityType>) stability.getAdapter()).getPosition(type);

            this.stability.setSelection(position);
        }
        else
        {
            this.stability.setSelection(0);
        }
    }

    @Override
    protected boolean validateData() {

        boolean allFieldsNotEmpty = !this.windDirection.getText().toString().matches("") &&
                                    !this.windSpeed.getText().toString().matches("");

        if(this.calcStability.isChecked())
        {
            allFieldsNotEmpty = allFieldsNotEmpty && this.solarInfo.getSelectedItemPosition() != 0;
        }
        else
        {
            allFieldsNotEmpty = allFieldsNotEmpty && this.stability.getSelectedItemPosition() != 0;
        }

        return allFieldsNotEmpty;
    }
}
