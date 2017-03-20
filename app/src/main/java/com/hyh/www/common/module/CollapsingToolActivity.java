package com.hyh.www.common.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyh.www.common.R;
import com.hyh.www.common.app.BaseActivity;
import com.hyh.www.common.widget.imageloader.ImageTool;

/**
 * 作者：Denqs on 2017/3/20.
 */

public class CollapsingToolActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsingtool);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout toolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView imageView=(ImageView) findViewById(R.id.iv);
        TextView textView=(TextView) findViewById(R.id.tv_text_01);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbarLayout.setTitle("这是标题");
        ImageTool.loadUrl(this,imageView,"http://imgsrc.baidu.com/forum/w%3D580/sign=952962efa84bd11304cdb73a6aaea488/1c13a3ec8a1363273f6c44af948fa0ec0afac7f1.jpg");
        textView.setText(getTest("测试一下！！就看看行不行再说！！！"));
    }
    public String getTest(String str){
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<50;i++){
            stringBuilder.append(str+"\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
