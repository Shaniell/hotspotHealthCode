package com.hotspothealthcode.hotspothealthcode.Components.Steps;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hotspothealthcode.hotspothealthcode.R;

import hotspothealthcode.BL.AtmosphericConcentration.AtmosphericConcentration;

public abstract class StepView extends GridLayout {

    protected static final int CURRENT_STEP = Color.parseColor("#C5CAE9");
    protected static final int REGULAR_STEP = Color.parseColor("#FFD6D1D1");

    protected Drawable stepIconGood;
    protected Drawable stepIconBad;

    protected String title;
    protected int stepNumber;

    protected View stepIcon;
    protected Button stepContinueBtn;
    protected Button stepCancleBtn;
    protected LinearLayout stepContent;
    protected LinearLayout stepContentClosed;
    protected FrameLayout stepContentData;
    protected LinearLayout stepTitleLayout;
    protected TextView stepTitle;
    protected TextView stepNumberView;
    protected View stepLine;
    protected View contentView;

    public StepView(Context context, int stepNumber, String title, int contentViewId) {
        super(context);

        initControl(context, stepNumber, title, contentViewId);
    }

    public StepView(Context context) {
        super(context);
    }

    public StepView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StepView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void initControl(Context context, int stepNumber, String title, int contentViewId)
    {
        // Load icons
        this.stepIconGood = context.getDrawable(R.drawable.step_icon_good);
        this.stepIconBad = context.getDrawable(R.drawable.step_icon_bad);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.step_view, this);

        this.stepIcon = findViewById(R.id.stepIcon);
        this.stepContinueBtn = (Button)findViewById(R.id.stepContinueBtn);
        this.stepCancleBtn = (Button)findViewById(R.id.stepCancelBtn);
        this.stepContent = (LinearLayout)findViewById(R.id.stepContent);
        this.stepContentClosed = (LinearLayout)findViewById(R.id.stepContentClosed);
        this.stepTitleLayout = (LinearLayout)findViewById(R.id.stepTitleLayout);
        this.stepContentData = (FrameLayout)findViewById(R.id.stepContentData);
        this.stepTitle = (TextView)findViewById(R.id.stepTitle);
        this.stepNumberView = (TextView)findViewById(R.id.stepNumber);
        this.stepLine = findViewById(R.id.stepVerLine);

        // Load step content
        this.contentView = findViewById(contentViewId);
        inflater.inflate(contentViewId, this.stepContentData);

        // Set title
        this.stepTitle.setText(title);

        // Set step number
        this.stepNumberView.setText(String.valueOf(stepNumber));

        this.stepTitleLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // If the content is visible do hide animation
                if (stepContent.getVisibility() == View.VISIBLE) {
                    hideContent();

                } else // If the content is invisible do show animation
                {
                    showContent();
                }
            }
        });
    }

    public void setContinueHandler(final OnClickListener continueBtnHandler)
    {
        this.stepContinueBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = validateData();

                // hide step number
                stepNumberView.setVisibility(INVISIBLE);

                if(isValid)
                {
                    hideContent();

                    // Set good icon
                    stepIcon.setBackground(stepIconGood);

                    // Call stepper on click
                    continueBtnHandler.onClick(v);
                }
                else
                {
                    // Set good icon
                    stepIcon.setBackground(stepIconBad);
                }
            }
        });
    }

    public void showContent()
    {
        stepLine.setPivotY(0);
        stepLine.animate()
                .scaleY(1.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                });

        stepContentClosed.setVisibility(View.GONE);
        stepContent.setVisibility(View.VISIBLE);

        stepContent.animate()
                .translationY(0)
                .alpha(1.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        stepTitleLayout.setBackgroundColor(StepView.CURRENT_STEP);
                    }
                });
    }

    public void hideContent()
    {
        stepTitleLayout.setBackgroundColor(StepView.REGULAR_STEP);

        stepLine.setPivotY(1);

        stepLine.animate()
                .scaleY(0.2f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                });

        stepContent.animate()
                .translationY(-stepContent.getHeight())
                .alpha(0.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        stepContent.setVisibility(View.GONE);
                        stepContentClosed.setVisibility(View.VISIBLE);
                    }
                });
    }

    public boolean isValid()
    {
        boolean isDataValid = this.validateData();

        // hide step number
        this.stepNumberView.setVisibility(INVISIBLE);

        if(isDataValid)
        {
            // Set good icon
            this.stepIcon.setBackground(stepIconGood);
        }
        else
        {
            // Set good icon
            this.stepIcon.setBackground(stepIconBad);
        }

        return isDataValid;
    }

    protected abstract boolean validateData();

    public abstract void setFieldsToCalculate(AtmosphericConcentration calcConcentration);
}
