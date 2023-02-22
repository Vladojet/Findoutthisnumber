package com.example.findoutthisnumber.presentation.activity

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.findoutthisnumber.R
import com.example.findoutthisnumber.databinding.ActivityMainBinding
import com.example.findoutthisnumber.presentation.game.minigame.GameFragment
import com.example.findoutthisnumber.presentation.game.rules.RulesFragment
import com.example.findoutthisnumber.presentation.game.scores.ScoresFragment
import com.example.findoutthisnumber.presentation.web.IOnBackPressed
import com.example.findoutthisnumber.presentation.web.WebViewFragment
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
const val MYSHAREDPREF = "saved place"
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    companion object {
        const val REDIRECTIONSTATE = "REDIRECTIONSTATE"
        const val WEBVIEWLINK = "WEBVIEWLINK"
        const val ISSAVED ="ISSAVED"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRemoteConfig()
        initFragment()
    }

    private fun saveInSharedPref(status: Boolean, link: String) {
        val sharedPref = getSharedPreferences(MYSHAREDPREF, MODE_PRIVATE)
        val editor = sharedPref.edit {
            putBoolean(REDIRECTIONSTATE, status)
            putString(WEBVIEWLINK, link)
            putBoolean(ISSAVED, true)
        }
        checkStatus(status, link)
    }

    private fun isLoaded(): Boolean {
        val isSaved = getSharedPreferences(MYSHAREDPREF, MODE_PRIVATE)
        return isSaved.getBoolean(ISSAVED, false)
    }


    private fun checkStatus(status: Boolean, link: String) {
        if (status) {
            startTransaction(WebViewFragment.newInstance(link))
            initNewConstraints()
        } else startTransaction(GameFragment.newInstance())
    }

    private fun initNewConstraints() {
        binding.bottomNavigationBar.visibility = View.INVISIBLE
        binding.fragmentHolder.layoutParams.height = LayoutParams.MATCH_PARENT
    }

    private fun initRemoteConfig() {
        Firebase.initialize(this)
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 5
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                if (!isLoaded()) {
                    var link = remoteConfig.getString("link")
                    var status = remoteConfig.getBoolean("status")
                    saveInSharedPref(status, link)
                } else {
                    changePref(true)
                }
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun changePref(status: Boolean) {
        val sharedPref = getSharedPreferences(MYSHAREDPREF, MODE_PRIVATE)
        val editor = sharedPref.edit {
            putBoolean(REDIRECTIONSTATE, status)
        }
        sharedPref.getString(WEBVIEWLINK, "A")?.let { checkStatus(status, it) }
    }

    private fun initFragment() {
        binding.bottomNavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.gameMenuItem -> {
                    startTransaction(GameFragment.newInstance())
                    true
                }
                R.id.rulesMenuItem -> {
                    startTransaction(RulesFragment.newInstance())
                    true
                }
                R.id.scoresMenuItem -> {
                    startTransaction(ScoresFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

    private fun startTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolder, fragment)
            .commit()
    }

    override fun onBackPressed() {
        (this.supportFragmentManager.fragments.last() as? IOnBackPressed?)?.onBackPressed()
            ?: onBackPressedDispatcher.onBackPressed()
    }
}
