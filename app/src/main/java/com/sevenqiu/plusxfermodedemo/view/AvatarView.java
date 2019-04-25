package com.sevenqiu.plusxfermodedemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sevenqiu.plusxfermodedemo.R;

import static com.sevenqiu.plusxfermodedemo.util.Util.dip2Pixl;

/**
 * @author Seven Qiu
 * @date 2019-04-25
 */
public class AvatarView extends View {
    private static final float RADIUS = dip2Pixl(100f);
    private static final float RADIUS_BOUNDS = dip2Pixl(110f);

    private Paint mPaint;
    private RectF mRectF;
    private RectF mBoundsRectF;
    private Bitmap mBitmap;
    private PorterDuffXfermode mPorterDuffXfermode;

    public AvatarView(Context context) {
        super(context);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = getBitmapBySize((int) RADIUS * 2);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF = new RectF(getWidth() / 2f - RADIUS, getHeight() / 2f - RADIUS, getWidth() / 2f + RADIUS, getHeight() / 2f + RADIUS);
        mBoundsRectF = new RectF(getWidth() / 2f - RADIUS_BOUNDS, getHeight() / 2f - RADIUS_BOUNDS,
                getWidth() / 2f + RADIUS_BOUNDS, getHeight() / 2f + RADIUS_BOUNDS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawOval(mBoundsRectF, mPaint);
        int saveLayer = canvas.saveLayer(mRectF, mPaint);
        canvas.drawOval(mRectF, mPaint);
        mPaint.setXfermode(mPorterDuffXfermode);
        canvas.drawBitmap(mBitmap, getWidth() / 2f - mBitmap.getWidth() / 2f, getHeight() / 2f - mBitmap.getHeight() / 2f, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
    }

    private Bitmap getBitmapBySize(int size) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.tang, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = size;
        return BitmapFactory.decodeResource(getResources(), R.drawable.tang, options);
    }
}
