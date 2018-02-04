package com.example.miracle.graduationproject.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.example.miracle.graduationproject.R;
import com.example.miracle.graduationproject.api.Http;
import com.example.miracle.graduationproject.api.NetWorkRequest;
import com.example.miracle.graduationproject.dto.Comment;
import com.example.miracle.graduationproject.listener.IReponseListener;
import com.example.miracle.graduationproject.utils.AndroidBug5497Workaround;
import com.example.miracle.graduationproject.utils.HawkKey;
import com.library.activity.BaseActivity;
import com.library.adapter.CommenDelegateAdapter;
import com.library.adapter.RecyclerViewHolder;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Miracle on 2018/2/3 0003.
 */

public class CommentActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.rv_comment)
    RecyclerView rvComment;
    @Bind(R.id.et_comment)
    EditText etComment;
    @Bind(R.id.tv_post_comment)
    TextView tvPostComment;
    @Bind(R.id.ll_post_comment)
    LinearLayout llPostComment;
    private DelegateAdapter delegateAdapter;
    private CommenDelegateAdapter<Comment> commentAdapter;
    private int hotelId = 0;

    @Override
    protected void onGetBundle(Bundle bundle) {
        if (bundle != null) {
            hotelId = bundle.getInt(HotelDetailActivity.HOTEL_ID);
        }
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvTitle.setText("评论");
        ivRight.setImageResource(R.drawable.icon_edit);
        initRecyclerView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        NetWorkRequest.getCommentByHotelId(this, hotelId, 1, new IReponseListener<List<Comment>>() {
            @Override
            public void onSuccess(List<Comment> data) {
                if (data != null && data.size() > 0) {
                    commentAdapter.upDateData(data);
                }
            }

            @Override
            public void onFail(String msg, int statusCode) {

            }
        });
    }

    /**
     * 初始化列表
     */
    private void initRecyclerView() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        rvComment.setLayoutManager(virtualLayoutManager);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        commentAdapter = new CommenDelegateAdapter<Comment>(this, R.layout.item_comment, null, linearLayoutHelper, 0) {

            @Override
            public void convert(RecyclerViewHolder holder, Comment comment, int position) {
                holder.setText(R.id.tv_comment_name, comment.getName());
                holder.setText(R.id.tv_comment_content, comment.getContent());
                holder.setText(R.id.tv_comment_time, comment.getCreateTime());
                Log.d("TAG", "convert: " + comment.getImgUrl());
                Glide.with(CommentActivity.this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516035003485&di=00da535057ef7791c0f2543483b4809f&imgtype=0&src=http%3A%2F%2Fimages.takungpao.com%2F2015%2F1029%2F20151029025635561.jpg").into((ImageView) holder.getView(R.id.iv_comment_head));
//                holder.setSquareImageLoad(R.id.iv_comment_head,comment.getImgUrl());
            }

            @Override
            public void convert(RecyclerViewHolder holder, int position) {

            }
        };
        delegateAdapter.addAdapter(commentAdapter);
        rvComment.setAdapter(delegateAdapter);

    }

    @Override
    protected void initListener() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (llPostComment.getVisibility() == View.VISIBLE) {
                    llPostComment.setVisibility(View.GONE);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @OnClick({R.id.iv_back, R.id.iv_right, R.id.tv_post_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_right:
                llPostComment.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_post_comment:
                postCommtent();
                break;
        }
    }

    /**
     * 发送评论
     */
    private void postCommtent() {
        NetWorkRequest.postComment(this, etComment.getText().toString(), Hawk.get(HawkKey.USER_ID, 0), hotelId, new IReponseListener<Void>() {
            @Override
            public void onSuccess(Void data) {
                etComment.setText("");
                showToast("发表成功");
                initData();

            }

            @Override
            public void onFail(String msg, int statusCode) {
                showToast(msg);
            }
        });
    }
}
