package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CommDetailResultDTO(
    @SerializedName("id")
    val id:Long,
    @SerializedName("writerId")
    val writerId:String,
    @SerializedName("title")
    val title:String,
    @SerializedName("content")
    val content:String,
    @SerializedName("regDate")
    val regDate:String,
    @SerializedName("modDate")
    val modDate:String,
    @SerializedName("comments")
    val comment:List<CommDetailComments>
):Serializable{}

data class CommDetailComments(
    @SerializedName("id")
    val id:Long,
    @SerializedName("writerId")
    val writerId:String,
    @SerializedName("content")
    val content:String,
    @SerializedName("regDate")
    val regDate:String,
    @SerializedName("modDate")
    val modDate:String
):Serializable{}