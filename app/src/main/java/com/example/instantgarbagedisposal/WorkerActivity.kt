package com.example.instantgarbagedisposal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.instantgarbagedisposal.workerfragments.workerHome
import com.example.instantgarbagedisposal.workerfragments.workerProfile
import com.google.android.material.bottomnavigation.BottomNavigationView

class WorkerActivity : AppCompatActivity() {

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.worker_fl_wrapper, fragment)
            commit()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker)

        val homeFragment: Fragment = workerHome()
        val profileFragment: Fragment = workerProfile()

        makeCurrentFragment(homeFragment)

        val bottom_nav = findViewById<BottomNavigationView>(R.id.worker_bottom_nav)
        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.worker_home -> makeCurrentFragment(homeFragment)
                R.id.worker_profile -> makeCurrentFragment(profileFragment)


            }
            true
        }
    }
}