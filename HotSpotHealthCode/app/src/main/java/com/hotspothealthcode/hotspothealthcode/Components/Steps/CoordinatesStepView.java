package com.hotspothealthcode.hotspothealthcode.Components.Steps;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.hotspothealthcode.hotspothealthcode.R;

import java.util.ArrayList;

import hotspothealthcode.BL.AtmosphericConcentration.AtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.results.ConcentrationPoint;
import hotspothealthcode.controllers.Controller;

/**
 * Created by Giladl on 16/01/2016.
 */
public class CoordinatesStepView extends StepView
{
    private static int COORDINATES_NUMBER = 10;

    private EditText verticalOffset;
    private GridLayout xYOffsets;
    private CheckBox enableAll;
    private Button restureDefaultsBtn;
    private ArrayList<CoordinateRow> coordinateRows;

    public CoordinatesStepView(Context context, int stepNumber, String title, int contentViewId) {
        super(context, stepNumber, title, contentViewId);

        this.initControl(context);
    }

    public CoordinatesStepView(Context context) {
        super(context);
    }

    public CoordinatesStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoordinatesStepView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void initControl(Context context)
    {
        // Get all fields
        this.verticalOffset = (EditText)findViewById(R.id.etVerticalOffset);
        this.xYOffsets = (GridLayout)findViewById(R.id.glXYCoordinates);
        this.enableAll = (CheckBox)findViewById(R.id.cbEnableAll);
        this.restureDefaultsBtn = (Button)findViewById(R.id.btnRestoreDefaults);

        // Set events
        this.enableAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = ((CheckBox)v).isChecked();

                // Enable or disable fields
                for (CoordinateRow coordinateRow: coordinateRows) {
                    coordinateRow.setEnableFields(isChecked);

                    coordinateRow.isEnabled.setChecked(isChecked);
                }
            }
        });

        this.restureDefaultsBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Restore default values
                for (CoordinateRow coordinateRow: coordinateRows) {
                    coordinateRow.restoreDefaultValues();
                }
            }
        });

        this.coordinateRows = new ArrayList<CoordinateRow>(COORDINATES_NUMBER);

        // Set grid layout columns and rows numbers
        this.xYOffsets.setColumnCount(5);
        this.xYOffsets.setRowCount(COORDINATES_NUMBER + 1);

        ArrayList<Double> defaultValues = Controller.getCoordinatesDefaultValues();

        // Create x,y coordinates
        for(int i = 1; i <= COORDINATES_NUMBER; i++)
        {
            CoordinateRow coordinateRow = new CoordinateRow(context, i, defaultValues.get(i - 1), 0);

            coordinateRow.addToGridLayout(this.xYOffsets);

            coordinateRows.add(coordinateRow);
        }
    }

    @Override
    protected boolean validateData() {

        boolean atLeastOneEntered = true;
        int enabledRows = 0;

        for (CoordinateRow coordinateRow: coordinateRows) {

            // If the row is enabled
            if(coordinateRow.isEnabled.isChecked())
            {
                // if the x field is empty, stop
                if(coordinateRow.xIEdit.getText().toString().matches("")) {
                    atLeastOneEntered = false;

                    break;
                }

                enabledRows++;
            }

            if (!(coordinateRow.xIEdit.getText().toString().matches("")))
            {
                atLeastOneEntered = true;

                break;
            }
        }

        return ((!this.verticalOffset.getText().toString().matches("")) &&
                (atLeastOneEntered) &&
                (enabledRows > 0));
    }

    @Override
    public void setFieldsToCalculate(AtmosphericConcentration calcConcentration) {

        ArrayList<ConcentrationPoint> points = new ArrayList<>();

        double verticalOffset = Double.parseDouble(this.verticalOffset.getText().toString());

        // Create concentration points
        for (CoordinateRow coordinateRow: coordinateRows) {

            // If the row is enabled, take its values
            if(coordinateRow.isEnabled.isChecked()) {
                double downWindOffset = Double.parseDouble(coordinateRow.xIEdit.getText().toString()) * 1000;
                double crossWindOffset = 0;

                // If the user ented a cross wind offset
                if(!coordinateRow.yIEdit.getText().toString().matches(""))
                    crossWindOffset = Double.parseDouble(coordinateRow.yIEdit.getText().toString()) * 1000;

                points.add(new ConcentrationPoint(downWindOffset, crossWindOffset, verticalOffset));
            }
        }

        calcConcentration.setConcentrationPoints(points);
    }

    @Override
    public void saveFieldsToSharedPreferences(Context context) {

    }

    private class CoordinateRow
    {
        private int rowNumber;
        private double xDefault;
        private double yDefault;

        public CheckBox isEnabled;
        public TextView xIView;
        public EditText xIEdit;
        public TextView yIView;
        public EditText yIEdit;

        public CoordinateRow(Context context, int rowNumber, double xDefault, double yDefault)
        {
            this.rowNumber = rowNumber;
            this.xDefault = xDefault;
            this.yDefault = yDefault;

            // Check box
            this.isEnabled = (CheckBox)View.inflate(context, R.layout.coordinate_checkbox, null);
            this.isEnabled.setChecked(true);

            this.isEnabled.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setEnableFields(((CheckBox)v).isChecked());
                }
            });

            // X
            this.xIView = (TextView)View.inflate(context, R.layout.coordinate_text_view, null);
            this.xIView.setText("X" + rowNumber);

            this.xIEdit = (EditText)View.inflate(context, R.layout.coordinate_text_edit, null);
            this.xIEdit.setText(String.valueOf(this.xDefault));

            // Y
            this.yIView = (TextView)View.inflate(context, R.layout.coordinate_text_view, null);
            this.yIView.setText("Y" + rowNumber);

            this.yIEdit = (EditText)View.inflate(context, R.layout.coordinate_text_edit, null);
            this.yIEdit.setText(String.valueOf(this.yDefault));
        }

        public void restoreDefaultValues()
        {
            this.xIEdit.setText(String.valueOf(this.xDefault));
            this.yIEdit.setText(String.valueOf(this.yDefault));
        }

        public void setEnableFields(boolean value)
        {
            this.xIView.setEnabled(value);
            this.xIEdit.setEnabled(value);
            this.yIView.setEnabled(value);
            this.yIEdit.setEnabled(value);
        }

        public void addToGridLayout(GridLayout gridLayout)
        {
            gridLayout.addView(this.isEnabled, new GridLayout.LayoutParams(
                    GridLayout.spec(this.rowNumber, GridLayout.CENTER),
                    GridLayout.spec(0)));

            gridLayout.addView(this.xIView, new GridLayout.LayoutParams(
                    GridLayout.spec(this.rowNumber, GridLayout.CENTER),
                    GridLayout.spec(1)));

            gridLayout.addView(this.xIEdit, new GridLayout.LayoutParams(
                    GridLayout.spec(this.rowNumber, GridLayout.CENTER),
                    GridLayout.spec(2)));

            gridLayout.addView(this.yIView, new GridLayout.LayoutParams(
                    GridLayout.spec(this.rowNumber, GridLayout.CENTER),
                    GridLayout.spec(3)));

            gridLayout.addView(this.yIEdit, new GridLayout.LayoutParams(
                    GridLayout.spec(this.rowNumber, GridLayout.CENTER),
                    GridLayout.spec(4)));
        }
    }
}
