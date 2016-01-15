package com.hotspothealthcode.hotspothealthcode.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.hotspothealthcode.hotspothealthcode.R;
import com.hotspothealthcode.hotspothealthcode.fragments.GeneralPlumeSourceTermFragment;

public class StepView extends GridLayout {

    private Button stepIcon;
    private Button stepContinueBtn;
    private Button stepCancleBtn;
    private LinearLayout stepContent;
    private LinearLayout stepContentClosed;
    private FrameLayout stepContentData;
    private View stepLine;

    public StepView(Context context) {
        super(context);
        initControl(context);
    }

    public StepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
    }

    public StepView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initControl(context);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);

        /*.getLayoutParams().height = stepContent.getHeight();

        stepLine.requestLayout();*/
    }

    private void initControl(Context context)
    {
        LayoutInflater inflater = (LayoutInflater)
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.step_view, this);

        this.stepIcon = (Button)findViewById(R.id.stepIconBtn);
        this.stepContinueBtn = (Button)findViewById(R.id.stepContinueBtn);
        this.stepCancleBtn = (Button)findViewById(R.id.stepCancelBtn);
        this.stepContent = (LinearLayout)findViewById(R.id.stepContent);
        this.stepContentClosed = (LinearLayout)findViewById(R.id.stepContentClosed);
        this.stepContentData = (FrameLayout)findViewById(R.id.stepContentData);
        this.stepLine = findViewById(R.id.stepVerLine);

        this.stepIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // If the content is visible do hide animation
                if(stepContent.getVisibility() == View.VISIBLE) {

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

                                    //stepLine.getLayoutParams().height = stepContentClosed.getHeight();

                                    //stepLine.requestLayout();
                                }
                            });
                }
                else // If the content is invisible do show animation
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

                                    /*stepLine.getLayoutParams().height = stepContent.getHeight();

                                    stepLine.requestLayout();*/
                                }
                            });
                }

                /*stepContent.setVisibility(View.GONE);
                stepContent.setAlpha(1.0f);

                // Start the animation
                stepContent.animate()
                        .translationY(-stepContent.getHeight())
                        .alpha(0.0f);*/


            }
        });

        ViewGroup.LayoutParams layoutParams = this.stepLine.getLayoutParams();

        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        this.stepLine.setLayoutParams(layoutParams);

        // layout is inflated, assign local variables to components
        /*header = (LinearLayout)findViewById(R.id.calendar_header);
        btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView)findViewById(R.id.calendar_next_button);
        txtDate = (TextView)findViewById(R.id.calendar_date_display);
        grid = (GridView)findViewById(R.id.calendar_grid);*/
    }


}
