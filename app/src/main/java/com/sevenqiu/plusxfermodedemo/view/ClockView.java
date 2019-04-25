package com.sevenqiu.plusxfermodedemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static com.sevenqiu.plusxfermodedemo.util.Util.dip2Pixl;

/**
 * @author Seven Qiu
 * @date 2019-04-25
 */
public class ClockView extends View {
    private static final String TAG = ClockView.class.getSimpleName();

    private static final float RADIUS = dip2Pixl(100f);
    private static final float ANGLE = 120;
    private static final float LENGTH = 300;


    private Paint mPaint;
    private RectF mRectF;
    private Path mPath;
    private Path mDash;
    private PathMeasure mPathMeasure;
    private PathDashPathEffect mDashPathEffect;
    private Point mMiddlePoint;

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(dip2Pixl(2));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPath = new Path();
        mDash = new Path();
        mDash.addRect(0, 0, dip2Pixl(2), dip2Pixl(10), Path.Direction.CW);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMiddlePoint = new Point(getWidth() / 2, getHeight() / 2);
        mRectF = new RectF(mMiddlePoint.x - RADIUS, mMiddlePoint.y - RADIUS, mMiddlePoint.x + RADIUS, mMiddlePoint.y + RADIUS);

        mPath.addArc(mRectF, 90 + ANGLE / 2, 360 - ANGLE);

        mPathMeasure = new PathMeasure(mPath, false);
        mDashPathEffect = new PathDashPathEffect(mDash, (mPathMeasure.getLength() - dip2Pixl(2)) / 20, 0, PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(mRectF, 90 + ANGLE / 2, 360 - ANGLE, false, mPaint);

        mPaint.setPathEffect(mDashPathEffect);
        canvas.drawArc(mRectF, 90 + ANGLE / 2, 360 - ANGLE, false, mPaint);
        mPaint.setPathEffect(null);

        canvas.drawLine(mMiddlePoint.x, mMiddlePoint.y,
                mMiddlePoint.x + (float) Math.cos(Math.toRadians(getAngleByMark(3))) * LENGTH,
                mMiddlePoint.y + (float) Math.sin(Math.toRadians(getAngleByMark(3))) * LENGTH,
                mPaint);
    }

    private float getAngleByMark(int mark) {
        return 90 + ANGLE / 2 + (360 - ANGLE) / 20 * mark;
    }

}
