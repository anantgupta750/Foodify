package com.food.factory.Admin.Fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.food.factory.AddSubMenu
import com.food.factory.Admin.Fragments.ViewPagerAdapter.AdminViewPagerAdapter
import com.food.factory.R
import com.google.android.material.tabs.TabLayout

class HotelDetail : AppCompatActivity() {

    private lateinit var pager: ViewPager // creating object of ViewPager
    private lateinit var tab: TabLayout  // creating object of TabLayout
    private lateinit var bar: Toolbar    // creating object of ToolBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_detail)

        pager = findViewById(R.id.viewPagerSecond)
        tab = findViewById(R.id.tabsSecond)


        val adapter = AdminViewPagerAdapter(supportFragmentManager)

        // add fragment to the list
        adapter.addFragment(EditHotelDetail(), "QR Code")
//        adapter.addFragment(AddMenu(), "Add Menu")
        adapter.addFragment(Sub_Menu_List(), "Sub  Menu  List")
//        adapter.addFragment(MenuList(), "Menu List")
        adapter.addFragment(AddSubMenu(), "Add Sub Menu")

        // Adding the Adapter to the ViewPager
        pager.adapter = adapter

        // bind the viewPager with the TabLayout.
        tab.setupWithViewPager(pager)

    }
}