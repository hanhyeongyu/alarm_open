package com.example.template.notification

import android.Manifest.permission
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.InboxStyle
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.example.tempalte.core.notification.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val MAX_NUM_NOTIFICATIONS = 5
private const val TARGET_ACTIVITY_NAME = "com.example.template.MainActivity"
private const val MESSAGE_NOTIFICATION_REQUEST_CODE = 0
private const val MESSAGE_NOTIFICATION_SUMMARY_ID = 1
private const val MESSAGE_NOTIFICATION_CHANNEL_ID = ""
private const val MESSAGE_NOTIFICATION_GROUP = "MESSAGE_NOTIFICATIONS"
private const val DEEP_LINK_SCHEME_AND_HOST = "https://www.nowinandroid.apps.samples.google.com"
private const val DEEP_LINK_FOR_YOU_PATH = "foryou"
private const val DEEP_LINK_BASE_PATH = "$DEEP_LINK_SCHEME_AND_HOST/$DEEP_LINK_FOR_YOU_PATH"
const val DEEP_LINK_NEWS_RESOURCE_ID_KEY = "linkedNewsResourceId"
const val DEEP_LINK_URI_PATTERN = "$DEEP_LINK_BASE_PATH/{$DEEP_LINK_NEWS_RESOURCE_ID_KEY}"

@Singleton
internal class SystemTrayNotifier @Inject constructor(
    @ApplicationContext private val context: Context,
) : Notifier {


    override fun postNotifications(
        resources: List<String>,
    ) = with(context) {
        if (ActivityCompat.checkSelfPermission(
                this, permission.POST_NOTIFICATIONS
            ) != PERMISSION_GRANTED
        ) {
            return
        }

        val truncatedNewsResources = resources.take(MAX_NUM_NOTIFICATIONS)

        val notifications = truncatedNewsResources.map { resource ->
            createNotification {
                setSmallIcon(R.drawable.core_notifications_ic_mock_notification).setContentTitle(
                        resource
                    ).setContentText(resource)
                    .setContentIntent(newsPendingIntent(resource))
                    .setGroup(MESSAGE_NOTIFICATION_GROUP).setAutoCancel(true)
            }
        }
        val summaryNotification = createNotification {
            val title = getString(
                R.string.core_notifications_messages_notification_group_summary,
                truncatedNewsResources.size,
            )
            setContentTitle(title).setContentText(title)
                .setSmallIcon(R.drawable.core_notifications_ic_mock_notification)
                // Build summary info into InboxStyle template.
                .setStyle(notificationStyle(truncatedNewsResources, title))
                .setGroup(MESSAGE_NOTIFICATION_GROUP).setGroupSummary(true).setAutoCancel(true).build()
        }

        // Send the notifications
        val notificationManager = NotificationManagerCompat.from(this)
        notifications.forEachIndexed { index, notification ->
            notificationManager.notify(
                truncatedNewsResources[index].hashCode(),
                notification,
            )
        }
        notificationManager.notify(MESSAGE_NOTIFICATION_SUMMARY_ID, summaryNotification)
    }

    /**
     * Creates an inbox style summary notification for news updates
     */
    private fun notificationStyle(
        resources: List<String>,
        title: String,
    ): InboxStyle = resources.fold(InboxStyle()) { inboxStyle, resource ->
            inboxStyle.addLine(resource)
        }.setBigContentTitle(title).setSummaryText(title)


}

/**
 * Creates a notification for configured for news updates
 */
private fun Context.createNotification(
    block: NotificationCompat.Builder.() -> Unit,
): Notification {
    ensureNotificationChannelExists()
    return NotificationCompat.Builder(
        this,
        MESSAGE_NOTIFICATION_CHANNEL_ID,
    ).setPriority(NotificationCompat.PRIORITY_DEFAULT).apply(block).build()
}

/**
 * Ensures that a notification channel is present if applicable
 */
private fun Context.ensureNotificationChannelExists() {
    if (VERSION.SDK_INT < VERSION_CODES.O) return

    val channel = NotificationChannel(
        MESSAGE_NOTIFICATION_CHANNEL_ID,
        getString(R.string.core_notifications_messages_notification_channel_name),
        NotificationManager.IMPORTANCE_DEFAULT,
    ).apply {
        description = getString(R.string.core_notifications_messages_notification_channel_description)
    }
    // Register the channel with the system
    NotificationManagerCompat.from(this).createNotificationChannel(channel)
}

private fun Context.newsPendingIntent(
    resource: String,
): PendingIntent? = PendingIntent.getActivity(
    this,
    MESSAGE_NOTIFICATION_REQUEST_CODE,
    Intent().apply {
        action = Intent.ACTION_VIEW
        data = resource.newsDeepLinkUri()
        component = ComponentName(
            packageName,
            TARGET_ACTIVITY_NAME,
        )
    },
    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
)

private fun String.newsDeepLinkUri() = "$DEEP_LINK_BASE_PATH/$this".toUri()
