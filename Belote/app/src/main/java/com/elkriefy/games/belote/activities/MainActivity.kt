package com.elkriefy.games.belote.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.elkriefy.games.belote.R
import com.elkriefy.games.belote.fragments.SplashFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragment, SplashFragment()).commit()
    }
}
