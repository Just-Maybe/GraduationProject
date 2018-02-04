package com.example.miracle.graduationproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.miracle.graduationproject.R;
import com.example.miracle.graduationproject.dto.Room;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Miracle on 2018/1/30 0030.
 */

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private Context mContext;
    private List<Room> mDatas;
    private onClickListener listener;

    public RoomAdapter(Context mContext, List<Room> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    public void update(List<Room> datas) {
        if (datas != null) {
            this.mDatas = datas;
            notifyDataSetChanged();
        }
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, final int position) {
        holder.tvRoomName.setText(mDatas.get(position).getRoomName());
        holder.tvRoomBed.setText(mDatas.get(position).getBedType());
        holder.tvRoomPrice.setText("￥ " + mDatas.get(position).getPrice());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position,mDatas.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface onClickListener{
        void onClick(int position,Room room);
    }
    public void setOnClickListener(onClickListener listener){
        this.listener = listener;
    }
    class RoomViewHolder extends RecyclerView.ViewHolder {
        View view;
        @Bind(R.id.tv_room_name)
        TextView tvRoomName;
        @Bind(R.id.tv_room_bed)
        TextView tvRoomBed;
        @Bind(R.id.tv_room_price)
        TextView tvRoomPrice;

        public RoomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
        }
    }
}
