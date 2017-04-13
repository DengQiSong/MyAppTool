package com.hyh.www.common.module.recycler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hyh.www.common.R;
import com.hyh.www.common.app.BaseActivity;
import com.hyh.www.common.module.vo.RecyclerViewBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Denqs on 2017/4/13.
 */

public class LeftActivity extends BaseActivity {
    private static final String TAG = "LeftActivity";
    @BindView(R.id.rv_left)
    RecyclerView rv_left;

    protected RecyclerViewAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        mAdapter = new RecyclerViewAdapter(getData(),this);
        rv_left.setAdapter(mAdapter);
        rv_left.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        LeftSnapHelper mLeftSnapHelper = new LeftSnapHelper();
        mLeftSnapHelper.attachToRecyclerView(rv_left);
    }

    private String[] strImgs = {"http://image.mifengkong.cn/qianba/organization_id_45/57c3fa36f38c1957331842.png",
            "http://image.mifengkong.cn/qianba/organization_id_58/585a26cada217270984611.png",
            "http://image.mifengkong.cn/qianba/organization_id_/57a1ccec91fe4958589462.png",
            "http://image.mifengkong.cn/qianba/organization_id_36/579f00415e54f658392338.png",
            "http://image.mifengkong.cn/qianba/organization_id_45/57c3fa36f38c1957331842.png",
            "http://image.mifengkong.cn/qianba/organization_id_58/585a26cada217270984611.png",
            "http://image.mifengkong.cn/qianba/organization_id_/57a1ccec91fe4958589462.png",
            "http://image.mifengkong.cn/qianba/organization_id_36/579f00415e54f658392338.png"};

    private String[] names = {"张三", "李四", "王五","孙六", "路人甲", "路人乙", "路人丙", "路人丁"};

    public List<RecyclerViewBean> getData() {
        List<RecyclerViewBean> list = new ArrayList<>();
        RecyclerViewBean data;
        for (int i = 0; i < 16; i++) {
            data = new RecyclerViewBean();
            data.setId(i);
            data.setImageUrl(strImgs[i % 8]);
            data.setName(names[i % 8]);
            list.add(data);
        }
        return list;
    }
}
