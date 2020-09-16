package com.bcaf.ivan.finalprojectandroid.Controller

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bcaf.ivan.finalprojectandroid.Controller.Fragment.AgencyFragment
import com.bcaf.ivan.finalprojectandroid.Controller.Fragment.BusFragment
import com.bcaf.ivan.finalprojectandroid.Controller.Fragment.ProfileFragment
import com.bcaf.ivan.finalprojectandroid.Entity.TokenResult
import com.bcaf.ivan.finalprojectandroid.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val gson = GsonBuilder().create()
    private val bundle = Bundle()
    private lateinit var tokenUser: TokenResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tokenResult = intent.getStringExtra("tokenResult")
        tokenUser = gson.fromJson(tokenResult, TokenResult::class.java)

        nav_bottomTab.setOnNavigationItemSelectedListener(this)
    }

    override fun onResume() {
        super.onResume()

        bundle.putString("agencyId", tokenUser.agencyId)

        var busFragment = BusFragment()
        busFragment.arguments = bundle
        loadfragment(busFragment)
    }

    override fun onBackPressed() {
        Toast.makeText(applicationContext, "Log out?", Toast.LENGTH_LONG).show()
    }

    fun loadfragment(fragment: Fragment): Boolean {
        supportFragmentManager
            .beginTransaction().replace(R.id.cont_fragment, fragment)
            .commit()
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment?

        when (item.itemId) {
            R.id.tab_agency -> fragment = AgencyFragment()
            R.id.tab_bus -> fragment = BusFragment()
            R.id.tab_profile -> fragment = ProfileFragment()
            else -> fragment = BusFragment()
        }

        return loadfragment(fragment)
    }
}