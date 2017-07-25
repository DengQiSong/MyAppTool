package com.dqs.example.widgetlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dengqs on 2017/7/25 0025.
 * 点击下拉的选择控件
 */

public class DownMenuView extends LinearLayout {


    private TextView title;
    private ImageView imageView;
    private View view;
    private PopupWindow popMenu;
    private ListView popListView;

    private int mWidth, mHeight;
    private int textSize;
    private int textSelected, titleColor;

    private DownMenuAdapter menuAdapter;
    private List<String> Data = new ArrayList<>();
    private OnClickItemDownMenu onClickItemDownMenu;

    public DownMenuView(Context context) {
        this(context, null);
    }

    public DownMenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.downmenu, this);
        initView(context);
        obtainAttributes(context,attrs);
        viewClick();
    }

    private void initView(final Context context) {
        title = (TextView) findViewById(R.id.tv_title);
        imageView = (ImageView) findViewById(R.id.iv_icon);
        View contentView = View.inflate(context, R.layout.down_popmenu_list, null);
        popMenu = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popMenu.setOutsideTouchable(true);
        popMenu.setBackgroundDrawable(new BitmapDrawable());
        popMenu.setFocusable(true);
        popMenu.setAnimationStyle(R.style.popwin_anim_style);

        contentView.findViewById(R.id.popwin_supplier_list_bottom)
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        popMenu.dismiss();
                    }
                });
        popListView = (ListView) contentView.findViewById(R.id.popwin_supplier_list_lv);
        menuAdapter = new DownMenuAdapter(context);
        setBackgroundColor(getResources().getColor(R.color.white));
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DowndMenu);
        textSize = ta.getInt(R.styleable.DowndMenu_titleSize, 15);
        textSelected = ta.getColor(R.styleable.DowndMenu_titleSelected, Color.parseColor("#39ac69"));
        titleColor = ta.getColor(R.styleable.DowndMenu_titleColor, Color.parseColor("#5a5959"));
        ta.recycle();//回收资源
        title.setTextSize(textSize);
    }

    private void viewClick() {
        popMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                title.setTextColor(titleColor);
                imageView.setImageResource(R.drawable.icon_arrow_down);
            }
        });
        popListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popMenu.dismiss();
                if (onClickItemDownMenu != null) {
                    onClickItemDownMenu.onItemClick(Data.get(position));
                }
            }
        });
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setTextColor(textSelected);
                imageView.setImageResource(R.drawable.icon_arrow_uo);
                popListView.setAdapter(menuAdapter);
                popMenu.showAsDropDown(DownMenuView.this, 0, 2);
            }
        });
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


    public interface OnClickItemDownMenu {
        void onItemClick(String currentProduct);
    }

    /**
     * ********************对外方法*******************
     */

    public void setOnClickDownMenuItem(@Nullable OnClickItemDownMenu onClickItemDownMenu) {
        this.onClickItemDownMenu = onClickItemDownMenu;
    }

    public void setTitle(String str) {
        this.title.setText(str);
        invalidateView();
    }

    public void setData(List<String> data) {
        if (Data != null && Data.size() > 0) {
            Data.clear();
        }
        if (data != null && data.size() > 0) {
            Data.addAll(data);
        } else {
            Toast.makeText(DownMenuView.this.getContext(), "数据为空", Toast.LENGTH_LONG).show();
        }
        menuAdapter.notifyDataSetChanged();
    }


    private class DownMenuAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        Context context;

        DownMenuAdapter(Context context) {
            this.context = context;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return Data.size();
        }

        @Override
        public Object getItem(int position) {
            return Data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GroupHold groupHold;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_down_menu, parent, false);
                groupHold = new GroupHold(convertView);
                convertView.setTag(groupHold);
            } else {
                groupHold = (GroupHold) convertView.getTag();
            }
            groupHold.list_popup_tv.setText(Data.get(position));
            return convertView;
        }

        class GroupHold {
            TextView list_popup_tv;

            GroupHold(View view) {
                this.list_popup_tv = (TextView) view.findViewById(R.id.list_popup_tv);
            }
        }

    }
}
