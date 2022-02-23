package com.yuqiaodan.mydemo.utils

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.yuqiaodan.mydemo.base.App
import com.yuqiaodan.mydemo.utils.time.CloudTimeUtils
import com.yuqiaodan.mydemo.utils.time.SNTPClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat

/**
 * Created by qiaodan on 2021/11/17
 * Description:公用工具方法 存储一下 作为一个记录
 */
class CommonUtils {

    val TAG = "CommonUtils"


    fun deleteApp(pkg: String, context: Context) {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:$pkg")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }


    private fun getFilePath() {
        val name = javaClass.name.split(".")


        val path = "${name[0]}.${name[1]}.${name[2]}"
        Log.d(TAG, "path  $path  ${name.size}")


        Log.d(TAG, "MainActivity 移除黏性事件")
    }


    private fun receiveScreen(){
        val pm = App.context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val keyguardManager = App.context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                delay(5000L)
                Log.d(TAG, "屏幕是否点亮 --> ${pm.isInteractive}")
                Log.d(TAG, "用户是否上锁 --> ${keyguardManager.isKeyguardLocked}")
            }

        }
    }


    /***远程时间获取***/
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
        //确认数据库db文件存放路径 不同版本获取的存储路径相等
        // 安卓N以以上可以直接获取到data路径
        val path = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            App.context.dataDir.toString() + "/" + "databases" + "/" + "idiom-db.db"
        } else {
            //android N一下 通过获取cache路径 进行替换获取到data路径
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


    fun jsonToMapTest() {
        val map = hashMapOf<String, String>(Pair("1", "1"), Pair("2", "2"), Pair("3", "4"))
        val json = Gson().toJson(map)
        Log.d(TAG, "test: map转json后的值-->$json")
        val type = object : TypeToken<Map<String, String>>() {}.type
        val newMap = Gson().fromJson<Map<String, String>>(json, type)
        Log.d(TAG, "test: json重新转map-->$newMap")
    }


    fun jsontest(){

        val jsonStr = "{\n" +
                "    \"cloud_config\":{\n" +
                "        \"splash\":{\n" +
                "            \"show\":1,\n" +
                "            \"time\":30,\n" +
                "            \"status\":1,\n" +
                "            \"total_timeout\":16,\n" +
                "            \"timeout\":8,\n" +
                "            \"config\":[\n" +
                "                {\n" +
                "                    \"source\":\"admob\",\n" +
                "                    \"type\":50002,\n" +
                "                    \"priority\":1,\n" +
                "                    \"id\":\"ca-app-pub-3940256099942544/3419835294\"\n" +
                "                },\n" +
                "                     {\n" +
                "                    \"source\":\"admob\",\n" +
                "                    \"type\":50002,\n" +
                "                    \"priority\":2,\n" +
                "                    \"id\":\"ca-app-pub-3940256099942544/3419835294\"\n" +
                "                }\n" +
                "                ,\n" +
                "\n" +
                "                     {\n" +
                "                    \"source\":\"admob\",\n" +
                "                    \"type\":50002,\n" +
                "                    \"priority\":0,\n" +
                "                    \"id\":\"ca-app-pub-3940256099942544/3419835294\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"home\":{\n" +
                "            \"show\":1,\n" +
                "            \"status\":1,\n" +
                "            \"total_timeout\":16,\n" +
                "            \"timeout\":8,\n" +
                "            \"config\":[\n" +
                "                {\n" +
                "                    \"source\":\"admob\",\n" +
                "                    \"type\":50001,\n" +
                "                    \"priority\":1,\n" +
                "                    \"id\":\"ca-app-pub-3940256099942544/2247696110\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"ani_end\":{\n" +
                "            \"show\":1,\n" +
                "            \"time\":10,\n" +
                "            \"status\":1,\n" +
                "            \"total_timeout\":16,\n" +
                "            \"timeout\":8,\n" +
                "            \"config\":[\n" +
                "                {\n" +
                "                    \"source\":\"admob\",\n" +
                "                    \"type\":50003,\n" +
                "                    \"priority\":1,\n" +
                "                    \"id\":\"ca-app-pub-3940256099942544/1033173712\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"res_card\":{\n" +
                "            \"show\":1,\n" +
                "            \"status\":1,\n" +
                "            \"total_timeout\":16,\n" +
                "            \"timeout\":8,\n" +
                "            \"config\":[\n" +
                "                {\n" +
                "                    \"source\":\"admob\",\n" +
                "                    \"type\":50001,\n" +
                "                    \"priority\":1,\n" +
                "                    \"id\":\"ca-app-pub-3940256099942544/2247696110\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"res_back\":{\n" +
                "            \"show\":1,\n" +
                "            \"time\":15,\n" +
                "            \"status\":1,\n" +
                "            \"total_timeout\":16,\n" +
                "            \"timeout\":8,\n" +
                "            \"config\":[\n" +
                "                {\n" +
                "                    \"source\":\"admob\",\n" +
                "                    \"type\":50001,\n" +
                "                    \"priority\":1,\n" +
                "                    \"id\":\"ca-app-pub-3940256099942544/2247696110\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"equip_card\":{\n" +
                "            \"show\":1,\n" +
                "            \"status\":1,\n" +
                "            \"total_timeout\":16,\n" +
                "            \"timeout\":8,\n" +
                "            \"config\":[\n" +
                "                {\n" +
                "                    \"source\":\"admob\",\n" +
                "                    \"type\":50001,\n" +
                "                    \"priority\":1,\n" +
                "                    \"id\":\"ca-app-pub-3940256099942544/2247696110\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"outapp_window\":{\n" +
                "            \"gabg_many\":{\n" +
                "                \"show\":1,\n" +
                "                \"time\":3600,\n" +
                "                \"nums\":10,\n" +
                "                \"first\":900,\n" +
                "                \"scan_time\":7200\n" +
                "            },\n" +
                "             \"ram_more\":{\n" +
                "                \"show\":1,\n" +
                "                \"time\":3600,\n" +
                "                \"nums\":10,\n" +
                "                \"first\":900,\n" +
                "                \"scan_time\":7200\n" +
                "            }, \n" +
                "            \"bat_low\":{\n" +
                "                \"show\":1,\n" +
                "                \"time\":3600,\n" +
                "                \"nums\":10,\n" +
                "                \"first\":900\n" +
                "            },\n" +
                "             \"bat_charg\":{\n" +
                "                \"show\":1,\n" +
                "                \"time\":3600,\n" +
                "                \"nums\":10,\n" +
                "                \"first\":900\n" +
                "            }, \n" +
                "            \"uninstall\":{\n" +
                "                \"show\":1,\n" +
                "                \"time\":3600,\n" +
                "                \"nums\":10,\n" +
                "                \"first\":900\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}"



        val json= JSONObject(jsonStr).getJSONObject("cloud_config")

        Log.d(TAG, "jsontest: ${json.getJSONObject("splash")}")

    }


}