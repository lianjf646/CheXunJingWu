package com.example.chexunjingwu.http.response

data class HomeLoginResponse(
    val `data`: Data
) {
    data class Data(
        val `data`: Data,
        val error: Any
    ) {
        data class Data(
            val menu: List<Any>,
            val token: String,
            val user: User
        ) {
            data class User(
                val appName: String,
                val contact: String,
                val department: String,
                val geographicalLevel: Int,
                val group: Group,
                val groupList: List<Group>,
                val id: String,
                val idCard: String,
                val isAdmin: Int,
                val job: List<Job>,
                val name: String,
                val pcard: String,
                val photo: String,
                val roleList: List<Role>,
                val state: Int,
                val visiable: Int
            ) {
                data class Group(
                    val code: String,
                    val depth: Int,
                    val id: String,
                    val name: String,
                    val parentId: String
                )

                data class Job(
                    val code: String,
                    val name: String
                )

                data class Role(
                    val description: String,
                    val rid: String,
                    val rname: String
                )
            }
        }
    }
}
