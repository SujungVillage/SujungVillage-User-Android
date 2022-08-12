package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginDTO(
    @SerializedName("access_token")
    val token: String,
): Serializable {}

data class LoginResultDTO(
    @SerializedName("jwtToken")
    val token: String,
): Serializable {}