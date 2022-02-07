package com.yuqiaodan.mydemo.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.base.App
import com.yuqiaodan.mydemo.eventbus.BusEventId
import com.yuqiaodan.mydemo.eventbus.BusWrapper
import com.yuqiaodan.mydemo.study.ThreadStudy
import com.yuqiaodan.mydemo.ui.activity.GreenDaoActivity
import com.yuqiaodan.mydemo.utils.EncryptUtils
import com.yuqiaodan.mydemo.utils.time.CloudTimeUtils
import com.yuqiaodan.mydemo.utils.time.SNTPClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity(), View.OnClickListener {
    val TAG = "MainLog"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()


        EventBus.getDefault().register(this)


    }


    private fun initView() {

        findViewById<View>(R.id.btn_test).setOnClickListener(this)
        findViewById<View>(R.id.btn_event_bus_test).setOnClickListener(this)


    }


    @SuppressLint("InvalidWakeLockTag")

    override fun onClick(v: View?) {
        when (v?.id) {


            R.id.btn_event_bus_test -> {
                //getFilePath()
                startActivity(Intent(this, FuncModeActivity::class.java))
                val str =
                    "C0cTCAERGxkBLQ8BQU5SAUtUXAVNSQATUVFVFUkDQURJQRUAFS0KDBAAUl8pO0lBFgIEHAJHWVYfFQIJR09WEw0TCAsGGFJfUAkVExtSSVAFCg4EFREbEgwMGlJfUFZHT1YUAAQDCQwEFRdQXEcFFRwWF0RJQRAVExsCR1lWRgYQBVZQFUUGQ14BVERIARZXAFUSSV1DAARWQ0BQRV5HT1YZCBcPR1lWUklQCgoAFQQMHQhHWQ9SBBYCFwYHA0dIROmYsOiknOedteikj+WvrOW5sOmap+WgseWNmee7lumVlui2iumckui+t+S8vOePqPCcj6rlsarljYhESUEVHwwcBwgGVkpH5Lyr54+t8JifueWxu+WNikdeRAYKAAlHSETopJrlr6rlubZSSVACDBAAAgwREkdZVumaseWgseWNiERJQRgRERtEX0FHREtAVlZUQkFHXkQJDBpSX1BXVVpaQFdGXldRVlxHHBMIARECR0hEU+WOlFZcRwIJDA0VHQBQXEfkvLrnj7/wnY+/5bG95Y2cR09WABcdEAwNFxVHSETpmLDopJznnbVSSVAUCgIQUl9Q57uE6ZWD6LaMVlxHARIXBhEER0hE57uH6ZWF6LabUhheRAgCF1JfUCVRWUBAXzRQXyFHSlZDXCNQVlxHHwkBBhhSX1AWAQEZQFVQSkcMFRkBUFxHUUM2VEVeI1A1RiNFUlVWRjFTQ18nVkZHVUQgI1dCSCBHUldaFhFTRFMAB0dEAxBXUFESFlYTVgECEEJSFwRSVlZcRx0VOhURAkdIRFxBWFIVGQFHWVYTCh9IBA0QAgobAksRERRLHwcLBBtSSVAWFwwMCUdIRAMCGAMAUEpHERsdR0hEBgwYHxcdFUdPVgIKHzkTBgZSX1BQS1JaQkdeRBcMGwRHSEQDAhgDAFBKRxAXEQstBxUTKxwMARJHWVZAR15EFgoZUl9QEhcWEVJJUBUWChBSX1AuMCIjNSxfDhxBWFIRAAcGCCsTDRMICwYYUl9QEhFBWFITFxQ6ABsUAFBcR1JEQFZHRElBAhUXLQgEDhFSX1BXS1NaQ1BQSkcVBB5HSEQDAhgDAFAb"
                EncryptUtils.decodeString("perfect", str)

                test()
                ThreadStudy().test()
            }

            R.id.btn_test -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Log.d(TAG, "path1:--->  ${App.context.dataDir.toString() + "/"}")
                }
                Log.d(TAG, "path2:--->  ${Environment.getDataDirectory().toString() + "/"}")
                Log.d(TAG, "path3:--->  ${App.context.filesDir.toString() + "/"}")
                Log.d(TAG, "path4:--->  ${App.context.cacheDir.toString() + "/"}")
                Log.d(TAG, "path5:--->  ${App.context.obbDir.toString() + "/"}")
                //Log.d(TAG, "path6:--->  ${App.context.getDatabasePath().toString() + "/"}")


                downLoadDb()



                startActivity(Intent(this, GreenDaoActivity::class.java))
            }

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1, sticky = true)
    fun onReceiveMessage(msg: BusWrapper) {
        //Log.d("BusTest", "MainActivity onReceiveMessage:${msg.id} ")

        // EventBus.getDefault().removeAllStickyEvents()

        if (msg.verifyMessage(BusEventId.SHOW_MSG_FROM_NEXT_ACTIVITY)) {
            findViewById<TextView>(R.id.tv_event_bus_test).text = msg.getContent()?.toString()
        }
        if (msg.verifyMessage(BusEventId.SHOW_STICK_MSG_FROM_NEXT_ACTIVITY)) {
            //EventBus.getDefault().removeStickyEvent(msg)
            //Log.d("BusTest", "MainActivity 移除黏性事件")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)

    }


    fun remoteTime() {
        val time1 = CloudTimeUtils.getInstance().cloudTimeNoAccurate
        Log.d(TAG, "直接使用同步方法获取：${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time1)}")

        GlobalScope.launch(Dispatchers.IO) {
            delay(5000L)
            val time3 = CloudTimeUtils.getInstance().cloudTimeNoAccurate
            Log.d(TAG, "5秒后重试 直接使用同步方法获取：${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time3)}")

        }

        Log.d(TAG, "---start request time---")
        CloudTimeUtils.getInstance().requestAsyncCloudTime(object : SNTPClient.Listener {
            override fun onTimeReceived(rawDate: String?, timeStamp: Long) {
                Log.d(TAG, "onTimeReceived: rawDate: $rawDate  timeStamp: $timeStamp")
                Log.d(TAG, "onTimeReceived: 格式化: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeStamp)}")
                val time2 = CloudTimeUtils.getInstance().cloudTimeNoAccurate
                Log.d(TAG, "成功一次后再使用同步方法获取： ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time2)}")
            }

            override fun onError(ex: Exception?) {
                Log.d(TAG, "onError: ${ex?.message}")
            }
        })
    }


    /**
     *
     * 下载db并写入到database
     * greendao加载该数据库
     *
     * **/


    fun downLoadDb() {
        val urlStr = "https://download.jingdekeji.cn/idiom-db.db"
        //App.context.getDir("", Context.MODE_PRIVATE)
        //val path =   App.context.getDir("", Context.MODE_PRIVATE).toString() + "/" + "databases" + "/" + "idiom-db.db"
        val path = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            App.context.dataDir.toString() + "/" + "databases" + "/" + "idiom-db.db"
        } else {
            App.context.cacheDir.toString().replace("cache", "databases") + "/" + "idiom-db.db"
        }

        Log.d("greenDAO", "下载数据库存储地址:$path ")
        FileDownloader.setup(App.context)

        FileDownloader.getImpl().create(urlStr).setPath(path).setListener(

            object : FileDownloadListener() {
                override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                    Log.d("greenDAO", "pending: ")
                }

                override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                    //Log.d("greenDAO", "soFarBytes: $soFarBytes  totalBytes: $totalBytes")

                }

                override fun completed(task: BaseDownloadTask?) {
                    Log.d("greenDAO", "completed")

                }

                override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                    Log.d("greenDAO", "paused")

                }

                override fun error(task: BaseDownloadTask?, e: Throwable?) {
                    Log.d("greenDAO", "error :${e?.message}")

                }

                override fun warn(task: BaseDownloadTask?) {
                    Log.d("greenDAO", "warn")
                }
            }

        ).start()
    }


    fun test() {
        val map = hashMapOf<String, String>(Pair("1", "1"), Pair("2", "2"), Pair("3", "4"))
        val json = Gson().toJson(map)
        Log.d(TAG, "test: map转json后的值-->$json")
        val type = object : TypeToken<Map<String, String>>() {}.type
        val newMap = Gson().fromJson<Map<String, String>>(json, type)
        Log.d(TAG, "test: json重新转map-->$newMap")
    }


}