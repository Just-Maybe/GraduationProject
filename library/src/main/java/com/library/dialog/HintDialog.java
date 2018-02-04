package com.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.library.R;
import com.library.widget.BGButton;

/**
 * Created by Administrator on 2016/11/30.
 */
public class HintDialog extends Dialog{

    TextView tvTitle;
    TextView tvContent;
    BGButton cancel;
    BGButton commit;
    private Callback callback;


    /**
     * @param context 上下文
     * @param content 提示的文字
     * @param btnText 按钮显示的文字，最多长度为两个
     */
    public HintDialog(Context context, String title, String content, String[] btnText) {
        super(context, R.style.Dialog);
        this.setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_hint);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);
        cancel = (BGButton) findViewById(R.id.cancel);
        commit = (BGButton) findViewById(R.id.commit);
        tvTitle.setText(title);
        tvContent.setText(content);
        if (btnText.length > 0) {
            if (btnText.length > 1) {
                cancel.setText(btnText[0]);
                commit.setText(btnText[1]);
            } else {
                cancel.setVisibility(View.GONE);
                commit.setText(btnText[0]);
            }
        } else {
            cancel.setVisibility(View.GONE);
            commit.setVisibility(View.GONE);
        }
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    dismiss();
                    callback.callback();
                    return;
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    dismiss();
                    callback.cancle();
                    return;
                }
            }
        });
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    public interface Callback {
        void callback();

        void cancle();
    }
}
