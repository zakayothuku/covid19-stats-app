package com.schatzdesigns.covid19stats.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.schatzdesigns.covid19stats.BaseActivity
import com.schatzdesigns.covid19stats.R
import com.schatzdesigns.covid19stats.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000L)
    }
}