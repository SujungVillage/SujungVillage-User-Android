package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginDTO(
    val id: String,
    val password: String,
    @SerializedName("fcm_token")
    val fcm: String
) : Serializable

data class LoginResultDTO(
    @SerializedName("jwtToken")
    val token: String,
    @SerializedName("refreshToken")
    val refreshToken: String
) : Serializable
