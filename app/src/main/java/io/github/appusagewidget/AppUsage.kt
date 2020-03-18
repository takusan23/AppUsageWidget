package io.github.appusagewidget

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import java.util.*

class AppUsage(val context: Context?) {

    fun getUsageStatusList(): MutableList<UsageStats>? {
        val usageStatsManager =
            context?.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        // 時間設定
        val calendar = Calendar.getInstance()
        val start = Calendar.getInstance()
        start.set(Calendar.HOUR_OF_DAY, 0)
        return usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            start.timeInMillis,
            System.currentTimeMillis()
        )
    }

}