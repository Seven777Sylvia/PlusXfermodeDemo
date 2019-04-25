package com.sevenqiu.plusxfermodedemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static com.sevenqiu.plusxfermodedemo.util.Util.dip2Pixl;

/**
 * @author Seven Qiu
 * @date 2019-04-25
 */
public class PieChart extends View {
    private static final float RADIUS = dip2Pixl(100f);
    private static final int PULLED_INDEX = 2;
    private static final float PULLED_OFFSET = dip2Pixl(20);

    private int[] colors = {Color.parseColor("#6F2D78"),
            Color.parseColor("#0A7875"),
            Color.parseColor("#43780B"),
            Color.parseColor("#1E2478"),
            Color.parseColor("#784819")};
    private int[] angles = {60, 70, 50, 100, 80};
    private int currentAngle = 0;

    private Paint mPaint;
    private RectF mRectF;

    public PieChart(Context context) {
        super(context);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF = new RectF(getWidth() / 2f - RADIUS, getHeight() / 2f - RADIUS, getWidth() / 2f + RADIUS, getHeight() / 2f + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < colors.length; i++) {
            mPaint.setColor(colors[i]);
            if (i == PULLED_INDEX) {
                canvas.save();
                canvas.translate((float) Math.cos(Math.toRadians(currentAngle + angles[i] / 2)) * PULLED_OFFSET,
                        (float) Math.sin(Math.toRadians(currentAngle + angles[i] / 2)) * PULLED_OFFSET);
            }

            canvas.drawArc(mRectF, currentAngle, angles[i], true, mPaint);
            currentAngle += angles[i];

            if (i == PULLED_INDEX) {
                canvas.restore();
            }
        }
    }
}
