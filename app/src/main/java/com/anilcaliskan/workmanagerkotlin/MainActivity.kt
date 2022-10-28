package com.anilcaliskan.workmanagerkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data=Data.Builder().putInt("intKey",1).build()

        val constraints=Constraints.Builder()
            //.setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()

        /*
        val myWorkRequest : WorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setConstraints(constraints)
            //send existing data along with it.
            .setInputData(data)
            //.setInitialDelay(2000, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)

         */
        val myWorkRequest : PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDatabase>(15, TimeUnit.SECONDS)
            .setInputData(data)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueue(myWorkRequest)
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(myWorkRequest.id).observe(this, Observer {
            if(it.state == WorkInfo.State.RUNNING){
                println("running")
            }else if(it.state == WorkInfo.State.FAILED){
                println("failed")
            }else if (it.state == WorkInfo.State.BLOCKED){
                println("bloked")
            }else if (it.state == WorkInfo.State.CANCELLED){
                println("cancelled")
            }else if(it.state == WorkInfo.State.ENQUEUED){
                println("enqueued")
            }
        })
        /*
        //Chaining

        val oneTimeRequest : OneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).beginWith(oneTimeRequest)
            .then(oneTimeRequest)
            .then(oneTimeRequest)
            .enqueue()
         */

        //WorkManager.getInstance(this).cancelAllWork()
    }
}