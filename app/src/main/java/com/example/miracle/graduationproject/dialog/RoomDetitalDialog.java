package com.example.miracle.graduationproject.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.miracle.graduationproject.R;
import com.example.miracle.graduationproject.dto.Room;
import com.library.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Miracle on 2018/2/2 0002.
 */

public class RoomDetitalDialog extends Dialog {
    @Bind(R.id.tv_hotel_name)
    TextView tvHotelName;
    @Bind(R.id.tv_enter_time)
    TextView tvEnterTime;
    @Bind(R.id.tv_quit_time)
    TextView tvQuitTime;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.tv_days)
    TextView tvDays;
    @Bind(R.id.tv_order)
    TextView tvOrder;
    @Bind(R.id.tv_remain)
    TextView tvRemain;
    private Context mContext;
    private Room mRoom;
    private int year;
    private int month;
    private int day;
    private Calendar calendar;
    private DatePickerDialog dialog;
    private String title;
    private Date enterDate;
    private Date quitDate;

    public RoomDetitalDialog(@NonNull Context context, Room room, String title) {
        super(context, R.style.ActionButtomDialogNotTransparentStyle);
        this.mContext = context;
        this.mRoom = room;
        this.title = title;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_room_detail);
        ButterKnife.bind(this);
        tvHotelName.setText(title);
        tvRemain.setText("剩余" + mRoom.getNumber() + "间");
        initCalendar();
    }

    private void initCalendar() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        tvEnterTime.setText(year + "-" + (month + 1) + "-" + day);
        tvQuitTime.setText(year + "-" + (month + 1) + "-" + day);
        enterDate = new Date(year, month, day);
        quitDate = new Date(year, month, day);
    }

    @OnClick({R.id.tv_order, R.id.tv_enter_time, R.id.tv_quit_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_order:
                orderNow();
                break;
            case R.id.tv_enter_time:
                dialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvEnterTime.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        enterDate = new Date(year, month, dayOfMonth);
                        int days = DateUtil.getDayDistance(enterDate.getTime(), quitDate.getTime());
                        tvDays.setText("共" + days + "天");
                        dialog.dismiss();
                    }
                }, year, month, day);
                dialog.show();
                break;
            case R.id.tv_quit_time:
                dialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvQuitTime.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        quitDate = new Date(year, month, dayOfMonth);
                        Log.d("TAG", "onDateSet: enterDate" + enterDate.getTime() + "quit" + quitDate.getTime());
                        int days = DateUtil.getDayDistance(enterDate.getTime(), quitDate.getTime());
                        tvDays.setText("共" + days + "天");
                        dialog.dismiss();
                    }
                }, year, month, day);
                dialog.show();
                break;
        }
    }

    /**
     * 马上下单
     */
    private void orderNow() {

    }
}
