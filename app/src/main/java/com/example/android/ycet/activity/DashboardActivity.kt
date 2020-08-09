package com.example.android.ycet.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.android.ycet.*
import com.example.android.ycet.classes.FireStore
import com.example.android.ycet.fragment.*
import com.example.android.ycet.models.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.dashboardactivity.*
import kotlinx.android.synthetic.main.nav_header.*

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    // A global variable for User Name
    private lateinit var mUserName: String
    lateinit var auth: FirebaseAuth
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
companion object{
    const val MY_PROFILE_REQUEST_CODE:Int = 11
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboardactivity)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)



        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.app_name, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        auth = FirebaseAuth.getInstance()
        FireStore().signInUser(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK
            && requestCode == MY_PROFILE_REQUEST_CODE
        ) {
            // Get the user updated details.
            FireStore().signInUser(this@DashboardActivity)
        }
        else {
            Log.e("Cancelled", "Cancelled")
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.linearlayout,
                    HomeFragment()
                ).commit()
            }
            R.id.nav_task -> {
                val intent = Intent(this,TaskHome::class.java)
                startActivity(intent)
            }
            R.id.nav_profile -> {

            startActivityForResult(
                Intent(this@DashboardActivity, ProfileActivity::class.java), MY_PROFILE_REQUEST_CODE
               // MY_PROFILE_REQUEST_CODE
            )
        }

            R.id.nav_documents -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.linearlayout,
                    DocumentsFragment()
                ).commit()
            }

            R.id.nav_attendance -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.linearlayout,
                    AttendanceFragment()
                ).commit()
            }

            R.id.nav_logout -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
                auth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

            R.id.nav_settings -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.linearlayout,
                    SettingsFragment()
                ).commit()
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)

        return true

    }
    fun updateNavigationUserDetails(user: User) {


        mUserName = user.firstName

        // The instance of the header view of the navigation view.
        val headerView = nav_view.getHeaderView(0)

        // The instance of the user image of the navigation view.
        val navUserImage = headerView.findViewById<ImageView>(R.id.iv_user_image)

        // Load the user image in the ImageView.
        Glide
            .with(this@DashboardActivity)
            .load(user.image) // URL of the image
            .centerCrop() // Scale type of the image.
            .placeholder(R.drawable.ic_user_place_holder) // A default place holder
            .into(navUserImage) // the view in which the image will be loaded.

        // The instance of the user name TextView of the navigation view.
        val navUsername = headerView.findViewById<TextView>(R.id.tv_username)
        // Set the user name
        navUsername.text = user.firstName


    }
}