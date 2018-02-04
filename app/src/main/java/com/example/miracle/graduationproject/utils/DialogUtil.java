package com.example.miracle.graduationproject.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.example.miracle.graduationproject.dialog.LoadingDialog;

import java.util.List;

/**
 * Created by Miracle on 2018/1/30 0030.
 */

public class DialogUtil {
    /**
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg) {
        LoadingDialog.Builder builder = new LoadingDialog.Builder(context);
        builder.setMessage(msg);
        return builder.create();
    }

    public static Dialog createDialog(Context context, int layout, int themeId, onDialogViewListener listener) {
        Dialog dialog = new Dialog(context,themeId);
        View view = LayoutInflater.from(context).inflate(layout, null);
        if (listener != null) {
            listener.DialogViewListener(view);
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        return dialog;
    }

    public interface onDialogViewListener {
        void DialogViewListener(View view);
    }

//    /**
//     * 显示选择item的dialog
//     *
//     * @param context
//     * @param list
//     * @param listener
//     * @param cancelOutSide
//     */
//    public static void showSelectItemDialog(final Context context,
//                                            String title, List<String> list, boolean cancelOutSide,
//                                            ISelectItemListener listener) {
//        if (context == null)
//            return;
//
//        SelectItemDialog selectItemDialog = new SelectItemDialog(context,
//                listener);
//        selectItemDialog.setListItem(list);
//        if (!TextUtils.isEmpty(title))
//            selectItemDialog.setTitle(title);
//        selectItemDialog.setCanceledOnTouchOutside(cancelOutSide);
//        selectItemDialog.show();
//    }

}
