package com.sam.khabri

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class KhabriApplication : Application() {

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var firebaseCrashlytics: FirebaseCrashlytics

    override fun onCreate() {
        super.onCreate()

        WorkManager.initialize(
            this,
            Configuration.Builder().setWorkerFactory(hiltWorkerFactory).build()
        )

        firebaseAnalytics = Firebase.analytics
        firebaseCrashlytics = Firebase.crashlytics

        firebaseAnalytics.setAnalyticsCollectionEnabled(true)
        firebaseCrashlytics.setCrashlyticsCollectionEnabled(true)
    }

}