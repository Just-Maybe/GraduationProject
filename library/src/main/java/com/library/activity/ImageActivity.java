package com.library.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.library.R;
import com.library.utils.glide.GlideUtil;
import com.library.widget.photoview.PhotoView;
import com.library.widget.photoview.PhotoViewAttacher;

import java.util.ArrayList;

/**
 * Created by longbh on 16/6/23.
 */
public class ImageActivity extends BaseActivity {

    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    private static final String STATE_POSITION = "STATE_POSITION";
    ArrayList<String> urls;
    private ViewPager mPager;
    private int pagerPosition;
    private TextView indicator;

    /**
     * 查看图片
     *
     * @param context
     * @param position
     * @param urls
     */
    public static void open(Context context, int position, ArrayList<String> urls) {
        Intent i = new Intent(context, ImageActivity.class);
        i.putExtra(EXTRA_IMAGE_INDEX, position);
        i.putStringArrayListExtra(EXTRA_IMAGE_URLS, urls);
        context.startActivity(i);
    }

    @Override
    protected void onGetBundle(Bundle bundle) {
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_image_show;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mPager = (ViewPager) findViewById(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        indicator = (TextView) findViewById(R.id.indicator);
        CharSequence text = 1 + "/" + mPager.getAdapter().getCount();
        indicator.setText(text);
        mPager.setCurrentItem(pagerPosition);
    }

    @Override
    protected void initListener() {
        // 更新下标
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = arg0 + 1 + "/" + mPager.getAdapter().getCount();
                indicator.setText(text);
            }
        });
    }

    public static class ImageDetailFragment extends Fragment {
        private String mImageUrl;
        private PhotoView mImageView;
        private ProgressBar progressBar;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View v = inflater.inflate(R.layout.fragment_image_detail, container, false);
            mImageView = (PhotoView) v.findViewById(R.id.image);
            mImageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    getActivity().finish();
                }
            });
            progressBar = (ProgressBar) v.findViewById(R.id.loading);
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            GlideUtil.loadPicture(mImageUrl, new GlideDrawableImageViewTarget(mImageView) {
                @Override
                public void onLoadStarted(Drawable placeholder) {
                    super.onLoadStarted(placeholder);
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    view.setImageDrawable(errorDrawable);
                    progressBar.setVisibility(View.GONE);
                }

                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                    mImageView.setImageDrawable(resource);
                    progressBar.setVisibility(View.GONE);
                }
            }, R.drawable.default_image);
        }
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            final ImageDetailFragment f = new ImageDetailFragment();
            final Bundle args = new Bundle();
            args.putString("url", url);
            f.setArguments(args);
            return f;
        }
    }

}
