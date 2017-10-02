package com.fuseteam.games.belote.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fuseteam.games.belote.R
import com.fuseteam.games.belote.fragments.MainActivityFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragment, MainActivityFragment()).commit()
    }
}
