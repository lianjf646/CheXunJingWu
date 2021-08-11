package com.hylink.chexunjingwu.http.api

import com.hylink.chexunjingwu.BuildConfig
import com.hylink.chexunjingwu.http.request.ChaRenResponse
import com.hylink.chexunjingwu.http.response.*
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {
    companion object {
//        const val BASE_URL = "http://111.42.38.120:9000/gacz-data-test-service/"
        const val BASE_URL = BuildConfig.BASE_URL
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

    /**
     * @param body
     * @return
     */
    @POST("vehicle/queryPeripheryVehicle")
    suspend fun queryPeripheryVehicle(@Body body: RequestBody?): ApiCommonResponse<QueryPeripheryVehicleResponse>

    @Headers("Authorization: authorization")
    @POST("vehicle/getPatrolList")
    suspend fun getPatrolList(@Body body: RequestBody?): ApiCommonResponse<GetPatrolListResponse>


}