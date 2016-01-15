package com.hotspothealthcode.hotspothealthcode;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.hotspothealthcode.hotspothealthcode.Components.StepView;

public class StepperActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper);

        LinearLayout stepLinearView = (LinearLayout) findViewById(R.id.stepperLinearView);

        StepView stepView = new StepView(getApplicationContext());

        stepLinearView.addView(stepView);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
