package com.example.miracle.graduationproject.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.miracle.graduationproject.R;


/**
 * Created by Miracle on 2018/1/30 0030.
 */

public class LoadingDialog extends Dialog{
    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
    protected LoadingDialog(Context context,boolean cancelable,OnCancelListener cancelListener){
        super(context,cancelable,cancelListener);
    }
    public static class Builder {

        private Context context;
        private String title;
        private String message;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Set the Dialog message from String
         *
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public LoadingDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final LoadingDialog dialog = new LoadingDialog(context, R.style.DialogCommon);
            dialog.setCanceledOnTouchOutside(false);
            View layout = inflater.inflate(R.layout.layout_dialog_loading, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            if (message != null) {
                ((TextView) layout.findViewById(R.id.dialog_tv_msg)).setText(message);
            }
            dialog.setContentView(layout);
            return dialog;
        }

    }
}
