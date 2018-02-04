package com.example.miracle.graduationproject.dto;

import java.util.List;

/**
 * Created by Miracle on 2018/2/1 0001.
 */

public class Hotel {

    /**
     * id : 1
     * hotelName : 上海三湘大厦
     * address : 上海 长宁区 中山西路1243号 ，近虹桥路。
     * introduce : 1992年开业  2012年装修  132间房   联系方式　　
     　　上海三湘大厦位于中山西路上，紧邻内环线，界于虹桥开发区和徐家汇商业圈之间，靠近东华大学。
     　　上海三湘大厦是湖南省人民政府在上海建造的涉外宾馆，主楼高17层。大厦设有各类客房、中西餐厅、宴会厅等配套服务齐全。客房配有彩电、冰箱、保险柜，环境舒适，是食宿、商务、娱乐的便捷旅居地。
     * type : 2
     * hotelImgUrl : [{"id":1,"picture":"/pic","hotel_id":1},{"id":2,"picture":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=36606817,1930198207&fm=27&gp=0.jpg","hotel_id":1}]
     * roomList : [{"id":1,"roomName":"商务房","price":299,"bedType":"大床","introduction":"拖鞋、24小时热水、浴缸、吹风机","hotelId":1,"number":8},{"id":3,"roomName":"总统套房","price":1299,"bedType":"3大床2小床","introduction":"泳池、温泉、桑拿房","hotelId":1,"number":8}]
     */

    private int id;
    private String hotelName;
    private String address;
    private String introduce;
    private int type;
    private List<HotelImgUrlBean> hotelImgUrl;
    private List<Room> roomList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<HotelImgUrlBean> getHotelImgUrl() {
        return hotelImgUrl;
    }

    public void setHotelImgUrl(List<HotelImgUrlBean> hotelImgUrl) {
        this.hotelImgUrl = hotelImgUrl;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public static class HotelImgUrlBean {
        /**
         * id : 1
         * picture : /pic
         * hotel_id : 1
         */

        private int id;
        private String picture;
        private int hotel_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public int getHotel_id() {
            return hotel_id;
        }

        public void setHotel_id(int hotel_id) {
            this.hotel_id = hotel_id;
        }
    }

}
