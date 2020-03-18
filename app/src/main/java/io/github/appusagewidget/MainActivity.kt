package io.github.appusagewidget

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permission_button.setOnClickListener {
            // 権限画面へ
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }

        widget_update_button.setOnClickListener {

        }

        val appUsage = AppUsage(this)
        val appUsageList = appUsage.getUsageStatusList()
        // 多い順に並び替える
        appUsageList?.sortByDescending { it.totalTimeInForeground }
        appUsageList?.forEach {
            println("${it.packageName} ${it.totalTimeInForeground}")
        }

    }


}
