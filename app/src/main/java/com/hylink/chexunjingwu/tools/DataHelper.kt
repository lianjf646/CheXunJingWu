package com.hylink.chexunjingwu.tools

object DataHelper {

    var imei: String? = "R624422009130189"
    var carInfo = "carInfo"
    var objectHashMap: HashMap<String, Any> = HashMap();

    val loginUserInfo = "loginUserInfo"
    val loginUserGroup = "loginUserGroup"
    val loginUserJob = "loginUserJob"
    val basicInformationBean = "basicInformationBean";
    val tagesInfoList = "tagesInfoList";
    val GETJQTZTGLISTRESPONSE_RESULTBEAN_LISTBEAN = "GetJqTztgListResponse.ResultBean.ListBean"
    fun getData(key: String): Any? = objectHashMap[key]

    fun putData(key: String, o: Any?) {
        objectHashMap[key] = o!!
    }

    fun remove(key: String) {
        objectHashMap.remove(key)
    }


}