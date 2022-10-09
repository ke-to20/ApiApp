package jp.techacademy.keito.nagata.apiapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import jp.techacademy.keito.nagata.apiapp.FavoriteShop.Companion.findBy
import kotlinx.android.synthetic.main.activity_web_view.*


class WebViewActivity : AppCompatActivity() {

    // 一覧画面から登録するときのコールバック（FavoriteFragmentへ通知するメソッド)
    var onClickAddFavorite: ((Shop) -> Unit)? = null

    // 一覧画面から削除するときのコールバック（ApiFragmentへ通知するメソッド)
    var onClickDeleteFavorite: ((Shop) -> Unit)? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView.loadUrl(intent.getStringExtra(KEY_URL).toString())

        Log.d("apiApp", "WebViewActivity onCreate")

        favoriteImageView1.setImageResource(R.drawable.ic_star_border)


    }

    companion object {
        private const val KEY_URL = "key_url"
        fun start(activity: Activity, url: String) {

            Log.d("apiApp", "WebViewActivity start url = " + url)

            activity.startActivity(
                Intent(activity, WebViewActivity::class.java).putExtra(
                    KEY_URL,
                    url
                )
            )

        }

//        wevViiewの☆マーク判定

        fun webview_star_draw(Web_list: MutableList<String>){
            for (i in 0..4) {
                Log.d("apiApp", "webview_star_draw web_list = " + Web_list[i])
            }
            Log.d("apiApp", "webview_star_draw web_list サイズ= " + Web_list.size.toString())

//            お気に入り判定
            var Web_favorite = findBy(Web_list[0])

            if (Web_favorite.toString() == "null") {
//            お気に入りでは無いときの処理

                Log.d("apiApp", "お気に入りではない")
                favoriteImageView1.setImageResource(R.drawable.ic_star)

            } else {
//                お気に入りに追加されているときの処理

                Log.d("apiApp", "お気に入り")
                favoriteImageView1.setImageResource(R.drawable.ic_star_border)
            }

        }


    }


}