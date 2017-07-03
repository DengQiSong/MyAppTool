package com.hyh.www.common.module.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.hyh.www.common.MainActivity;
import com.hyh.www.common.R;
import com.hyh.www.common.app.BaseApplication;
import com.hyh.www.common.app.BaseFragment;
import com.hyh.www.common.module.CollapsingToolActivity;
import com.hyh.www.common.module.MapViewTest;
import com.hyh.www.common.module.recycler.LeftActivity;
import com.hyh.www.common.module.service.TimingService;
import com.hyh.www.common.widget.banner.BannerBean;
import com.hyh.www.common.widget.banner.BannerHolderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Denqs on 2017/3/6.
 */

public class MainFragment extends BaseFragment implements OnItemClickListener {
    private static final String TAG = "MainFragment";
    private ConvenientBanner<BannerBean> convenientBanner;//顶部广告栏控件
    private String[] images = {"http://img6.web07.cn/UPics/Bizhi/2016/0913/121474130955191.jpg", "http://pic17.nipic.com/20111122/6759425_152002413138_2.jpg", "http://img3.duitang.com/uploads/item/201510/10/20151010211325_ZdA4R.jpeg", "http://pic.pp3.cn/uploads//201603/20160321008.jpg"};
    private String[] images_flag = {"最热风景", "此处风景独好", "最美风景啊", "这个真不错"};
    private List<BannerBean> bannerList;

    @BindView(R.id.am_btn_left)
    Button amBtnLeft;
    @BindView(R.id.btn_start)
    Button btn_start;
    @BindView(R.id.btn_end)
    Button btn_end;

    public static MainFragment newInstance(String fragConent) {
        Bundle args = new Bundle();
        args.putString(MainActivity.ARGS_NAVI_BTN_NAME, fragConent);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    protected void onViewReallyCreated(View view) {
        super.onViewReallyCreated(view);
        ButterKnife.bind(this, view);
        initView();
    }

    public void initView() {
        bannerList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            BannerBean bannerBean = new BannerBean();
            bannerBean.setImageUrl(images[i]);
            bannerBean.setImageName(images_flag[i]);
            bannerList.add(bannerBean);
        }
        convenientBanner = findView(R.id.convenientBanner);
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new BannerHolderView();
            }
        }, bannerList).setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                .setOnItemClickListener(this);

        findView(R.id.but_tool).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CollapsingToolActivity.class));
            }
        });
        findView(R.id.but_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MapViewTest.class));
            }
        });
        amBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LeftActivity.class));
            }
        });
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlarm();
            }
        });
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endAlarm();
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        if (bannerList != null) {
            Log.e(TAG, bannerList.get(position).getImageName());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //开启自动翻页
        convenientBanner.startTurning(5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    PendingIntent collectSender;
    int anHour = 5 * 1000; //毫秒数

    private void startAlarm() {
        Intent collectIntent = new Intent(getActivity(), TimingService.class);
        getActivity().startService(collectIntent);
        if (collectSender == null) {
            collectSender = PendingIntent.getService(getContext(), 0, collectIntent, 0);
        }
        AlarmManager am = (AlarmManager) BaseApplication.getInstances().getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), anHour, collectSender);
    }

    private void endAlarm() {
        Intent collectIntent = new Intent(getActivity(), TimingService.class);
        if (collectSender == null) {
            collectSender = PendingIntent.getService(getContext(), 0, collectIntent, 0);
        }
        AlarmManager am = (AlarmManager) BaseApplication.getInstances().getSystemService(Context.ALARM_SERVICE);
        am.cancel(collectSender);
        getActivity().stopService(collectIntent);
    }

}
