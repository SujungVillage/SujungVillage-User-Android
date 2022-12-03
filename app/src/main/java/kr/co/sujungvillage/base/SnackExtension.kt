package kr.co.sujungvillage.base

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

/** 스낵바 활성화 확장함수 (LENGTH_SHORT) */
fun Context.showSnackbar(view: View, msg: String) {
    Snackbar.make(view, msg, Toast.LENGTH_SHORT).show()
}
