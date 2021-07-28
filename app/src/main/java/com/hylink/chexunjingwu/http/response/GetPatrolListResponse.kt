package com.hylink.chexunjingwu.http.response

data class GetPatrolListResponse(

    val result: Result
) {
    data class Result(
        val bayonetList: List<Bayonet>,
        val individualEquipmentList: List<IndividualEquipment>,
        val intercomList: List<Intercom>,
        val keyPlaceList: List<KeyPlace>,
        val list: List<Any>,
        val policeStationList: List<PoliceStation>,
        val reason: Reason,
        val videoList: List<Video>
    ) {
        data class Bayonet(
            val bayonet_id: String,
            val gps: List<Double>,
            val gxdwdm: String,
            val gxdwmc: String,
            val jd: String,
            val kkdm: String,
            val kkid: String,
            val kkmc: String,
            val wd: String,
            val xh: Int
        )

        data class IndividualEquipment(
            val createtime: String,
            val createuser: String,
            val cth: String,
            val equipment_id: String,
            val equipment_identification_code: String,
            val equipment_message: EquipmentMessage,
            val equipment_name: String,
            val equipment_organization_code: String,
            val equipment_organization_name: String,
            val equipment_state: String,
            val equipment_type: String,
            val flag: String,
            val gps_point: List<Double>,
            val remark: String,
            val source_type: Int,
            val status: Int,
            val updatetime: String,
            val updateuser: String,
            val visibale: Int
        ) {
            data class EquipmentMessage(
                val cth: String
            )
        }

        data class Intercom(
            val createtime: String,
            val createuser: String,
            val cth: String,
            val equipment_id: String,
            val equipment_identification_code: String,
            val equipment_message: EquipmentMessage,
            val equipment_name: String,
            val equipment_organization_code: String,
            val equipment_organization_name: String,
            val equipment_state: String,
            val equipment_type: String,
            val flag: String,
            val gps_point: List<Double>,
            val remark: String,
            val source_type: Int,
            val status: Int,
            val updatetime: String,
            val updateuser: String,
            val visibale: Int
        ) {
            data class EquipmentMessage(
                val cth: String
            )
        }

        data class KeyPlace(
            val bayonet_id: String,
            val gps: List<Double>,
            val gxdwdm: String,
            val gxdwmc: String,
            val jd: String,
            val kkdm: String,
            val kkid: String,
            val kkmc: String,
            val wd: String,
            val xh: Int
        )

        data class PoliceStation(
            val bayonet_id: String,
            val gps: List<Double>,
            val gxdwdm: String,
            val gxdwmc: String,
            val jd: String,
            val kkdm: String,
            val kkid: String,
            val kkmc: String,
            val wd: String,
            val xh: Int
        )

        data class Reason(
            val code: String,
            val text: String
        )

        data class Video(
            val bayonet_id: String,
            val gps: List<Double>,
            val gxdwdm: String,
            val gxdwmc: String,
            val jd: String,
            val kkdm: String,
            val kkid: String,
            val kkmc: String,
            val wd: String,
            val xh: Int
        )
    }
}
