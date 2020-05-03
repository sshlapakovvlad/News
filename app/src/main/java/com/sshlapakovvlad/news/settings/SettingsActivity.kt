package com.sshlapakovvlad.news.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sshlapakovvlad.news.MainActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
    }

    override fun onBackPressed() {
        finish()
        val intent = Intent(this@SettingsActivity, MainActivity::class.java)
        startActivity(intent)
    }
}
