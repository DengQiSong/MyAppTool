package com.hyh.www.common.module;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.hyh.www.common.R;
import com.hyh.www.common.app.BaseActivity;

/**
 * 作者：Denqs on 2017/3/20.
 */

public class MapViewTest extends BaseActivity implements View.OnClickListener {
    private BottomSheetBehavior<View> behavior;
    private FloatingActionButton fab;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapview);

        setOnClickListener(this, R.id.but_okView);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        fab = (FloatingActionButton) findViewById(R.id.floating_but);
        if (bottomSheet != null) {
            behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.setHideable(false);
        }
        textView = (TextView) findViewById(R.id.tv_text_01);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.e("有没有触发。。"," onStateChanged 试试"+newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.e("有没有触发。。"," onSlide 试试"+slideOffset);
                if(slideOffset>0.5){
                    fab.setVisibility(View.VISIBLE);
                }else if(slideOffset<0.4){
                    fab.setVisibility(View.GONE);
                }
            }
        });
    }

    public String getTest(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            stringBuilder.append(str + "\n");
        }
        return stringBuilder.toString();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_okView:
                textView.setText(getTest("测试一下！！就看看行不行再说！！！"));
                    int state = behavior.getState();
                    if (state != BottomSheetBehavior.STATE_EXPANDED) {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }

                break;
        }
    }

}
