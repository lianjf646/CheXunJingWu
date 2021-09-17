package com.hylink.chexunjingwu.tools

import cn.com.cybertech.models.User

object DataHelper {

    var imei: String? = "";
    var carInfo = "carInfo"
    var objectHashMap: HashMap<String, Any> = HashMap();

    val loginUserInfo = "loginUserInfo"
    val loginUserGroup = "loginUserGroup"
    val loginUserJob = "loginUserJob"
    val basicInformationBean = "basicInformationBean";
    val tagesInfoList = "tagesInfoList";
    val GETJQTZTGLISTRESPONSE_RESULTBEAN_LISTBEAN = "GetJqTztgListResponse.ResultBean.ListBean"

    var idCard: String? = "";
    var deptId: String? = "";
    var user: User? = null;


    fun getData(key: String): Any? = objectHashMap[key]

    fun putData(key: String, o: Any?) {
        objectHashMap[key] = o!!
    }

    fun remove(key: String) {
        objectHashMap.remove(key)
    }


}