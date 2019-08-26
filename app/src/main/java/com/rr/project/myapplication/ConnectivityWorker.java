package com.example.background.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.graphics.BitmapFactory
import com.example.background.R
import timber.log.Timber

class ConnectivityWorker(context:Context,params:WorkerParameters):Worker(context,param){

  override fun doWork():Result{
    val appContxt = applicationContext
    //makeStatusNotification("Blurring image", appContext)
    //Check network connectivity
    if(isConnectedToInternet())
      Result.success()
    else
      Result.failure()
  }
  
  fun isConnectedToInternet(): boolean{
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
    
    val isWiFi: Boolean = activeNetwork?.type == ConnectivityManager.TYPE_WIFI
    
    return isConnected
  }
  
  //this in view model
  fun runWorkManager(){
    private val workManager = WorkManager.getInstance(application)
    
    // Create a Constraints object that defines when the task should run
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        //.setRequiresDeviceIdle(true)
        //.setRequiresCharging(true)
        .build()
 
 
    
    workManager.enqueue(OneTimeWorkRequest.from(BlurWorker::class.java))
  }
  
}
