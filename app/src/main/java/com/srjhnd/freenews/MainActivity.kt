package com.srjhnd.freenews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.srjhnd.freenews.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        if (supportActionBar == null)
            Timber.d("it is null")
        else
            Timber.d("it is not null")
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.mipmap.logo_fg)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.title = ""

    }
}
