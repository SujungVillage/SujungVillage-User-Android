package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FaqGetResultDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("writerId")
    val userId: String,
    @SerializedName("question")
    val question: String,
    @SerializedName("answer")
    val answer: String,
    @SerializedName("regDate")
    val date: String,
    @SerializedName("modDate")
    val modDate: String,
): Serializable {}

data class MyqGetResultDTO(
    @SerializedName("questionId")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("redDate")
    val date: String,
    @SerializedName("answered")
    val isAnswered: Boolean,
): Serializable {}

data class MyqDetailGetResultDTO(
    @SerializedName("question")
    val question: QnAQuestion,
    @SerializedName("answer")
    val answer: QnAAnswer,
): Serializable {}

data class QnAQuestion(
    @SerializedName("id")
    val id: Long,
    @SerializedName("writerId")
    val userId: String,
    @SerializedName("writerName")
    val name: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("anonymous")
    val isAnonymous: Boolean,
    @SerializedName("reqDate")
    val reqDate: String,
    @SerializedName("modDate")
    val modDate: String,
): Serializable {}

data class QnAAnswer(
    @SerializedName("id")
    val id: Long,
    @SerializedName("writerName")
    val name: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("regDate")
    val regDate: String,
    @SerializedName("modDate")
    val modDate: String,
): Serializable {}

data class MyqWriteDTO(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("anonymous")
    val isAnonymous: Boolean,
): Serializable {}

data class MyqWriteResultDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("writerId")
    val userId: String,
    @SerializedName("writerName")
    val name: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("anonymous")
    val isAnonymous: Boolean,
    @SerializedName("reqDate")
    val reqDate: String,
    @SerializedName("modDate")
    val modDate: String,
): Serializable {}