package com.example.chexunjingwu.http.api

import com.example.chexunjingwu.http.request.ChaRenResponse
import com.example.chexunjingwu.http.response.*
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {
    companion object {
        const val BASE_URL = "http://111.42.38.120:9000/gacz-data-test-service/"
    }

    @POST("sign/login")
    suspend fun homeLogin(@Body homeLogin: RequestBody): ApiCommonResponse<HomeLoginResponse>


    /**
     * 二维码是否过期
     *
     * @param body
     * @return
     */
    @POST("scanCodeLogin/ifTimeOut")
    suspend fun ifTimeOut(@Body homeLogin: RequestBody): ApiCommonResponse<IfTimeOutResponse>

    /**
     * 扫码登录接口
     *
     * @param body
     * @return
     */
    @POST("scanCodeLogin/login")
    suspend fun login(@Body body: RequestBody): ApiCommonResponse<LoginResponse>

    /**
     * @param body
     * @return
     */
    @POST("sign/signStatus")
    suspend fun signStatus(@Body body: RequestBody): ApiCommonResponse<SignStatusResponse>


    /**
     * @param body
     * @return
     */
    @POST("jq/getJqList")
    suspend fun getJqList(@Body body: RequestBody?): ApiCommonResponse<GetJqListResponse>


    @Headers("Authorization: authorization")
    @POST("jq/jqQs")
    suspend fun jqQs(@Body body: RequestBody): ApiCommonResponse<JqChangeStateResponse>


    @Headers("Authorization: authorization")
    @POST("jq/jqDcqr")
    suspend fun jqDcqr(@Body body: RequestBody?): ApiCommonResponse<JqChangeStateResponse>


    @Headers("Authorization: authorization")
    @POST("jq/jqJqjs")
    suspend fun jqJqjs(@Body body: RequestBody): ApiCommonResponse<JqChangeStateResponse>


    @Headers("Authorization: token")
    @POST("jq/jqJqfkzcDetails")
    suspend fun jqJqfkzcDetails(@Body request: RequestBody): ApiCommonResponse<JqJqfkzcDetailsResponse>

    @Headers("Authorization: authorization")
    @POST("jq/jqDict")
    suspend fun jqDict(@Body body: RequestBody?): ApiCommonResponse<JqDictResponse>

    @Headers("Authorization: authorization")
    @POST("sign/getUserList")
    suspend fun getUserList(@Body request: RequestBody): ApiCommonResponse<GetUserListResponse>

    @Headers("Authorization: authorization")
    @POST("jq/ywDict")
    suspend fun ywDict(@Body body: RequestBody?): ApiCommonResponse<YwDictResponse>

    @Headers("Authorization: token")
    @POST("jq/jTztgFk")
    suspend fun jTztgFk(@Body request: RequestBody?): ApiCommonResponse<JTztgFkResponse>

    @Headers("Authorization: authorization")
    @POST("jq/jqJqfk")
    suspend fun jqJqfk(@Body body: RequestBody?): ApiCommonResponse<JqJqfkResponse>

    @Headers("Authorization: authorization")
    @POST("jq/getJqFkList")
    suspend fun getJqFkList(@Body body: RequestBody?): ApiCommonResponse<GetJqFkListResponse>

    /**
     * 查询车牌
     *
     * @param body
     * @return
     */
    @POST("verification/checCL")
    suspend fun checCL(@Body body: RequestBody?): ApiCommonResponse<ChaCheResponse>

    /**
     * 根据身份证进行查询
     *
     * @param body
     * @return
     */
    @POST("verification/checkRY")
    suspend fun checkRY(@Body body: RequestBody?): ApiCommonResponse<ChaRenResponse>

    @Headers("Authorization: token")
    @POST("jq/getJqTztgList")
    suspend fun getJqTztgList(@Body request: RequestBody?): ApiCommonResponse<GetJqTztgListResponse>

    @Headers("Authorization: token")
    @POST("jq/instructionMessageRead")
    suspend fun instructionMessageRead(@Body request: RequestBody?): ApiCommonResponse<InstructionMessageReadResponse>

    /**
     * 这是确认指令接口  不要被命名迷惑
     *
     * @param request
     * @return
     */
    @Headers("Authorization: token")
    @POST("jq/getJqTztgDetail")
    suspend fun getJqTztgDetail(@Body request: RequestBody?): ApiCommonResponse<GetJqTztgDetailResponse>

    @Headers("Authorization: token")
    @POST("jq/getJqTztgFkList")
    suspend fun getJqTztgFkList(@Body request: RequestBody?): ApiCommonResponse<GetJqTztgFkListResponse>

    @Headers("Authorization: token")
    @POST("jq/jqJqfkzcList")
    suspend fun jqJqfkzcList(@Body request: RequestBody?): ApiCommonResponse<JqJqfkzcListResponse>

    /**
     * 预警记录查询
     *
     * @param body
     * @return
     */
    @POST("verification/alarmPageList")
    suspend fun alarmList(@Body body: RequestBody?): ApiCommonResponse<AlarmListResponse>

    /**
     * @param body
     * @return
     */
    @POST("verification/getAlarmById")
    suspend fun getAlarmById(@Body body: RequestBody?): ApiCommonResponse<GetAlarmByIdResponse>

    /**
     * 获取人像比对结果根据核查id
     * @param body
     * @return
     */
    @Headers("Authorization: authorization")
    @POST("verification/getVerificationComparisonById")
    suspend fun getVerificationPortraitById(@Body body: RequestBody?): ApiCommonResponse<GetVerificationPortraitByIdResponse>

    @Headers("Authorization: token")
    @POST("jq/getAjbh")
    suspend fun getAjbh(@Body request: RequestBody?): ApiCommonResponse<GetAjbhResponse>

    @Headers("Authorization: authorization")
    @POST("jq/jqCfjd")
    suspend fun jqCfjd(@Body body: RequestBody?): ApiCommonResponse<JqCfjdResponse>


    @Headers("Authorization: authorization")
    @POST("jq/cfjdEdit")
    suspend fun cfjdEdit(@Body body: RequestBody?): ApiCommonResponse<JqCfjdResponse>

    @Headers("Authorization: token")
    @POST("jq/getCfjdDetail")
    suspend fun getCfjdDetail(@Body request: RequestBody?): ApiCommonResponse<GetCfjdDetailResponse>
//    /**
//     * 预警记录查询
//     *
//     * @param body
//     * @return
//     */
//    @POST("verification/alarmPageList")
//    fun alarmList(@Body body: RequestBody?): ApiCommonResponse<AlarmListResponse>


//    // 首页热门博文(热门博文 = 置顶文章 + 首页文章) - 置顶文章
//    @GET("article/top/json")
//    suspend fun getHomeTopArticles(): ApiCommonResponse<List<ArticleBean>>
//
//    // 首页热门博文(热门博文 = 置顶文章 + 首页文章) - 首页文章列表
//    @GET("article/list/{page}/json")
//    suspend fun getHomePageArticles(@Path("page") page: Int): ApiCommonResponse<CommonArticleData<ArticleBean>>
//
//    // 首页Banner
//    @GET("banner/json")
//    suspend fun getHomeBanner(): ApiCommonResponse<List<HomeBannerBean>>
//
//    // 获取最新项目（首页第二个TAB）
//    @GET("article/listproject/{page}/json")
//    suspend fun getHomeNewestProjects(@Path("page") page: Int): ApiCommonResponse<CommonArticleData<ArticleBean>>
//
//    // 首页最新分享（广场）
//    @GET("user_article/list/{page}/json")
//    suspend fun getHomeNewestShare(@Path("page") page: Int): ApiCommonResponse<CommonArticleData<ArticleBean>>
//
//    // 首页公众号列表
//    @GET("wxarticle/chapters/json")
//    suspend fun getPublicAccountList(): ApiCommonResponse<List<PublicAccountBean>>
//
//    // 首页公众号文章列表
//    @GET("wxarticle/list/{id}/{page}/json")
//    suspend fun getPublicAccountArticles(
//        @Path("id") id: Int, @Path("page") page: Int
//    ): ApiCommonResponse<CommonArticleData<ArticleBean>>
//
//    @GET("project/tree/json")
//    suspend fun getProjectCategory(): ApiCommonResponse<List<ProjectCategoryBean>>
//
//    // 项目文章
//    @GET("project/list/{page}/json")
//    suspend fun getProjectArticles(
//        @Path("page") page: Int, @Query("cid") cid: Int
//    ): ApiCommonResponse<CommonArticleData<ArticleBean>>
//
//    // 体系分类
//    @GET("tree/json")
//    suspend fun getSystemCategory(): ApiCommonResponse<List<SystemCategoryBean>>
//
//    // 体系文章
//    @GET("article/list/{page}/json")
//    suspend fun getSystemArticles(
//        @Path("page") page: Int, @Query("cid") cid: Int
//    ): ApiCommonResponse<CommonArticleData<ArticleBean>>
//
//    // 导航分类
//    @GET("navi/json")
//    suspend fun getNavigation(): ApiCommonResponse<List<NavigationBean>>
//
//    // 登录
//    @POST("user/login")
//    @FormUrlEncoded
//    suspend fun login(
//        @Field("username") username: String,
//        @Field("password") password: String
//    ): ApiCommonResponse<UserBean>
//
//    // 注册
//    @POST("user/register")
//    @FormUrlEncoded
//    suspend fun register(
//        @Field("username") username: String,
//        @Field("password") password: String,
//        @Field("repassword") repassword: String
//    ): ApiCommonResponse<UserBean>
//
//    // 获取个人积分数和排名
//    @GET("lg/coin/userinfo/json")
//    suspend fun getMyPointsInfo(): ApiCommonResponse<PointRankBean>
//
//    // 获取个人积分列表
//    @GET("lg/coin/list/{page}/json")
//    suspend fun getMyPointsList(@Path("page") page: Int): ApiCommonResponse<CommonArticleData<PointsListBean>>
//
//    // 获取积分排行榜
//    @GET("coin/rank/{page}/json")
//    suspend fun getAllPointsRank(@Path("page") page: Int): ApiCommonResponse<CommonArticleData<PointRankBean>>
//
//    // 收藏文章列表
//    @GET("lg/collect/list/{page}/json")
//    suspend fun getMyCollections(@Path("page") page: Int): ApiCommonResponse<CommonArticleData<ArticleBean>>
//
//    // 收藏站内文章
//    @POST("lg/collect/{id}/json")
//    suspend fun collectArticle(@Path("id") id: Int): ApiCommonResponse<Any>
//
//    // 从文章列表取消收藏
//    @POST("lg/uncollect_originId/{id}/json")
//    suspend fun cancelCollectFromArticleList(@Path("id") id: Int): ApiCommonResponse<Any>
//
//    // 从收藏列表取消收藏
//    @POST("lg/uncollect/{id}/json")
//    @FormUrlEncoded
//    suspend fun cancelCollectFromCollectionList(
//        @Path("id") id: Int,
//        @Field("originId") originId: Int
//    ): ApiCommonResponse<Any>
//
//    // 我的分享列表
//    @GET("user/lg/private_articles/{page}/json")
//    suspend fun getMyShared(@Path("page") page: Int): ApiCommonResponse<ShareBean>
//
//    // 别人的分享列表
//    @GET("user/{id}/share_articles/{page}/json")
//    suspend fun getOthersShared(
//        @Path("id") id: Int,
//        @Path("page") page: Int
//    ): ApiCommonResponse<ShareBean>
//
//    // 添加分享
//    @POST("lg/user_article/add/json")
//    @FormUrlEncoded
//    suspend fun addShare(
//        @Field("title") title: String, @Field("link") link: String
//    ): ApiCommonResponse<ShareBean>
//
//    // 删除我的分享
//    @POST("lg/user_article/delete/{id}/json")
//    suspend fun deleteMyShare(@Path("id") id: Int): ApiCommonResponse<Any>
//
//    // 获取TODO列表
//    @GET("lg/todo/v2/list/{page}/json")
//    suspend fun getMyTodo(
//        @Path("page") page: Int, @Query("status") status: Int, @Query("type") type: Int,
//        @Query("priority") priority: Int, @Query("orderby") orderby: Int
//    ): ApiCommonResponse<CommonArticleData<TodoBean>>
//
//    // 新增待办
//    @POST("lg/todo/add/json")
//    @FormUrlEncoded
//    suspend fun addMyTodo(
//        @Field("title") title: String,
//        @Field("content") content: String,
//        @Field("date") date: String,
//        @Field("type") type: Int,
//        @Field("priority") priority: Int
//    ): ApiCommonResponse<TodoBean>
//
//    // 更新待办
//    @POST("lg/todo/update/{id}/json")
//    @FormUrlEncoded
//    suspend fun updateMyTodo(
//        @Path("id") id: Int, @Field("title") title: String, @Field("content") content: String,
//        @Field("date") date: String, @Field("status") status: Int, @Field("type") type: Int,
//        @Field("priority") priority: Int
//    ): ApiCommonResponse<TodoBean>
//
//    // 更新待办状态，例如将未完成修改成完成
//    @POST("lg/todo/done/{id}/json")
//    @FormUrlEncoded
//    suspend fun updateMyTodoStatus(
//        @Path("id") id: Int,
//        @Field("status") status: Int
//    ): ApiCommonResponse<TodoBean>
//
//    // 删除待办
//    @POST("lg/todo/delete/{id}/json")
//    suspend fun deleteMyTodo(@Path("id") id: Int): ApiCommonResponse<Any>
//
//
//    @GET("wenda/list/{page}/json")
//    suspend fun getQA(@Path("page") page: Int): ApiCommonResponse<CommonArticleData<ArticleBean>>
//
//    // 更新待办状态，例如将未完成修改成完成
//    @POST("article/query/{page}/json")
//    @FormUrlEncoded
//    suspend fun search(
//        @Path("page") page: Int,
//        @Field("k") k: String
//    ): ApiCommonResponse<CommonArticleData<ArticleBean>>
//
//    @GET("hotkey/json")
//    suspend fun getHotKey(): ApiCommonResponse<List<HotKeyBean>>
}