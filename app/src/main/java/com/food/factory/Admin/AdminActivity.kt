package com.food.factory.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.food.factory.Admin.Fragments.AddHotel
import com.food.factory.Admin.Fragments.HotelList
import com.food.factory.Admin.Fragments.OrderList
import com.food.factory.Admin.Fragments.ViewPagerAdapter.AdminViewPagerAdapter
import com.food.factory.MainActivity
import com.food.factory.R
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth

class AdminActivity : AppCompatActivity() {

    private lateinit var pager: ViewPager // creating object of ViewPager
    private lateinit var tab: TabLayout  // creating object of TabLayout
    private lateinit var bar: Toolbar    // creating object of ToolBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        // set the references of the declared objects above
        pager = findViewById(R.id.viewPager)
        tab = findViewById(R.id.tabs)


        // Initializing the ViewPagerAdapter
        val adapter = AdminViewPagerAdapter(supportFragmentManager)

        // add fragment to the list
        adapter.addFragment(AddHotel(), "Add Hotel")
        adapter.addFragment(HotelList(), "Hotel List")
        adapter.addFragment(OrderList(), "Order List")

        // Adding the Adapter to the ViewPager
        pager.adapter = adapter

        // bind the viewPager with the TabLayout.
        tab.setupWithViewPager(pager)
    }

    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut();
        val intent = Intent(this , MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}