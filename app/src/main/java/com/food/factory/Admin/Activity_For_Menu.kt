package com.food.factory.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.food.factory.Admin.Fragments.*
import com.food.factory.Admin.Fragments.ViewPagerAdapter.AdminViewPagerAdapter
import com.food.factory.Admin.Fragments.ViewPagerAdapter.MenuAdapterr
import com.food.factory.R
import com.google.android.material.tabs.TabLayout

class Activity_For_Menu : AppCompatActivity() {
    private lateinit var pager: ViewPager // creating object of ViewPager
    private lateinit var tab: TabLayout  // creating object of TabLayout
    private lateinit var bar: Toolbar    // creating object of ToolBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_menu)

        pager = findViewById(R.id.viewPager)
        tab = findViewById(R.id.tabs)


        // Initializing the ViewPagerAdapter
        val adapter = MenuAdapterr(supportFragmentManager)
        // add fragment to the list
//        adapter.addFragment(AddHotel(), "Add Hotel")
        adapter.addFragment(AddMenu(), "Add Dish List")
        adapter.addFragment(MenuList(), "Dish List")

        // Adding the Adapter to the ViewPager
        pager.adapter = adapter

        // bind the viewPager with the TabLayout.
        tab.setupWithViewPager(pager)

    }
}