package com.sam.khabri.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sam.khabri.ui.NewsHeadingListFragment
import java.util.Locale

private const val TAB_COUNT = 7

class HomeTabAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val categoryArray: Array<String>
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return NewsHeadingListFragment.newInstance(categoryArray[position].lowercase(Locale.ROOT))
    }

}