package dev.chu.darkmodesample

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat

/**
 * 참고 : https://proandroiddev.com/daynight-applying-dark-mode-without-recreating-your-app-c8a62d51092d
 */

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
        const val DAY = 1
        const val NIGHT = 2
    }

    private var defaultFlag = 1
    private var nightFlag = 1

    private val container: LinearLayout by lazy { findViewById(R.id.container) }
    private val tvTitle: TextView by lazy { findViewById(R.id.tvTitle) }
    private val switcher: SwitchCompat by lazy { findViewById(R.id.switcher) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate, ${window.decorView.systemUiVisibility}")

        defaultFlag = window.decorView.systemUiVisibility
        applyDayNight(DAY)

        switcher.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart, ${window.decorView.systemUiVisibility}")
//        applyDayNight()
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume, ${window.decorView.systemUiVisibility}")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.i(TAG, "onConfigurationChanged")

        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        Log.i(TAG, "nightModeFlags = $nightModeFlags == ${Configuration.UI_MODE_NIGHT_NO}")
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO) {
            applyDayNight(DAY)
        } else {
            applyDayNight(NIGHT)
        }
    }

    private fun applyDayNight(state: Int) {
        Log.i(TAG, "applyDayNight state = $state")
        nightFlag = state
        if (state == DAY) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.apply {
                    systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
            } else {
                window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryNight)
            }

            tvTitle.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            container.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        } else {
            window.decorView.systemUiVisibility = defaultFlag
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryNight)
            tvTitle.setTextColor(ContextCompat.getColor(this, R.color.colorTextNight))
            container.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryNight))
        }
    }
}