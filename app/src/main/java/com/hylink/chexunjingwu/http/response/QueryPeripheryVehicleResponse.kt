package com.hylink.chexunjingwu.http.response

data class QueryPeripheryVehicleResponse
    (
    val list: List<Dto>
) {
    data class Dto(
        val coordinated_operations_participant_imei: String,
        val cth: String? = "",
        val gps_patrol_state: Any,
        val gps_point: List<Double>,
        val is_online: Int,
        val organization_code: String,
        val organization_name: String,
        val participant_type: Int,
        val userInfo: List<UserInfoDTO>,
        val vehicle_id: String,
        val vehicle_license_plate: String,
        val vehicle_message: Any,
        val vehicle_state: Any
    ) {
        data class UserInfoDTO(var name: String? = null, var contact: String? = null)
    }
}
