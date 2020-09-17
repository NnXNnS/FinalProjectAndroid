package com.bcaf.ivan.finalprojectandroid.Controller

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bcaf.ivan.finalprojectandroid.Controller.Fragment.AgencyFragment
import com.bcaf.ivan.finalprojectandroid.Controller.Fragment.BusFragment
import com.bcaf.ivan.finalprojectandroid.Controller.Fragment.ProfileFragment
import com.bcaf.ivan.finalprojectandroid.Helper.CustomActivity
import com.bcaf.ivan.finalprojectandroid.Helper.SessionManager
import com.bcaf.ivan.finalprojectandroid.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val bundle = Bundle()
    private lateinit var sessionManager: SessionManager
    private lateinit var activity:CustomActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sessionManager= SessionManager(applicationContext)
        activity= CustomActivity(this)
        nav_bottomTab.setOnNavigationItemSelectedListener(this)
        nav_bottomTab.selectedItemId = R.id.tab_bus
    }

    override fun onResume() {
        super.onResume()
        var busFragment = BusFragment()
        busFragment.arguments = bundle
        loadfragment(busFragment)
    }

    override fun onBackPressed() {
        showDialog()
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

    private fun showDialog() {
        var alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Are you sure,You wanted to logout?")
        alertDialogBuilder.setPositiveButton("Yes") { _: DialogInterface, i: Int ->
            sessionManager.removeSession()
            activity.startAndDestroy(LoginActivity::class.java)
        }

        alertDialogBuilder.setNegativeButton("No") { dialog: DialogInterface, i: Int ->
            dialog.cancel()
        }

        var alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
