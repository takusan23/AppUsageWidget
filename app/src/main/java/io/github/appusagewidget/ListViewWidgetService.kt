package io.github.appusagewidget

import android.app.usage.UsageStats
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Icon
import android.media.Image
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.graphics.drawable.toBitmap
import java.text.SimpleDateFormat
import java.util.*


class ListViewWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return ListViewWidgetFactory()
    }

    private inner class ListViewWidgetFactory() : RemoteViewsFactory {

        var appUsageList = mutableListOf<UsageStats>()

        override fun onCreate() {

        }

        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun onDataSetChanged() {
            // データ取得
            val appUsage = AppUsage(applicationContext)
            if (appUsage.getUsageStatusList() != null) {
                appUsageList = appUsage.getUsageStatusList()!!
            }
            // 並び替え
            appUsageList.sortByDescending { it.totalTimeInForeground }
        }

        override fun hasStableIds(): Boolean {
            return true
        }

        override fun getViewAt(position: Int): RemoteViews {
            val usage = appUsageList[position]
            val remoteViews =
                RemoteViews(packageName, R.layout.listview_item_layout)
            // 時間
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = usage.totalTimeInForeground
            calendar.add(Calendar.HOUR_OF_DAY, -9)
            val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
            remoteViews.setTextViewText(
                R.id.adapter_textview,
                simpleDateFormat.format(calendar.timeInMillis)
            )
            // アイコン
            val bitmap = applicationContext?.packageManager?.getApplicationIcon(usage.packageName)
                ?.toBitmap(100, 100)
            remoteViews.setImageViewBitmap(
                R.id.adapter_imageView,
                bitmap
            )
            return remoteViews
        }

        override fun getCount(): Int {
            return appUsageList.size
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun onDestroy() {

        }
    }
}
