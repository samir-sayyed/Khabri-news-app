package com.sam.khabri.ui

import androidx.core.widget.doAfterTextChanged
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.tabs.TabLayoutMediator
import com.sam.khabari.R
import com.sam.khabari.databinding.FragmentHomeBinding
import com.sam.khabri.base.BaseFragment
import com.sam.khabri.ui.adapter.HomeTabAdapter
import com.sam.khabri.worker.NotificationWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {


    companion object {
        private const val TAG = "HomeFragment"
    }

    private var mHomeTabAdapter: HomeTabAdapter? = null

    override fun setUpViews() {
        super.setUpViews()
        (requireActivity() as MainActivity).supportActionBar?.hide()
        initPeriodicNotification()
        initTabLayout()
    }

    private fun initTabLayout() {
        val tabTextArray = arrayOf(
            "General",
            "Business",
            "Entertainment",
            "Health",
            "Science",
            "Sports",
            "Technology"
        )
        mHomeTabAdapter = HomeTabAdapter(
            childFragmentManager, viewLifecycleOwner.lifecycle,
            tabTextArray
        )
        viewBinding?.viewpager?.adapter = mHomeTabAdapter

        TabLayoutMediator(viewBinding?.tabLayout!!, viewBinding?.viewpager!!) { tab, position ->
            tab.text = tabTextArray[position]
        }.attach()
    }

    private fun initPeriodicNotification() {
        val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            NotificationWorker::class.java,
            1,
            TimeUnit.HOURS
        )
            .setInitialDelay(15, TimeUnit.MINUTES)
            .setConstraints(myConstraints)
            .addTag(TAG)
            .build()

        WorkManager.getInstance(requireContext())
            .enqueueUniquePeriodicWork(
                TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )
    }


    override fun viewModelClass() = HomeViewModel::class.java
    override val layoutId: Int = R.layout.fragment_home

}