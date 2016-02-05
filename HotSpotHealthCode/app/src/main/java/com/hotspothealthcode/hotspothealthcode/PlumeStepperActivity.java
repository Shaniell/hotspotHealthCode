package com.hotspothealthcode.hotspothealthcode;

import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;

import com.hotspothealthcode.hotspothealthcode.Components.Steps.CoordinatesStepView;
import com.hotspothealthcode.hotspothealthcode.Components.Steps.GeneralPlumeStepView;
import com.hotspothealthcode.hotspothealthcode.Components.Steps.MeteorologyStepView;
import com.hotspothealthcode.hotspothealthcode.Components.Steps.StepView;

import java.util.HashMap;

public class PlumeStepperActivity extends StepperActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.stepLinearView = (LinearLayout) findViewById(R.id.stepperLinearView);
        this.steps = new HashMap<Integer, StepView>();

        StepView stepView = new GeneralPlumeStepView(getApplicationContext(), 1, "General Plume Data", R.layout.general_plume_step_view);

        this.stepLinearView.addView(stepView);
        this.steps.put(1, stepView);

        StepView stepView2 = new MeteorologyStepView(getApplicationContext(), 2, "Meteorology Data", R.layout.meteorology_step_view);

        this.stepLinearView.addView(stepView2);
        this.steps.put(2, stepView2);

        StepView stepView3 = new CoordinatesStepView(getApplicationContext(), 3, "Coordinates Data", R.layout.coordinate_step_view);

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
}
