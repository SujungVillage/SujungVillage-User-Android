package kr.co.sujungvillage.base

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayOutputStream


// 키보드 내리기 메서드
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

// extension function to convert bitmap to byte array
fun Bitmap.toByteArray():ByteArray{
    ByteArrayOutputStream().apply {
        compress(Bitmap.CompressFormat.JPEG,100,this)
        return toByteArray()
    }
}


// extension function to convert byte array to bitmap
fun ByteArray.toBitmap():Bitmap{
    return BitmapFactory.decodeByteArray(this,0,size)
}