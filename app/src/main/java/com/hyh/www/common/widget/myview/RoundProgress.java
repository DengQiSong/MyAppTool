package com.hyh.www.common.widget.myview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.www.common.R;

/**
 * 作者：Denqs on 2017/5/17.
 * 自定义的进度条
 */

public class RoundProgress extends View {
    /**
     * 进度条颜色
     */
    private int colors = Color.RED;
    /**
     * 进度条最大值
     */
    private float maxCount;
    /**
     * 进度条当前值
     */
    private float currentCount;
    /**
     * 画笔
     */
    private ValueAnimator progressAnimator;
    private Paint mPaint;
    private int mWidth, mHeight, defHeight = dipToPx(10);
    private int mStroke = 4;

    public RoundProgress(Context context) {
        this(context, null);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
        maxCount = ta.getInt(R.styleable.RoundProgress_max, 0);
        currentCount = ta.getInt(R.styleable.RoundProgress_current, 0);
        colors = ta.getColor(R.styleable.RoundProgress_colors, Color.RED);
        ta.recycle();//回收资源
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        MprogressAnimator();
    }

    //进度动画
    public void MprogressAnimator() {
        progressAnimator = ValueAnimator.ofFloat(0, currentCount);
        progressAnimator.setDuration(3000);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                //更新进度值
                currentCount = currentValue;
                invalidateView();
            }
        });

        postDelayed(new Runnable() {
            @Override
            public void run() {
                progressAnimator.start();
            }
        }, 100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawBottomText(canvas);
    }

    //进度
    private void drawBackground(Canvas canvas) {
        int round = mHeight / 2;
        mPaint.setColor(Color.WHITE);
        RectF rectBg = new RectF(0, 0, mWidth, defHeight);
        canvas.drawRoundRect(rectBg, round, round, mPaint);
        mPaint.setColor(Color.YELLOW);
        RectF rectBlackBg = new RectF(mStroke, mStroke, mWidth - mStroke, defHeight - mStroke);
        canvas.drawRoundRect(rectBlackBg, round, round, mPaint);

        float section = currentCount / maxCount;
        RectF rectProgressBg = new RectF(mStroke, mStroke, (mWidth - mStroke) * section, defHeight - mStroke);
        if (section != 0.0f) {
            mPaint.setColor(colors);
        } else {
            mPaint.setColor(Color.TRANSPARENT);
        }
        canvas.drawRoundRect(rectProgressBg, round, round, mPaint);
    }

    //文字
    private void drawBottomText(Canvas canvas) {
        String testString = (int) currentCount + "/" + (int) maxCount;
        Paint mPaint = new Paint();
        mPaint.setStrokeWidth(3);
        mPaint.setTextSize(40);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.LEFT);
        Rect bounds = new Rect();
        mPaint.getTextBounds(testString, 0, testString.length(), bounds);
        int baseLineX = getMeasuredWidth() / 2 - bounds.width() / 2;
        int baseline = defHeight * 2 + dipToPx(2);
        canvas.drawText(testString, baseLineX, baseline, mPaint);
    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /***
     * 设置最大的进度值
     * @param maxCount
     */
    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
        invalidateView();
    }

    /***
     * 设置当前的进度值
     * @param currentCount
     */
    public void setCurrentCount(float currentCount) {
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
        invalidateView();
    }

    public float getMaxCount() {
        return maxCount;
    }

    public float getCurrentCount() {
        return currentCount;
    }

    private void invalidateView() {
        //判断当前线程
        if (Looper.getMainLooper() == Looper.myLooper()) {
            //UI线程则
            invalidate();
        } else {
            //非UI则
            postInvalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(25);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }
}
