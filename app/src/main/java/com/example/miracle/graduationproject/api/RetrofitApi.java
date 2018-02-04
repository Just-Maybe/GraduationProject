package com.example.miracle.graduationproject.api;

import com.example.miracle.graduationproject.dto.Comment;
import com.example.miracle.graduationproject.dto.Hotel;
import com.example.miracle.graduationproject.dto.Order;
import com.example.miracle.graduationproject.dto.RequestEntity;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Miracle on 2018/1/29 0029.
 */

public interface RetrofitApi {
    /**
     * 注册
     *
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(Api.REGISTER)
    Observable<RequestEntity<Void>> register(@Field("username") String username, @Field("password") String password);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(Api.LOGIN)
    Observable<RequestEntity<Integer>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 根据id 获取酒店详情
     *
     * @param hotelId
     * @return
     */
    @FormUrlEncoded
    @POST(Api.GET_HOTEL_BY_ID)
    Observable<RequestEntity<Hotel>> getHotelById(@Field("hotelId") Integer hotelId);

    /**
     * 搜索酒店
     *
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST(Api.SEARCH_HOTEL)
    Observable<RequestEntity<List<Hotel>>> searchHotel(@Field("searchContent") String content);

    @FormUrlEncoded
    @POST(Api.GET_ORDER_BY_USER_ID)
    Observable<RequestEntity<List<Order>>> getOrderByUserId(@Field("userId") Integer userId,
                                                            @Field("status") Integer status,
                                                            @Field("page") Integer page);

    /**
     * 根据hotelId 获取评论列表
     *
     * @param hotelId
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(Api.GET_COMMENT_BY_HOTEL_ID)
    Observable<RequestEntity<List<Comment>>> getCommentByHotelId(@Field("hotelId") Integer hotelId,
                                                                 @Field("page") Integer page);

    /**
     * 发送评论
     *
     * @param content
     * @param userId
     * @param hotelId
     * @return
     */
    @FormUrlEncoded
    @POST(Api.POST_COMMENT)
    Observable<RequestEntity<Void>> postComment(@Field("content") String content,
                                                @Field("userId") Integer userId,
                                                @Field("hotelId") Integer hotelId);
}
