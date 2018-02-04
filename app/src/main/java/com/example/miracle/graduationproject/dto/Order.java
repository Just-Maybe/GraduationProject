package com.example.miracle.graduationproject.dto;

/**
 * Created by Miracle on 2018/2/2 0002.
 */

public class Order {


    /**
     * id : 6
     * roomId : 1
     * days : 1
     * price : 299
     * createDate : 2017-12-29 11:02:14.0
     * leaveTime : 2017-12-30 11:02:11.0
     * liveTime : 2017-12-30 11:02:06.0
     * status : 5
     * userId : 2
     * number : 1
     * orderNo : 0
     * hotelName : 上海三湘大厦
     */

    private int id;
    private int roomId;
    private int days;
    private int price;
    private String createDate;
    private String leaveTime;
    private String liveTime;
    private int status;
    private int userId;
    private int number;
    private long orderNo;
    private String hotelName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", days=" + days +
                ", price=" + price +
                ", createDate='" + createDate + '\'' +
                ", leaveTime='" + leaveTime + '\'' +
                ", liveTime='" + liveTime + '\'' +
                ", status=" + status +
                ", userId=" + userId +
                ", number=" + number +
                ", orderNo=" + orderNo +
                ", hotelName='" + hotelName + '\'' +
                '}';
    }
}
