package com.anilcaliskan.workmanagerkotlin

import android.content.Context
import androidx.work.WorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters

class RefreshDatabase(val context: Context,workerParams: WorkerParameters): Worker(context,workerParams) {

    override fun doWork(): Result {
        val getData = inputData
        val myNumber = getData.getInt("intKey", 0)

        refreshDatabase(myNumber)

        return Result.success()
    }

    fun refreshDatabase(myNumber: Int) {
        val sharedPreferences = context.getSharedPreferences("com.anilcaliskan.workmanagerkotlin", Context.MODE_PRIVATE)
        var mySaveNumber = sharedPreferences.getInt("myNumber", 0)
        mySaveNumber = mySaveNumber + myNumber
        println(mySaveNumber)
        sharedPreferences.edit().putInt("myNumber", mySaveNumber).apply()
    }

}