package com.rescue.flutter_720yun.models.homemodel

data class BaseResponse<T> (
    val code: Int,
    var data: T,
    val message: String
)

data class BaseListResp<T> (
    val code: Int,
    val data: List<T>,
    val message: String
)

/*
"userInfo": {
                "id": 26,
                "avator": "1659245031116/TOPSPigb.png",
                "username": "xi1",
                "phone_number": "13689242201",
                "email": "",
                "create_time": "2022-07-27T23:01:45.636463+08:00",
                "wx_id": ""
            },
 */
data class UserInfo(
    val id: Int,
    val avator: String,
    val username: String,
    val phone_number: String,
    val email: String,
    val create_time: String,
    val wx_id: String
)

data class HomeListModel (
    val id: Int?,
    val content: String,
    val create_time: String,
    val upload_time: String,
    val userInfo: UserInfo
)