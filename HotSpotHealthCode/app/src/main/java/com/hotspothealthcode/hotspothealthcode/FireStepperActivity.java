package com.hotspothealthcode.hotspothealthcode;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.hotspothealthcode.hotspothealthcode.Components.Steps.CoordinatesStepView;
import com.hotspothealthcode.hotspothealthcode.Components.Steps.GeneralFireStepView;
import com.hotspothealthcode.hotspothealthcode.Components.Steps.GeneralPlumeStepView;
import com.hotspothealthcode.hotspothealthcode.Components.Steps.MeteorologyStepView;
import com.hotspothealthcode.hotspothealthcode.Components.Steps.StepView;

import java.util.HashMap;

import hotspothealthcode.BL.AtmosphericConcentration.AtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.FireAtmosphericConcentration;
import hotspothealthcode.controllers.Controller;

public class FireStepperActivity extends StepperActivity{

    private FireAtmosphericConcentration fireAtmosphericConcentration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.fireAtmosphericConcentration = new FireAtmosphericConcentration();

        this.stepLinearView = (LinearLayout) findViewById(R.id.stepperLinearView);
        this.steps = new HashMap<Integer, StepView>();

        StepView stepView = new GeneralFireStepView(getApplicationContext(),
                                                    1,
                                                    "General Fire Data",
                                                    R.layout.general_fire_step_view,
                                                    this.fireAtmosphericConcentration);

        this.stepLinearView.addView(stepView);
        this.steps.put(1, stepView);

        StepView stepView2 = new MeteorologyStepView(getApplicationContext(),
                                                     2,
                                                     "Meteorology Data",
                                                     R.layout.meteorology_step_view,
                                                     this.fireAtmosphericConcentration);

        this.stepLinearView.addView(stepView2);
        this.steps.put(2, stepView2);

        StepView stepView3 = new CoordinatesStepView(getApplicationContext(),
                                                     3,
                                                     "Coordinates Data",
                                                     R.layout.coordinate_step_view,
                                                     this.fireAtmosphericConcentration);

        this.stepLinearView.addView(stepView3);
        this.steps.put(3, stepView3);

        stepView.setContinueHandler(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                steps.get(2).showContent();
            }
        });

        stepView2.setContinueHandler(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                steps.get(3).showContent();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected AtmosphericConcentration createCalculationObject() {
        return null;
    }
}
