package com.hotspothealthcode.hotspothealthcode;

import android.os.Bundle;
import android.app.Activity;
import android.widget.LinearLayout;

import com.hotspothealthcode.hotspothealthcode.Components.Steps.ChooseModelStepView;
import com.hotspothealthcode.hotspothealthcode.Components.Steps.StepView;

public class StepperActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper);

        LinearLayout stepLinearView = (LinearLayout) findViewById(R.id.stepperLinearView);

        StepView stepView = new ChooseModelStepView(getApplicationContext(), "Dispersion Model", null, R.layout.fragment_general_plume_source_term);

        stepLinearView.addView(stepView);

        StepView stepView2 = new ChooseModelStepView(getApplicationContext(), "Dispersion Model", null, R.layout.fragment_general_plume_source_term);

        stepLinearView.addView(stepView2);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
