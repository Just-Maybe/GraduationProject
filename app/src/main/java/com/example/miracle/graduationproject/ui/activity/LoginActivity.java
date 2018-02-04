package com.example.miracle.graduationproject.ui.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.miracle.graduationproject.R;
import com.example.miracle.graduationproject.api.NetWorkRequest;
import com.example.miracle.graduationproject.listener.IReponseListener;
import com.example.miracle.graduationproject.utils.HawkKey;
import com.library.activity.BaseActivity;
import com.library.utils.StringUtil;
import com.orhanobut.hawk.Hawk;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Miracle on 2018/1/25 0025.
 */

public class LoginActivity extends BaseActivity {
    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.tv_login)
    ImageView tvLogin;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @Bind(R.id.iv_bg)
    ImageView ivBg;
    private static final int LOGIN = 290;
    private static final int REGISTER = 111;
    private static int status = LOGIN;

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        initBtnGoAnimation();
        Glide.with(this).load(R.drawable.login_bg).into(ivBg);
    }

    /**
     *
     */
    private void initView() {
        if (status == LOGIN) {
            etConfirmPassword.setVisibility(View.GONE);
        } else {
            etConfirmPassword.setVisibility(View.VISIBLE);
        }
        if (!StringUtil.isEmpty(Hawk.get(HawkKey.USERNAME, ""))) {
            etUsername.setText(Hawk.get(HawkKey.USERNAME, ""));
        }
    }

    private void initBtnGoAnimation() {
        tvLogin.animate().rotation(360).setInterpolator(new AccelerateInterpolator()).setDuration(2000).start();
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.tv_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                if (status == LOGIN) {
                    login();
                    break;
                }
                register();

                break;
            case R.id.tv_register:
                if (status == LOGIN) {
                    status = REGISTER;
                    etConfirmPassword.setVisibility(View.VISIBLE);
//                    etConfirmPassword.setAlpha(0);
                    ObjectAnimator animator = ObjectAnimator.ofFloat(
                            etConfirmPassword, "alpha", 0.0f, 1.0f);
                    animator.setDuration(2000).start();

                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(
                            tvRegister, "alpha", 1.0f, 0.0f).setDuration(2000);
                    animator2.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            tvRegister.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animator2.start();

                }
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        final String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            showToast("账号密码未填写");
            return;
        }

        NetWorkRequest.login(this, true, username, password, new IReponseListener<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                Hawk.put(HawkKey.USERNAME, username);
                Hawk.put(HawkKey.USER_ID, data);
                tvLogin.animate().translationX(1000).setInterpolator(new AccelerateInterpolator()).setDuration(1000).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startActivity(null, MainActivity.class);
                        finish();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
            }

            @Override
            public void onFail(String msg, int statusCode) {
                showToast(msg);
            }
        });

    }

    /**
     * 注册
     */
    private void register() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password) || StringUtil.isEmpty(confirmPassword)) {
            return;
        }
        if (!password.equals(confirmPassword)) {
            showToast("两次密码不相同");
            return;
        }
        NetWorkRequest.register(this, username, password, new IReponseListener() {
            @Override
            public void onSuccess(Object data) {
                tvLogin.animate().translationX(1000).setInterpolator(new AccelerateInterpolator()).setDuration(1000).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startActivity(null, MainActivity.class);
                        finish();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
            }

            @Override
            public void onFail(String msg, int statusCode) {
                showToast(msg);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (status == REGISTER) {
            status = LOGIN;
            tvRegister.setVisibility(View.VISIBLE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(
                    tvRegister, "alpha", 0.0f, 1.0f);
            animator.setDuration(2000).start();
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(
                    etConfirmPassword, "alpha", 1.0f, 0.0f);
            animator2.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    etConfirmPassword.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator2.setDuration(2000).start();
            return;
        }
        super.onBackPressed();
    }
}
