package kr.co.sujungvillage.base

import android.content.Context
import android.widget.Toast

/** 토스트 활성화 확장함수 (LENGTH_SHORT) */
fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}