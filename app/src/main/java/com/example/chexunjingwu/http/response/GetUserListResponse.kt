package com.example.chexunjingwu.http.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class GetUserListResponse(var data: DataDTO) {

    data class DataDTO(var list: List<ListDTO>? = null) {

        @Parcelize
        data class ListDTO(
            var featureState: Int = 0,
            val rolesname: String? = null,
            var idcard: String? = null,
            var contact: String? = null,
            var name: String? = null,
            var pcard: String? = null,
            var id: String? = null,
            var opttime: String? = null,
            var state: Int = 0,
            val department: String? = null,
            var depname: String? = null,
            var job: List<JobDTO>? = null,
            var choice: Boolean = false
        ) : Parcelable {
            @Parcelize
            data class JobDTO(
                var code: String? = null,
                val name: String? = null,
            ):Parcelable
        }
    }
}