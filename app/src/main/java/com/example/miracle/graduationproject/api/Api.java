package com.example.miracle.graduationproject.api;

/**
 * Created by Miracle on 2018/1/29 0029.
 */

public class Api {

    public static final String REGISTER = "user/register";  //注册

    public static final String LOGIN = "user/login";   //登录

    public static final String GET_HOTEL_BY_ID = "hotel/getHotelById";//根据id 获取酒店详情

    public static final String SEARCH_HOTEL = "hotel/searchHotel";  //搜索酒店

    public static final String GET_ORDER_BY_USER_ID = "order/getOrderByUserId"; //根据userid 查询订单

    public static final String GET_COMMENT_BY_HOTEL_ID = "comment/getCommentByHotelId";//根据hotelId 获取评论

    public static final String POST_COMMENT = "comment/postComment";    //发送评论
}
