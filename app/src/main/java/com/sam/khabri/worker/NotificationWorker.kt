package com.sam.khabri.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sam.khabari.R
import com.sam.khabri.data.KhabriRepository
import com.sam.khabri.data.db.ArticleDao
import com.sam.khabri.data.model.response.Article
import com.sam.khabri.ui.MainActivity
import com.sam.khabri.utils.NOTIFICATION
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext


@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val khabriRepository: KhabriRepository,
    private val articleDao: ArticleDao
) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val newHeadlines = mutableListOf<Article>()
                val savedHeadLines = articleDao.getArticles()
                val savedTitles = arrayListOf<String?>()
                savedHeadLines.forEach {
                    savedTitles.add(it.title)
                }
                khabriRepository.getHeadings("general").collectLatest { headlines ->
                    headlines.forEach {
                        if (!savedTitles.contains(it.title))
                            newHeadlines.add(it)
                    }
//                    newHeadlines.addAll(headlines)
                    articleDao.insertArticle(headlines)
                }
                showNotificationsForNewArticles(newHeadlines)
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }

    private fun showNotificationsForNewArticles(newsList: List<Article>) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "news_channel"
        val channelName = "News Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(channel)

        newsList.takeLast(2).forEach { news ->
            val notificationId = generateNotificationId(news.title.toString())
            val intent = Intent(context, MainActivity::class.java)
                .apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
            intent.putExtra(NOTIFICATION, news.url)

            val notificationPendingIntent = PendingIntent.getActivity(
                context, notificationId, intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
                .setContentTitle(news.title)
                .setAutoCancel(true)
                .setContentText(news.description)
                .setContentIntent(notificationPendingIntent)
                .setSmallIcon(R.drawable.notification_icon)

            notificationManager.notify(notificationId, notificationBuilder.build())
        }
    }

    private fun generateNotificationId(articleId: String): Int {
        return articleId.hashCode()
    }

}