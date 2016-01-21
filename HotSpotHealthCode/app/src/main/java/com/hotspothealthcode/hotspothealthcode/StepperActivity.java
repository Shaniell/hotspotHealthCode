package com.hotspothealthcode.hotspothealthcode;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.hotspothealthcode.hotspothealthcode.Components.Steps.GeneralFireStepView;
import com.hotspothealthcode.hotspothealthcode.Components.Steps.GeneralPlumeStepView;
import com.hotspothealthcode.hotspothealthcode.Components.Steps.MeteorologyStepView;
import com.hotspothealthcode.hotspothealthcode.Components.Steps.StepView;

public class StepperActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper);

        LinearLayout stepLinearView = (LinearLayout) findViewById(R.id.stepperLinearView);

        StepView stepView = new GeneralFireStepView(getApplicationContext(), 1, "Dispersion Model", R.layout.general_fire_step_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        stepLinearView.addView(stepView);

        StepView stepView2 = new GeneralPlumeStepView(getApplicationContext(), 2, "Dispersion Model", R.layout.general_plume_step_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        stepLinearView.addView(stepView2);

        StepView stepView3 = new MeteorologyStepView(getApplicationContext(), 2, "Dispersion Model", R.layout.meteorology_step_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        stepLinearView.addView(stepView3);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
