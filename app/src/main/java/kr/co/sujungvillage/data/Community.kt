package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// 커뮤니티 프래그먼트
data class CommDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("writerId")
    val writerId: String,
    @SerializedName("regDate")
    val regDate: String,
    @SerializedName("numOfComments")
    val numOfComments: Long
) : Serializable

// 커뮤니티 댓글 쓰기
data class CommDetailCommentsWriteDTO(
    @SerializedName("postId")
    val postId: Long,
    @SerializedName("content")
    val content: String
) : Serializable

data class CommDetailCommentWriteResultDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("postId")
    val postId: Long,
    @SerializedName("writerId")
    val writerId: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("regDate")
    val regDate: String,
    @SerializedName("modDate")
    val modDate: String
) : Serializable

// 커뮤니티 상세
data class CommDetailResultDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("writerId")
    val writerId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("regDate")
    val regDate: String,
    @SerializedName("modDate")
    val modDate: String,
    @SerializedName("comments")
    val comments: MutableList<CommDetailCommentsRequest>
) : Serializable

data class CommDetailCommentsRequest(
    @SerializedName("id")
    val id: Long,
    @SerializedName("postId")
    val postId: Long,
    @SerializedName("writerId")
    val writerId: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("regDate")
    val regDate: String,
    @SerializedName("modDate")
    val modDate: String
) : Serializable

// 커뮤니티 글쓰기
data class CommWriteDTO(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String
) : Serializable

data class CommWriteResultDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("writerId")
    val writer: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("regDate")
    val reg_date: String,
    @SerializedName("modDate")
    val mod_date: String
) : Serializable
