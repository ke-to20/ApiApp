package jp.techacademy.keito.nagata.apiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FragmentCallback {


    private val viewPagerAdapter by lazy { ViewPagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ViewPager2の初期化
        viewPager2.apply {
            adapter = viewPagerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL // スワイプの向き横（ORIENTATION_VERTICAL を指定すれば縦スワイプで実装可能です）
            offscreenPageLimit = viewPagerAdapter.itemCount // ViewPager2で保持する画面数
        }

        // TabLayoutの初期化
        // TabLayoutとViewPager2を紐づける
        // TabLayoutのTextを指定する
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.setText(viewPagerAdapter.titleIds[position])
        }.attach()
    }

    override fun onClickItem(shop: Shop) {

        var url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc


        WebViewActivity.start(this, url)

        Log.d("apiApp", "MainActivity onClickItem url = " + url)
        Log.d("apiApp", "MainActivity shop = " + shop.toString())
        Log.d("apiApp", "MainActivity shop の型 " + shop.javaClass.toString())


    }

    override fun onClickItem(shop: FavoriteShop) {

//        var url = shop.url




        WebViewActivity.start(this, shop.url)
        Log.d("apiApp", "MainActivity shop = " + shop.toString())
        Log.d("apiApp", "MainActivity onClickItem ID = " + shop.id.toString())
        Log.d("apiApp", "MainActivity onClickItem name = " + shop.name)
        Log.d("apiApp", "MainActivity onClickItem url = " + shop.url)
        Log.d("apiApp", "MainActivity onClickItem address = " + shop.address)
        Log.d("apiApp", "MainActivity onClickItem imageUrl = " + shop.imageUrl)
        Log.d("apiApp", "MainActivity shop の型 " + shop.javaClass.toString())

    }

    override fun onAddFavorite(shop: Shop) { // Favoriteに追加するときのメソッド(Fragment -> Activity へ通知する)
        Log.d("apiApp", "MainActivityで追加処理完了")

        FavoriteShop.insert(FavoriteShop().apply {
            id = shop.id
            name = shop.name
            imageUrl = shop.logoImage
            url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc

//            ＝＝＝＝＝　住所　追加　＝＝＝＝＝
            address = shop.address
        })
        (viewPagerAdapter.fragments[VIEW_PAGER_POSITION_FAVORITE] as FavoriteFragment).updateData()
    }

    override fun onDeleteFavorite(id: String) { // Favoriteから削除するときのメソッド(Fragment -> Activity へ通知する)
        Log.d("apiApp", "MainActivityで削除処理スタート")
        showConfirmDeleteFavoriteDialog(id)
    }

    private fun showConfirmDeleteFavoriteDialog(id: String) {

        Log.d("apiApp", "MainActivityで削除処理確認")
        AlertDialog.Builder(this)
            .setTitle(R.string.delete_favorite_dialog_title)
            .setMessage(R.string.delete_favorite_dialog_message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                deleteFavorite(id)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->}
            .create()
            .show()
    }

    private fun deleteFavorite(id: String) {
        Log.d("apiApp", "MainActivityで削除処理完了")
        FavoriteShop.delete(id)
        (viewPagerAdapter.fragments[VIEW_PAGER_POSITION_API] as ApiFragment).updateView()
        (viewPagerAdapter.fragments[VIEW_PAGER_POSITION_FAVORITE] as FavoriteFragment).updateData()
    }

    companion object {
        private const val VIEW_PAGER_POSITION_API = 0
        private const val VIEW_PAGER_POSITION_FAVORITE = 1
    }
}