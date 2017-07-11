package com.hyh.www.common.widget.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hyh.www.common.R;

/**
 * 作者：Denqs on 2017/4/21.
 */

public class PeopleView extends TextView {
    String name;
    int age;
    String careers;

    public PeopleView(Context context) {
        super(context);
    }

    public PeopleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        obtainAttributes(context, attrs);
    }

    public PeopleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PeopleView);
        name = ta.getString(R.styleable.PeopleView_name);
        age = ta.getInt(R.styleable.PeopleView_age, 15);
        Boolean adult = ta.getBoolean(R.styleable.PeopleView_adult, false);
        String str_adult = getAdultStatus(adult);
        int weight = ta.getInt(R.styleable.PeopleView_weight, 1);// 默认是中等身材，属性为:1
        String str_weight = getWeightStatus(weight);//获得肥胖属性
        String careers = ta.getString(R.styleable.PeopleView_careers);

        ta.recycle();//回收资源
        setText("姓名：" + name + "\n年龄：" + age + "\n是否成年：" + str_adult + "\n职业:" + careers
                + "\n体形：" + str_weight);//给自定义的控件赋值
    }

    public void setName(String name) {
        this.name = name;
        invalidateView();
    }

    public void setAge(int age) {
        this.age = age;
        invalidateView();
    }

    public void setCareers(String careers) {
        this.careers = careers;
        invalidateView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setText("姓名：" + name + "\n年龄：" + age + "\n职业:" + careers);
    }

    /**
     * 根据传入的值判断是否成年
     */
    private String getAdultStatus(Boolean adult) {
        String str_adult = "未成年";
        if (adult) {
            str_adult = "成年";
        }
        return str_adult;
    }

    /**
     * 根据传入的值判断肥胖状态
     */
    private String getWeightStatus(int weight) {
        String str_weight = "中等";
        switch (weight) {
            case 0:
                str_weight = "瘦";
                break;
            case 1:
                str_weight = "中等";
                break;
            case 2:
                str_weight = "肥胖";
                break;
            default:
                break;
        }
        return str_weight;
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
}
