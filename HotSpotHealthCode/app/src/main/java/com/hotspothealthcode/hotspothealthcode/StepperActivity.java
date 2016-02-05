package com.hotspothealthcode.hotspothealthcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.hotspothealthcode.hotspothealthcode.Components.Steps.StepView;

import java.util.HashMap;
import java.util.Map;

public class StepperActivity extends AppCompatActivity {

    protected LinearLayout stepLinearView;
    protected HashMap<Integer, StepView> steps;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper);

        this.toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(this.toolbar);
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

    private void continueToOutput()
    {
        boolean areStepsValid = true;

        for (Map.Entry<Integer, StepView> entry: this.steps.entrySet())
        {
            if(!entry.getValue().isValid())
            {
                areStepsValid = false;
            }
        }

        if(areStepsValid)
        {
            //TODO: CREATE OUTPUT ACTIVITY
        }
    }
}
