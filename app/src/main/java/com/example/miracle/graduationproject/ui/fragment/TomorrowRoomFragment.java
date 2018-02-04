package com.example.miracle.graduationproject.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.example.miracle.graduationproject.R;
import com.library.activity.BaseFragment;
import com.library.adapter.CommenDelegateAdapter;
import com.library.adapter.RecyclerViewHolder;
import com.library.utils.glide.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Miracle on 2018/1/21 0021.
 */

public class TomorrowRoomFragment extends BaseFragment {
    @Bind(R.id.rv_tomorrow_room)
    RecyclerView rvTodayRoom;
    private CommenDelegateAdapter<String> tomorrowRoomAdapter;

    @Override
    protected int getViewId() {
        return R.layout.fragment_tomorrow_room;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initRecyclerView();
    }

    @Override
    protected void initListener() {

    }

    /**
     * 初始化房间列表
     */
    private void initRecyclerView() {
        final List<String> imageUrls = new ArrayList<>();
        imageUrls.add("http://imgsrc.baidu.com/imgad/pic/item/30adcbef76094b36a3f67270a9cc7cd98c109dc2.jpg");
        imageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516035003485&di=00da535057ef7791c0f2543483b4809f&imgtype=0&src=http%3A%2F%2Fimages.takungpao.com%2F2015%2F1029%2F20151029025635561.jpg");
        imageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516035003485&di=85ade4af6a9c4a82651a669104d88b4d&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F64380cd7912397ddff3daf395282b2b7d0a28742.jpg");
        imageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516035003485&di=00da535057ef7791c0f2543483b4809f&imgtype=0&src=http%3A%2F%2Fimages.takungpao.com%2F2015%2F1029%2F20151029025635561.jpg");

        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getActivity());
        rvTodayRoom.setLayoutManager(virtualLayoutManager);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        tomorrowRoomAdapter = new CommenDelegateAdapter<String>(getActivity(), R.layout.item_room_info, imageUrls, linearLayoutHelper, imageUrls.size()) {

            @Override
            public void convert(RecyclerViewHolder holder, String s, int position) {
                Glide.with(getActivity()).load(imageUrls.get(position)).into((ImageView) holder.getView(R.id.iv_room_bg));

            }

            @Override
            public void convert(RecyclerViewHolder holder, int position) {

            }
        };

        delegateAdapter.addAdapter(tomorrowRoomAdapter);
        rvTodayRoom.setAdapter(delegateAdapter);
    }
}
