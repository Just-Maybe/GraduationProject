package com.example.miracle.graduationproject.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.example.miracle.graduationproject.R;
import com.example.miracle.graduationproject.utils.DialogUtil;
import com.library.activity.BaseActivity;
import com.library.adapter.CommenDelegateAdapter;
import com.library.adapter.RecyclerViewHolder;
import com.library.utils.DateUtil;
import com.library.utils.LogUtil;
import com.library.utils.glide.GlideImageLoader;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Miracle on 2018/1/13 0013.
 */

public class MainActivity extends BaseActivity {
    @Bind(R.id.rv_main)
    RecyclerView rvMain;
    private Dialog calendarDialog;
    List<Date> selectedDates = new ArrayList<>();
    private onSelectedDatesListener listener;
    private String quitTime;
    private String enterTime;
    private EditText etSearch;

    public void setListener(onSelectedDatesListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
//        tvTitle.setText("华软大酒店");
//        ivBack.setImageResource(R.drawable.icon_mine);
//        ivRight.setImageResource(R.drawable.icon_search);
        initRecyclerView();
    }

    /**
     * 初始化 主页列表
     */
    private void initRecyclerView() {
        final List<String> imageUrls = new ArrayList<>();
        imageUrls.add("http://imgsrc.baidu.com/imgad/pic/item/30adcbef76094b36a3f67270a9cc7cd98c109dc2.jpg");
        imageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516035003485&di=00da535057ef7791c0f2543483b4809f&imgtype=0&src=http%3A%2F%2Fimages.takungpao.com%2F2015%2F1029%2F20151029025635561.jpg");
        imageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516035003485&di=85ade4af6a9c4a82651a669104d88b4d&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F64380cd7912397ddff3daf395282b2b7d0a28742.jpg");
        imageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516035003485&di=00da535057ef7791c0f2543483b4809f&imgtype=0&src=http%3A%2F%2Fimages.takungpao.com%2F2015%2F1029%2F20151029025635561.jpg");

        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);
        rvMain.setLayoutManager(virtualLayoutManager);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        LinearLayoutHelper bannerLayoutHelper = new LinearLayoutHelper();
        CommenDelegateAdapter<String> bannerAdapter = new CommenDelegateAdapter<String>(this, R.layout.item_banner_layout, imageUrls, bannerLayoutHelper, 1) {
            @Override
            public void convert(RecyclerViewHolder holder, String s, int position) {
                Banner banner = holder.getView(R.id.banner);
                LogUtil.d("banner", banner == null);
                banner.setImageLoader(new GlideImageLoader());
                banner.setImages(imageUrls);
                banner.start();
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        showToast("positioin" + position);
                        Bundle bundle = new Bundle();
                        bundle.putInt(HotelDetailActivity.HOTEL_ID, 1);
                        startActivity(bundle, HotelDetailActivity.class);
                    }
                });
            }

            @Override
            public void convert(RecyclerViewHolder holder, int position) {

            }
        };

        /**
         * 查询酒店 Adapter
         */
        LinearLayoutHelper searchHeplper = new LinearLayoutHelper(30, 1);
        CommenDelegateAdapter<String> searchAdapter = new CommenDelegateAdapter<String>(this, R.layout.item_search_layout, imageUrls, searchHeplper, 1) {
            @Override
            public void convert(final RecyclerViewHolder holder, String s, int position) {
                holder.setOnClickListener(R.id.rl_live_time, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calendarDialog = DialogUtil.createDialog(MainActivity.this, R.layout.calendar_dialog, R.style.ActionButtomDialogStyle, new DialogUtil.onDialogViewListener() {
                            @Override
                            public void DialogViewListener(View view) {
                                final CalendarPickerView calendar = view.findViewById(R.id.calendar_view);
                                Calendar nextYear = Calendar.getInstance();
                                nextYear.add(Calendar.MONTH, 5);

                                Calendar lastYear = Calendar.getInstance();
                                lastYear.add(Calendar.MONTH, -5);
                                calendar.init(lastYear.getTime(), nextYear.getTime()) //
                                        .inMode(CalendarPickerView.SelectionMode.RANGE) //
                                        .withSelectedDate(new Date());

                                ArrayList<Integer> list = new ArrayList<>();
                                list.add(1);
                                calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
                                    @Override
                                    public void onDateSelected(Date date) {
                                        selectedDates = calendar.getSelectedDates();
                                        if (selectedDates.size() > 1) {
                                            calendarDialog.dismiss();
                                            listener.onSeleted();
                                        }
                                    }

                                    @Override
                                    public void onDateUnselected(Date date) {

                                    }
                                });
                                calendar.deactivateDates(list);
                            }
                        });

                        calendarDialog.show();
                    }
                });
                setListener(new onSelectedDatesListener() {
                    @Override
                    public void onSeleted() {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        if (selectedDates.size() > 0 && selectedDates.get(0) != null) {
                            enterTime = formatter.format(selectedDates.get(0).getTime());
                            holder.setText(R.id.tv_enter_time, enterTime);
                        }
                        if (selectedDates.size() > 1 && selectedDates.get(selectedDates.size() - 1) != null) {
                            quitTime = formatter.format(selectedDates.get(selectedDates.size() - 1).getTime());

                            holder.setText(R.id.tv_quit_time, quitTime);
                            int days = DateUtil.getDayDistance(selectedDates.get(0).getTime(), selectedDates.get(selectedDates.size() - 1).getTime());
                            holder.setText(R.id.tv_total_days, "共" + days + "晚");
                        }

                    }
                });
                etSearch = holder.getView(R.id.et_search_content);

                holder.setOnClickListener(R.id.tv_book, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(SearchResultActivity.ENTER_TIME, enterTime);
                        bundle.putString(SearchResultActivity.QUIT_TIME, quitTime);
                        bundle.putString(SearchResultActivity.SEARCH_CONTENT, etSearch.getText().toString());
                        startActivity(bundle, SearchResultActivity.class);
                    }
                });

            }

            @Override
            public void convert(RecyclerViewHolder holder, int position) {

            }
        };
        /**
         *  房间信息   我的订单   我的信息
         */
        LinearLayoutHelper functionHeplper = new LinearLayoutHelper(80, 1);
        CommenDelegateAdapter<String> functionAdapter = new CommenDelegateAdapter<String>(this, R.layout.item_function_layout, imageUrls, functionHeplper, 1) {
            @Override
            public void convert(RecyclerViewHolder holder, String s, int position) {
                holder.setOnClickListener(R.id.btn_order, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(null, OrderActivity.class);
                    }
                });

                holder.setOnClickListener(R.id.btn_room, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(null, RoomInfoActivity.class);
                    }
                });
            }

            @Override
            public void convert(RecyclerViewHolder holder, int position) {

            }
        };

        /**
         * 推荐房间
         */
        LinearLayoutHelper recommendHeplper = new LinearLayoutHelper(0, imageUrls.size());
        CommenDelegateAdapter<String> recommendAdapter = new CommenDelegateAdapter<String>(this, R.layout.item_recommend_layout, imageUrls, recommendHeplper, imageUrls.size()) {
            @Override
            public void convert(RecyclerViewHolder holder, String s, int position) {
                Glide.with(MainActivity.this).load(mDatas.get(position)).into((ImageView) holder.getView(R.id.iv_recommend_img));
                holder.setText(R.id.tv_recommend, "推荐文字" + position);
            }

            @Override
            public void convert(RecyclerViewHolder holder, int position) {

            }
        };

        delegateAdapter.addAdapter(bannerAdapter);
        delegateAdapter.addAdapter(searchAdapter);
        delegateAdapter.addAdapter(functionAdapter);
        delegateAdapter.addAdapter(recommendAdapter);
        rvMain.setAdapter(delegateAdapter);
    }

    @Override
    protected void initListener() {

    }

    interface onSelectedDatesListener {
        void onSeleted();
    }
}
