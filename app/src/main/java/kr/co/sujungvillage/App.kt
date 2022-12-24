package kr.co.sujungvillage

import android.app.Application
import kr.co.sujungvillage.base.Prefs

class App : Application() {
    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var prefs: Prefs
        lateinit var context: App
    }
}
