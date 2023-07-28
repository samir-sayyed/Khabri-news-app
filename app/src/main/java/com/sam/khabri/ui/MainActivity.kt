package com.sam.khabri.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.sam.khabari.R
import com.sam.khabri.data.model.response.Article
import com.sam.khabri.utils.NOTIFICATION
import com.sam.khabri.worker.NotificationWorker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url = intent.getStringExtra(NOTIFICATION)
        if (!url.isNullOrEmpty()){
            navigateToNewsDetail(url)
        }
    }

    private fun navigateToNewsDetail(url: String?) {
        val article = Article(id = 1, url = url)
        navigate(
            HomeFragment::class.java,
            HomeFragmentDirections.actionHomeFragmentToArticleDetailsFragment(article)
        )
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val url = intent?.getStringExtra(NOTIFICATION)
        if (!url.isNullOrEmpty()){
            navigateToNewsDetail(url)
        }


    }

    /**
     * Method to navigate fragment
     * @param from class from which the navigation has to happen
     * @param direction nav direction
     */
    private fun navigate(from: Class<out Fragment>, direction: NavDirections) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        if (navHostFragment != null && navHostFragment.childFragmentManager.fragments.isNotEmpty()
            && from.isInstance(navHostFragment.childFragmentManager.fragments.first())
        ) {
            navHostFragment.findNavController().navigate(direction)
        }
    }


}