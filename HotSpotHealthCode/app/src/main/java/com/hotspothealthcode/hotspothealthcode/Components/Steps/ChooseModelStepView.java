package com.hotspothealthcode.hotspothealthcode.Components.Steps;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Giladl on 16/01/2016.
 */
public class ChooseModelStepView extends StepView
{
    public ChooseModelStepView(Context context, String title, OnClickListener continueBtnHandler, int contentViewId) {
        super(context, title, continueBtnHandler, contentViewId);
    }

    public ChooseModelStepView(Context context) {
        super(context);
    }

    public ChooseModelStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChooseModelStepView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean validateData() {
        return false;
    }
}
