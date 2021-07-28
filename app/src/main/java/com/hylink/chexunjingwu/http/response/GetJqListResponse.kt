package com.hylink.chexunjingwu.http.response

import java.io.Serializable

data class GetJqListResponse(
    val list: List<ListBean>,
    val page: Page
) {
    data class ListBean(
        val afdd: String,
        val afsj: String,
        val ajjssj: String,
        val alarm_time: Long,
        val arrive_time: Any,
        val ay: String,
        val bcjjnr: Any,
        val bjcph: Any,
        val bjdh: String,
        val bjfs: String,
        val bjfsdm: Any,
        val bjlbdm: String,
        val bjlbmc: String,
        val bjlxdm: String,
        val bjlxmc: String,
        val bjnr: String,
        val bjr: String,
        val bjrlxdz: String,
        val bjrxb: String,
        val bjrxm: String,
        val bjrzjh: Any,
        val bjsj: String,
        val bjxldm: String,
        val bjxlmc: String,
        val blqk: Any,
        val bzwqk: Any,
        val cjdbh: String,
        val cjdwdm: Any,
        val cjdwmc: String,
        val cjsj: String,
//            val cjxx: SignStatusResponse.DataBean.Cjxx,
        val cjyj: String,
        val cllx: Any,
        val clqx: Any,
        val createtime: Long,
        val createuser: Any,
        val dcjyxm: Any,
        val dcsj: Any,
        val dcxx: Any,
        val etl_sbsz: Any,
        val finish_time: Any,
        val fkyq: Any,
        val flag: String,
        val gxdwdm: String,
        val gxdwmc: String,
        val hzdj: Any,
        val jjczsj: Any,
        val jjdscbz: Any,
        val jjdwmc: String,
        val jjdzt: String,
        val jjlxdm: Any,
        val jjlyh: String,
        val jjyxm: String,
        val jptsj: Any,
        val jqbh: String,
        val jqjb: String,
        val jsjyxm: Any,
        val jssj: Any,
        val jsxx: Any,
        val jzjg: Any,
        val jzlb: Any,
        val lat: String,
        val ldgbh: Any,
        val lhlbdm: Any,
        val lng: String,
        val lxdh: String,
        val lyh: String,
        val one_level_time: Any,
        val pad_cid: Any,
        val police_alarm_destination_gps: Any,
        val police_alarm_flow_id: Any,
        val police_alarm_id: String,
        val qhcs: Any,
        val qsjyxm: Any,
        val qssj: Any,
        val qsxx: Any,
        val receive_message: ListBean.ReceiveMessage,
        val receive_type: Any,
        val rksjc: Any,
        val rswxz: Any,
        val sfbw: Any,
        val sfzxxs: Any,
        val sjfssj: String,
        val sjgxsj: Any,
        val status: Any,
        val syqx: Any,
        val thsc: Any,
        val two_level_time: Any,
        val updatetime: Long,
        val updateuser: Any,
        val visibale: Int,
        val xh: Int,
        val xzb: String,
        val xzqh: Any,
        val yfty: Any,
        val yfwxwz: Any,
        val yhdz: String,
        val yhxm: String,
        val yjdw: Any,
        val yjr: Any,
        val yjsm: Any,
        val ywbkry: Any,
        val ywbzxl: Any,
        val yzb: String,
        val zjly: Any
    ) : Serializable {
        data class Cjxx(
            val cjr: String
        ) : Serializable

        data class ReceiveMessage(
            val jjdw: String
        ) : Serializable
    }

    data class Page(
        val currentPage: Int,
        val currentResult: Int,
        val entityOrField: Boolean,
        val pageStr: String,
        val pd: Pd,
        val showCount: Int,
        val totalPage: Int,
        val totalResult: Int
    ) : Serializable {
        data class Pd(
            val bjsj: String,
            val carno: String,
            val gxdwdm: String,
            val imei: String,
            val isgx: Int,
            val isnow: Int,
            val jjdzt: String,
            val mjdwmc: String,
            val sign: Sign
        ) : Serializable {
            data class Sign(
                val gxdwdm: String,
                val gxdwmc: String,
                val kqrbm: Any,
                val kqrxm: Any
            ) : Serializable
        }
    }
}
