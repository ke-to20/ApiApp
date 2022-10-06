package jp.techacademy.keito.nagata.apiapp
import android.app.Application
import android.util.Log
import io.realm.Realm

class ApiApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        Log.d("apiApp", "ApiApplication onCreate Realmの初期化")
    }
}