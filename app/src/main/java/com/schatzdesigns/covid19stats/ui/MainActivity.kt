package com.schatzdesigns.covid19stats.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.schatzdesigns.covid19stats.BaseActivity
import com.schatzdesigns.covid19stats.R
import com.schatzdesigns.covid19stats.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    fun updateTitle(title: String) {
        binding.title.text = title
    }

}
