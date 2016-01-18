package com.hotspothealthcode.hotspothealthcode.Components.Steps;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.google.android.gms.common.internal.GetServiceRequest;
import com.hotspothealthcode.hotspothealthcode.R;

/**
 * Created by Giladl on 15/01/2016.
 */
public class StepIcon extends Drawable
{
    private static final int REG_COLOR = Color.parseColor("#00f");
    private static final int GOOD_COLOR = Color.parseColor("#0f0");
    private static final int BAD_COLOR = Color.parseColor("#f00");

    private Drawable goodIcon;
    private Drawable badIcon;
    private int backgroundColor;
    private Drawable background;
    private Drawable icon;
    private String stepNumber;
    private Context context;

    public StepIcon(Context context, String stepNumber)
    {
        super();

        /*this.context = context;
        this.stepNumber = stepNumber;
        this.backgroundColor = StepIcon.REG_COLOR;

        Resources r = context.getResources();

        this.goodIcon = r.getDrawable(R.drawable.ic_step_good, r.newTheme());
        this.badIcon = r.getDrawable(R.drawable.ic_step_bad, r.newTheme());

        this.background = r.getDrawable(R.drawable.step_icon_background, r.newTheme());
        this.background.setTint(this.backgroundColor);*/
    }

    @Override
    public void draw(Canvas canvas) {

        if(this.icon == null) {
            Paint p = new Paint();

            p.setARGB(1, 255, 255, 255);

            this.background.draw(canvas);
            canvas.drawText(this.stepNumber, 0, 0, p);
        }
        else
        {
            this.background.setTint(StepIcon.GOOD_COLOR);
            this.background.draw(canvas);

            this.icon.draw(canvas);
        }
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
