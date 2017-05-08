package com.hyh.www.common.widget.myview;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hyh.www.common.R;
import com.hyh.www.common.widget.myview.vo.Panel;
import com.hyh.www.common.widget.myview.vo.SesameItemModel;

import java.util.ArrayList;

/**
 * 作者：Denqs on 2017/5/8.
 */

public class ScalePanel extends View {
    private Paint panelPaint;
    private Paint linePaint;
    private Paint topPaint;
    private Paint panelTextPaint;
    private Paint picPaint;
    private Paint progressPaint;
    //进度条范围
    private RectF progressRectF;
    //刻度范围
    private RectF panelRectF;
    private int viewHeight;
    private int viewWidth;
    private int progressRaduis;
    private int progressStroke = 4;
    private int mTikeCount = 31;
    private int panelStroke = 18;
    private int mItemcount = 6;
    private int startAngle = 160;
    private int sweepAngle = 220;
    private PointF centerPoint = new PointF();
    private float progressSweepAngle = 1;
    //旋转的角度(0.34误差校准)
    private float rAngle = sweepAngle / mTikeCount + 0.34f;
    private float progressTotalSweepAngle;
    private ValueAnimator progressAnimator;
    private String sesameJiFen;
    private int startGradientColor = Color.parseColor("#f24a29");
    private int endGradientColor = Color.parseColor("#1170c1");

    //刻度参数
    private int total;
    private ArrayList<SesameItemModel> sesameItemModels;

    public ScalePanel(Context context) {
        this(context, null);
    }

