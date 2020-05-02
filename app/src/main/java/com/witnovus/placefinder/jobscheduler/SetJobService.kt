package com.witnovus.placefinder.jobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast

class SetJobService constructor(val context: Context, val time: Long, val notificationMessage: String,val jobId:Int) {

    var componentname: ComponentName? = null
    var scheduler: JobScheduler? = null

    init {
        if (componentname == null && scheduler == null) {
            componentname = ComponentName(context, NotificationService::class.java)
            scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            setScheduler(context, time, notificationMessage,jobId)
        }
    }

    private fun setScheduler(
        context: Context,
        time: Long,
        notificationMessage: String,
        jobId: Int
    ) {

        var bundle: PersistableBundle = PersistableBundle()
        bundle.putString("notificationMessage", notificationMessage)

        if (componentname != null && scheduler != null) {
            Log.d("hour", "time" + time)

                var jobInfo: JobInfo? = JobInfo.Builder(jobId, componentname!!)
                    .setRequiresCharging(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .setPersisted(true)
                    .setExtras(bundle)
                    .setMinimumLatency(time)
                    .setOverrideDeadline(time)
                    .build()

                val resultCode = scheduler!!.schedule(jobInfo!!)

                if (resultCode == JobScheduler.RESULT_SUCCESS) {
                    Toast.makeText(context, "Reminder set successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Reminder does not set,Please try again", Toast.LENGTH_SHORT).show()
                }
            }

    }
}