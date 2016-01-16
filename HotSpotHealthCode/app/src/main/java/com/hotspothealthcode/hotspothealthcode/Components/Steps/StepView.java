package com.hotspothealthcode.hotspothealthcode.Components.Steps;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hotspothealthcode.hotspothealthcode.R;

public abstract class StepView extends GridLayout {

    protected static final int CURRENT_STEP = Color.parseColor("#C5CAE9");
    protected static final int REGULAR_STEP = Color.WHITE;

    protected Button stepIcon;
    protected Button stepContinueBtn;
    protected Button stepCancleBtn;
    protected LinearLayout stepContent;
    protected LinearLayout stepContentClosed;
    protected FrameLayout stepContentData;
    protected LinearLayout stepTitleLayout;
    protected TextView stepTitle;
    protected View stepLine;
    protected String title;
    protected View contentView;

    public StepView(Context context, String title, OnClickListener continueBtnHandler, int contentViewId) {
        super(context);
        initControl(context, title, continueBtnHandler, contentViewId);
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

    private void initControl(Context context, String title, OnClickListener continueBtnHandler, int contentViewId)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.step_view, this);

        this.stepIcon = (Button)findViewById(R.id.stepIconBtn);
        this.stepContinueBtn = (Button)findViewById(R.id.stepContinueBtn);
        this.stepCancleBtn = (Button)findViewById(R.id.stepCancelBtn);
        this.stepContent = (LinearLayout)findViewById(R.id.stepContent);
        this.stepContentClosed = (LinearLayout)findViewById(R.id.stepContentClosed);
        this.stepTitleLayout = (LinearLayout)findViewById(R.id.stepTitleLayout);
        this.stepContentData = (FrameLayout)findViewById(R.id.stepContentData);
        this.stepTitle = (TextView)findViewById(R.id.stepTitle);
        this.stepLine = findViewById(R.id.stepVerLine);

        // Load step content
        this.contentView = findViewById(contentViewId);
        inflater.inflate(contentViewId, this.stepContentData);

        // Set title
        this.stepTitle.setText(title.toCharArray(), 0, title.length());

        this.stepContinueBtn.setOnClickListener(continueBtnHandler);

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

    public void showContent()
    {
        stepTitleLayout.setBackgroundColor(StepView.CURRENT_STEP);

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

    public void setContinueHandler(OnClickListener listener)
    {
        this.stepContinueBtn.setOnClickListener(listener);
    }

    public boolean continueToNextStep()
    {
        boolean isDataValid = this.validateData();

        if(isDataValid)
        {
            this.hideContent();
        }

        return isDataValid;
    }

    protected abstract boolean validateData();
}
