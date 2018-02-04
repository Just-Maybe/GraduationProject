package com.example.miracle.graduationproject.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Miracle on 2018/2/1 0001.
 */

public class Room implements Parcelable{

    /**
     * id : 1
     * roomName : 商务房
     * price : 299
     * bedType : 大床
     * introduction : 拖鞋、24小时热水、浴缸、吹风机
     * hotelId : 1
     * number : 8
     */

    private int id;
    private String roomName;
    private int price;
    private String bedType;
    private String introduction;
    private int hotelId;
    private int number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.roomName);
        dest.writeInt(this.price);
        dest.writeString(this.bedType);
        dest.writeString(this.introduction);
        dest.writeInt(this.hotelId);
        dest.writeInt(this.number);
    }

    public Room() {
    }

    protected Room(Parcel in) {
        this.id = in.readInt();
        this.roomName = in.readString();
        this.price = in.readInt();
        this.bedType = in.readString();
        this.introduction = in.readString();
        this.hotelId = in.readInt();
        this.number = in.readInt();
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel source) {
            return new Room(source);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };
}
