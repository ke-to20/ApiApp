package jp.techacademy.keito.nagata.apiapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.*


class WebViewActivity: AppCompatActivity() {

//    ＝＝＝＝＝　お気に入りボタン　追加　＝＝＝＝＝

    // 一覧画面から登録するときのコールバック（FavoriteFragmentへ通知するメソッド)
    var onClickAddFavorite: ((Shop) -> Unit)? = null

    // 一覧画面から削除するときのコールバック（ApiFragmentへ通知するメソッド)
    var onClickDeleteFavorite: ((Shop) -> Unit)? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView.loadUrl(intent.getStringExtra(KEY_URL).toString())

        Log.d("apiApp", "WebViewActivity onCreate"+ KEY_URL)
    }

    companion object {
        private const val KEY_URL = "key_url"
        fun start(activity: Activity, url: String) {

            Log.d("apiApp", "WebViewActivity start"+ url)

            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_URL, url))
            Log.d("apiApp", "WebViewActivity start"+ KEY_URL)

        }


    }
}