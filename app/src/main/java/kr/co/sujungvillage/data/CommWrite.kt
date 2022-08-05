package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CommWriteDTO(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
): Serializable {}


data class CommWriteResultDTO(
    @SerializedName("id")
    val id:Long,
    @SerializedName("writer")
    val writer:CommWriteUser,
    @SerializedName("title")
    val title:String,
    @SerializedName("content")
    val content:String,
    @SerializedName("reg_date")
    val reg_date:String,
    @SerializedName("mod_date")
    val mod_date:String
):Serializable {}

data class CommWriteUser(
    @SerializedName("id")
    val id:String,
    @SerializedName("name")
    val name:String,
    @SerializedName("phoneNumber")
    val phoneNumber:String,
    @SerializedName("domitory")
    val domitory:CommWriterDormitory,
    @SerializedName("detailAddress")
    val detailAddress:String,
    @SerializedName("authority")
    val authority:String
):Serializable {}

data class CommWriterDormitory(
    @SerializedName("domitoryName")
    val domitoryName:String,
    @SerializedName("address")
    val address:String
):Serializable {}