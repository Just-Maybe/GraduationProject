package com.example.miracle.graduationproject.dto;

/**
 * Created by Miracle on 2018/2/3 0003.
 */

public class Comment {

    /**
     * id : 1
     * content : 设施够标准，卫生也很好。房间及走廊地毯味太大！空调师傅竟然不会开空调，给我们开了一夜冷空调，把我老伴都冻感冒了！
     * createTime : 2017-12-29 10:58:03.0
     * hotelId : 1
     * userId : 1
     * name : 用户a
     * imgUrl : null
     */

    private int id;
    private String content;
    private String createTime;
    private int hotelId;
    private int userId;
    private String name;
    private String imgUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", hotelId=" + hotelId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