    public ScalePanel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScalePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        obtainAttributes(context, attrs);
    }

    private void init() {
        panelPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        panelPaint.setColor(Color.argb(125, 255, 255, 255));
        panelPaint.setStyle(Paint.Style.STROKE);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(1);

        topPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        topPaint.setStyle(Paint.Style.STROKE);
        topPaint.setTextSize(40);
        topPaint.setColor(Color.WHITE);

        panelTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        panelTextPaint.setStyle(Paint.Style.STROKE);

        picPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        picPaint.setStyle(Paint.Style.STROKE);
        picPaint.setColor(Color.WHITE);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setColor(Color.WHITE);
        progressPaint.setStrokeWidth(progressStroke);

        sesameItemModels = getList();
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScalePanel);
        total = ta.getInt(R.styleable.ScalePanel_total, 0);
        ta.recycle();//回收资源
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (total != 0 && sesameItemModels != null && sesameItemModels.size() != 0) {
            viewWidth = w;
            viewHeight = h;
            progressRaduis = (w / 2) * 7 / 10;
            centerPoint.set(viewWidth / 2, viewHeight / 2);
            sesameJiFen = String.valueOf(350);
            getProgressAnimator();
        }
    }

    public void getProgressAnimator() {
        progressTotalSweepAngle = computeProgressAngle();
        Panel startPanel = new Panel();
        startPanel.setStartSweepAngle(1);
        startPanel.setStartSweepValue(350);

        Panel endPanel = new Panel();
        endPanel.setEndSweepAngle(progressTotalSweepAngle);
        endPanel.setEndSweepValue(total);

        progressAnimator = ValueAnimator.ofObject(new creditEvaluator(), startPanel, endPanel);
        progressAnimator.setDuration(5000);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Panel panel = (Panel) animation.getAnimatedValue();
                //更新进度值
                progressSweepAngle = panel.getSesameSweepAngle();
                sesameJiFen = String.valueOf(panel.getSesameSweepValue());
                invalidateView();
            }
        });

        postDelayed(new Runnable() {
            @Override
            public void run() {
                progressAnimator.start();
            }
        }, 1000);
    }

    /**
     * 计算用户信用分所占角度
     */
    private float computeProgressAngle() {
        ArrayList<SesameItemModel> list = sesameItemModels;
        int userTotal = total;
        float progressAngle = 0;
        for (int i = 0; i < list.size(); i++) {
            if (userTotal > list.get(i).getMax()) {
                progressAngle += mItemcount * rAngle;
                continue;
            }
            int blance = userTotal - list.get(i).getMin();
            float areaItem = (list.get(i).getMax() - list.get(i).getMin()) / mItemcount;
            progressAngle += (blance / areaItem) * rAngle;
            if (blance % areaItem != 0) {
                blance -= (blance / areaItem) * areaItem;
                float percent = (blance / areaItem);
                progressAngle += (int) (percent * rAngle);
            }
            break;
        }
        return progressAngle;
    }

    private class creditEvaluator implements TypeEvaluator {
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Panel resultPanel = new Panel();
            Panel startPanel = (Panel) startValue;
            Panel endPanel = (Panel) endValue;
            //开始扫描角度,从1度开始扫描
            float startSweepAngle = startPanel.getStartSweepAngle();
            //结束扫描的角度,为计算出来的用户信用分在仪表盘上扫描过的角度
            float endSweepAngle = endPanel.getEndSweepAngle();
            float sesameSweepAngle = startSweepAngle + fraction * (endSweepAngle - startSweepAngle);
            //计算出来进度条变化时变化的角度
            resultPanel.setSesameSweepAngle(sesameSweepAngle);
            //开始扫描的值,为起始刻度350
            float startSweepValue = startPanel.getStartSweepValue();
            //结束扫描的值,为用户的信用分
            float endSweepValue = endPanel.getEndSweepValue();
            //计算出进度条在变化的时候信用分的值
            float sesameSweepValue = startSweepValue + fraction * (endSweepValue - startSweepValue);
            resultPanel.setSesameSweepValue((int) sesameSweepValue);
            return resultPanel;

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (total != 0 && sesameItemModels != null && sesameItemModels.size() != 0) {
            drawBackground(canvas);
            drawPanel(canvas);
        }
    }

    /**
     * 绘制仪表盘
     */
    private void drawPanel(Canvas canvas) {
        panelPaint.setStrokeWidth(progressStroke);
        progressRectF = new RectF(centerPoint.x - progressRaduis, centerPoint.y - progressRaduis, centerPoint.x + progressRaduis, centerPoint.y + progressRaduis);
        canvas.drawArc(progressRectF, startAngle, sweepAngle, false, panelPaint);
        canvas.drawArc(progressRectF, startAngle, progressSweepAngle, false, progressPaint);

        panelPaint.setStrokeWidth(panelStroke);
        int panelRadius = progressRaduis * 9 / 10;
        panelRectF = new RectF(centerPoint.x - panelRadius, centerPoint.y - panelRadius, centerPoint.x + panelRadius, centerPoint.y + panelRadius);
        canvas.drawArc(panelRectF, startAngle, sweepAngle, false, panelPaint);
        canvas.save();
        //旋转-110度,即坐标系270度位置  即垂直方向,便于计算
        canvas.rotate(-110, centerPoint.x, centerPoint.y);
        drawLineAndText(canvas);
        canvas.restore();
        drawPanelText(canvas);
    }

    /**
     * 绘制仪表盘文本
     */
    private void drawPanelText(Canvas canvas) {
        float drawTextY, textSpace = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        Rect rect = new Rect();
        drawTextY = centerPoint.y - panelRectF.height() / 2 * 0.45f;

        String text = sesameJiFen;
        panelTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 70, getResources().getDisplayMetrics()));
        panelTextPaint.setColor(Color.WHITE);
        panelTextPaint.setFakeBoldText(true);
        panelTextPaint.getTextBounds(text, 0, text.length(), rect);
        drawTextY = drawTextY + rect.height() + textSpace;
        canvas.drawText(text, centerPoint.x - rect.width() / 2, drawTextY, panelTextPaint);
    }

    /**
     * 绘制仪表盘刻度以及刻度文字
     */
    private void drawLineAndText(Canvas canvas) {
        ArrayList<SesameItemModel> sesameItemdataModels = sesameItemModels;
        for (int i = 0; i < sesameItemdataModels.size(); i++) {
            SesameItemModel sesameItem = sesameItemdataModels.get(i);
            for (int j = 0; j < mItemcount; j++) {
                if (j == 0) {
                    //大刻度
                    linePaint.setColor(Color.parseColor("#ffffff"));
                    canvas.drawLine(centerPoint.x, panelRectF.top - panelStroke / 2, centerPoint.x, panelRectF.top + panelStroke / 2, linePaint);
                } else {
                    //小刻度
                    linePaint.setColor(Color.parseColor("#e5e5e5"));
                    canvas.drawLine(centerPoint.x, panelRectF.top - panelStroke / 2, centerPoint.x, panelRectF.top + panelStroke / 4, linePaint);
                }
                String itemMin = String.valueOf(sesameItem.getMin());
                String itemMax = String.valueOf(sesameItem.getMax());
                String itemType = sesameItem.getArea();
                Rect rect = new Rect();
                if (j == 0) {
                    linePaint.setTextSize(20);
                    linePaint.getTextBounds(itemMin, 0, itemMin.length(), rect);
                    canvas.drawText(itemMin, centerPoint.x - rect.width() / 2, panelRectF.top + panelStroke + 15, linePaint);
                }
                if (j == 3) {
                    linePaint.setTextSize(20);
                    linePaint.getTextBounds(itemType, 0, itemType.length(), rect);
                    canvas.drawText(itemType, centerPoint.x - rect.width() / 2, panelRectF.top + panelStroke + 15, linePaint);
                }
                canvas.rotate(rAngle, centerPoint.x, centerPoint.y);
                //补足最后一个大刻度
                if (i == sesameItemdataModels.size() - 1 && j == 5) {
                    //最后大刻度
                    linePaint.setColor(Color.parseColor("#ffffff"));
                    canvas.drawLine(centerPoint.x, panelRectF.top - panelStroke / 2, centerPoint.x, panelRectF.top + panelStroke / 2, linePaint);
                    //最后一个文本
                    linePaint.setTextSize(20);
                    linePaint.getTextBounds(itemMax, 0, itemMax.length(), rect);
                    canvas.drawText(itemMax, centerPoint.x - rect.width() / 2, panelRectF.top + panelStroke + 15, linePaint);
                }
            }
        }
    }

    private void drawBackground(Canvas canvas) {
        int startColor = computeGradientColor(startGradientColor, endGradientColor, progressSweepAngle, progressTotalSweepAngle);
        canvas.drawColor(startColor);
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

    /**
     * 根据当前进度条变化的角度与总角度计算两个颜色的变化期间的颜色值
     */
    private int computeGradientColor(int startColor, int endColor, float curAngle, float totalAngle) {
        int r, g, b;
        r = (int) (Color.red(startColor) + (Color.red(endColor) - Color.red(startColor)) * curAngle / totalAngle);
        g = (int) (Color.green(startColor) + (Color.green(endColor) - Color.green(startColor)) * curAngle / totalAngle);
        b = (int) (Color.blue(startColor) + (Color.blue(endColor) - Color.blue(startColor)) * curAngle / totalAngle);
        return Color.rgb(r, g, b);
    }

    //刻度表数据
    public ArrayList<SesameItemModel> getList() {
        ArrayList<SesameItemModel> sesameItemModels = new ArrayList<SesameItemModel>();

        SesameItemModel ItemModel350 = new SesameItemModel();
        ItemModel350.setArea("较差");
        ItemModel350.setMin(350);
        ItemModel350.setMax(550);
        sesameItemModels.add(ItemModel350);

        SesameItemModel ItemModel550 = new SesameItemModel();
        ItemModel550.setArea("中等");
        ItemModel550.setMin(550);
        ItemModel550.setMax(600);
        sesameItemModels.add(ItemModel550);

        SesameItemModel ItemModel600 = new SesameItemModel();
        ItemModel600.setArea("良好");
        ItemModel600.setMin(600);
        ItemModel600.setMax(650);
        sesameItemModels.add(ItemModel600);

        SesameItemModel ItemModel650 = new SesameItemModel();
        ItemModel650.setArea("优秀");
        ItemModel650.setMin(650);
        ItemModel650.setMax(700);
        sesameItemModels.add(ItemModel650);

        SesameItemModel ItemModel700 = new SesameItemModel();
        ItemModel700.setArea("较好");
        ItemModel700.setMin(700);
        ItemModel700.setMax(950);
        sesameItemModels.add(ItemModel700);
        return sesameItemModels;
    }
    public void setTotal(int total){
        if(total!=0){
            this.total=total;
            invalidateView();
            getProgressAnimator();
        }
    }
}
