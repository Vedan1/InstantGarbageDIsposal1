package com.example.instantgarbagedisposal
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.instantgarbagedisposal.fragments.PostIssue_Fragment
import com.example.instantgarbagedisposal.fragments.SettingsFragment
import com.example.instantgarbagedisposal.fragments.home_Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity() {
    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val issueButton : Button = findViewById(R.id.issueButton)
        val homeFragment = home_Fragment()
        val postissueFragment = PostIssue_Fragment()
        val settingsFragment = SettingsFragment()

        makeCurrentFragment(homeFragment)

//        issueButton.setOnClickListener {
//            val intent: Intent = Intent(
//                this,
//                IssueActivity::class.java
//            )
//
//            startActivity(intent)
//        }
        val   bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->makeCurrentFragment(homeFragment)
                R.id.issuePost->makeCurrentFragment(postissueFragment)
                R.id.settings->makeCurrentFragment(settingsFragment)


            }
            true
    }

}}