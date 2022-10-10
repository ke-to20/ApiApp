package jp.techacademy.keito.nagata.apiapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import jp.techacademy.keito.nagata.apiapp.FavoriteShop.Companion.delete
import jp.techacademy.keito.nagata.apiapp.FavoriteShop.Companion.findBy
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.fragment_api.*


class WebViewActivity : AppCompatActivity(){

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

        Log.d("apiApp", "WebViewActivity onCreate" + star_ans)

        if (star_ans == null) {
//            お気に入りでは無いときの処理
            favoriteImageView1.setImageResource(R.drawable.ic_star_border)

        } else {
//                お気に入りに追加されているときの処理
            favoriteImageView1.setImageResource(R.drawable.ic_star)

        }

        favoriteImageView1.setOnClickListener {
            // EditTextの文字列をTextViewに設定
            Log.d("apiApp", "☆マークがクリックされたよ")

            if (star_ans == null) {
//            お気に入りでは無いときの処理
                favoriteImageView1.setImageResource(R.drawable.ic_star)
                star_ans = "star"

//                お気に入りに追加
                webAdd()




            } else {
//                お気に入りに追加されているときの処理
                favoriteImageView1.setImageResource(R.drawable.ic_star_border)
                star_ans = null

//                お気に入り削除
                webDel()
            }

        }

    }

    override fun onStop(){
        super.onStop()
        Log.d("apiApp", "onStop")
    }



    companion object {

        private var star_ans: String? = null

        private var web_id: String? = null
        private var web_name: String? = null
        private var web_imageUrl: String? = null
        private var web_url: String? = null
        private var web_address: String? = null

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

        fun webview_star_draw(Web_list: MutableList<String>) {
            for (i in 0..4) {
                Log.d("apiApp", "webview_star_draw web_list = " + Web_list[i])
            }

            web_id = Web_list[0]
            web_name = Web_list[1]
            web_imageUrl = Web_list[2]
            web_url = Web_list[3]
            web_address = Web_list[4]

//            お気に入り判定
            var Web_favorite = findBy(Web_list[0])

            if (Web_favorite.toString() == "null") {
//            お気に入りでは無いときの処理
                Log.d("apiApp", "お気に入りではない")
                star_ans = null

            } else {
//                お気に入りに追加されているときの処理
                Log.d("apiApp", "お気に入り")
                star_ans = Web_favorite.toString()
            }


        }

        //        WebView上でお気に入りに追加
        fun webAdd() {
            Log.d("apiApp", "webAdd")

            FavoriteShop.insert(FavoriteShop().apply {
                id = web_id.toString()
                name = web_name.toString()
                imageUrl = web_imageUrl.toString()
                url = web_url.toString()
                address = web_address.toString()
            })
        }

        //        WebView上でお気に入りから削除
        fun webDel() {
            Log.d("apiApp", "webDel")


            FavoriteShop.delete(web_id.toString())

        }



    }


}