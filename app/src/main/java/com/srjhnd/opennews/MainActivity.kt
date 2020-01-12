package com.srjhnd.opennews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.srjhnd.opennews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        if (supportActionBar == null)
            Log.d("SRJ", "it is null")
        else
            Log.d("SRJ", "it is not null")
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.mipmap.logo_fg)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.title = ""

    }
}
