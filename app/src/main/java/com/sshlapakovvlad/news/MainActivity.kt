package com.sshlapakovvlad.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.tabs.TabLayoutMediator
import com.sshlapakovvlad.news.repositories.SharedPrefsUserDataRepository
import com.sshlapakovvlad.news.settings.SettingsActivity
import com.sshlapakovvlad.news.tabs.TabsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupTabLayout()
    }

    private fun setupTabLayout() {
        val categories = SharedPrefsUserDataRepository(
            applicationContext.getSharedPreferences("userPrefs", Context.MODE_PRIVATE),
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        ).readUserCategories()
        viewPager.adapter = TabsPagerAdapter(this, categories)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = categories[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_setting) {
            val intent = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
