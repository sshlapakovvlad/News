package com.sshlapakovvlad.news.settings

import android.content.Context
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.sshlapakovvlad.news.R
import com.sshlapakovvlad.news.repositories.SharedPrefsUserDataRepository

open class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        if (preference.key.startsWith("category_")) {
            val prefs = SharedPrefsUserDataRepository(
                requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE),
                PreferenceManager.getDefaultSharedPreferences(requireContext())
            )
            val newVal = (preference as CheckBoxPreference).isChecked
            prefs.writeUserCategories(preference.key, newVal)
        }
        return super.onPreferenceTreeClick(preference)
    }
}
