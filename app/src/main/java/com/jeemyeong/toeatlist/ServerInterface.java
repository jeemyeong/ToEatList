package com.jeemyeong.toeatlist;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by jeemyeong_id on 2016-07-19.
 */
public interface ServerInterface {

    /**
     * Food List를 GET방식으로 요청하는 메소드
     *
     * @param callback
     */

    @GET("/foods")
    public void getFoods(Callback<List<Food>> callback);

    /**
     * 여러 게시글 중에서 하나에 해당하는 게시글을 GET방식으로 요청하는 메소드
     *
     * /contents/{article-id}에서 article-id 부분에 선택된 게시글의 id가 붙여져서 요청
     *
     * @param id
     * @param callback
     */

//    @GET("/contents/{article-id}")
//    public void getContentById(@Path("article-id") long id, Callback<Content> callback);
//
//    /**
//     * 게시글의 내용을 객체로 보내는 부분
//     *
//     * 헤더에 Content Type : application/json으로 자료가 어떤 방식으로 요청 및 응답하는지 표시한다.
//     * content를 보내고 그에 대한 결과로 Thumbnail을 받는다.
//     *
//     * @param content
//     * @param callback
//     */
//
//    @Headers("Content-Type: application/json")
//    @POST("/contents")
//    public void newContent(@Body Content content, Callback<Thumbnail> callback);


}
