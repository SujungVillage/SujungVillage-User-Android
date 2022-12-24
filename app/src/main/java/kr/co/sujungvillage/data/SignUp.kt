package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// 회원가입
data class SignUpDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("dormitoryName")
    val dormitoryName: String,
    @SerializedName("detailedAddress")
    val detailedAddress: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String
) : Serializable
