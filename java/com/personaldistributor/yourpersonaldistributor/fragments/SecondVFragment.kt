package com.personaldistributor.yourpersonaldistributor.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import com.personaldistributor.yourpersonaldistributor.R
import com.personaldistributor.yourpersonaldistributor.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_third.*

/**
 * A simple [Fragment] subclass.
 */
class SecondVFragment : Fragment() {
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myFragment = inflater.inflate(R.layout.fragment_second_v, container, false)

        viewPager = myFragment.findViewById<ViewPager>(R.id.viewPager)
        tabLayout = myFragment.findViewById<TabLayout>(R.id.tabs)
        return myFragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpTabs()

    }
    private fun setUpTabs(){
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(CurrentVProduct(),"Current Product")
        adapter.addFragment(HistoryVProduct(),"History")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)
        tabs.getTabAt(1)


    }
}

