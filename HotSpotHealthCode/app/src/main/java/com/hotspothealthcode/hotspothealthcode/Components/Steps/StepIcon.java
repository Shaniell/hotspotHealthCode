package com.hotspothealthcode.hotspothealthcode.Components.Steps;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/**
 * Created by Giladl on 15/01/2016.
 */
public class StepIcon extends Drawable
{

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();

        p.setARGB(1, 255, 255, 255);

        canvas.drawText("g",0, 0, p);
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
