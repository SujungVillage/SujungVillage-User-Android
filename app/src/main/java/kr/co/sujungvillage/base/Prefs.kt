package kr.co.sujungvillage.base

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Prefs(context: Context) {
    private val prefNm = "SujungVillage"
    private val prefs = context.getSharedPreferences(prefNm, MODE_PRIVATE)

    var token: String?
        get() = prefs.getString("token", null)
        set(value) {
            prefs.edit().putString("token", value).apply()
        }

    var refresh: String?
        get() = prefs.getString("refresh", null)
        set(value) {
            prefs.edit().putString("refresh", value).apply()
        }

    var id: String?
        get() = prefs.getString("studentNum", null)
        set(value) {
            prefs.edit().putString("studentNum", value).apply()
        }
}
