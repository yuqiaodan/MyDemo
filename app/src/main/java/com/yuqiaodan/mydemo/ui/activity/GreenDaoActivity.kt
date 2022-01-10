package com.yuqiaodan.mydemo.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.base.App
import com.yuqiaodan.mydemo.greendao.DaoManager
import com.yuqiaodan.mydemo.greendao.bean.Idiom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder

/***
 * GreenDao学习Activity
 * */
class GreenDaoActivity : AppCompatActivity(), View.OnClickListener {

    val TAG="greenDAO"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_green_dao)
        findViewById<View>(R.id.btn_insert).setOnClickListener(this)
        findViewById<View>(R.id.btn_get).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_insert -> {
                insertItem()

            }

            R.id.btn_get->{
                initJson()
                //getAllItems()
            }

        }
    }


    fun insertItem() {
        val json = "[\n" +
                "    {\n" +
                "        \"derivation\": \"语出《法华经·法师功德品》下至阿鼻地狱。”\",\n" +
                "        \"example\": \"但也有少数意志薄弱的……逐步上当，终至堕入～。★《上饶集中营·炼狱杂记》\",\n" +
                "        \"explanation\": \"阿鼻梵语的译音，意译为无间”，即痛苦无有间断之意。常用来比喻黑暗的社会和严酷的牢狱。又比喻无法摆脱的极其痛苦的境地。\",\n" +
                "        \"pinyin\": \"ā bí dì yù\",\n" +
                "        \"word\": \"阿鼻地狱\",\n" +
                "        \"abbreviation\": \"abdy\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"derivation\": \"三国·魏·曹操《整齐风俗令》阿党比周，先圣所疾也。”\",\n" +
                "        \"example\": \"《论语·卫灵公》众恶之，必察焉；众好之，必察焉”何晏集解引三国魏王肃曰或众～，或其人特立不群，故好恶不可不察也。”\",\n" +
                "        \"explanation\": \"指相互勾结，相互偏袒，结党营私。\",\n" +
                "        \"pinyin\": \"ē dǎng bǐ zhōu\",\n" +
                "        \"word\": \"阿党比周\",\n" +
                "        \"abbreviation\": \"edbz\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"derivation\": \"《汉书·诸葛丰传》今以四海之大，曾无伏节死谊之臣，率尽苟合取容，阿党相为，念私门之利，忘国家之政。”\",\n" +
                "        \"example\": \"无\",\n" +
                "        \"explanation\": \"阿党偏袒、偏私一方。为了谋求私利相互偏袒、包庇。\",\n" +
                "        \"pinyin\": \"ē dǎng xiāng wéi\",\n" +
                "        \"word\": \"阿党相为\",\n" +
                "        \"abbreviation\": \"edxw\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"derivation\": \"鲁迅《我们要批评家》然而新的批评家不开口，类似批评家之流便趁势一笔抹杀‘阿狗阿猫’。”\",\n" +
                "        \"example\": \"无\",\n" +
                "        \"explanation\": \"旧时人们常用的小名。引申为任何轻贱的，不值得重视的人或著作。\",\n" +
                "        \"pinyin\": \"ā gǒu ā māo\",\n" +
                "        \"word\": \"阿狗阿猫\",\n" +
                "        \"abbreviation\": \"agam\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"derivation\": \"见阿家阿翁”。\",\n" +
                "        \"example\": \"既然如此，你我两个，便学个不痴不聋的～。★《儿女英雄传》二三回\",\n" +
                "        \"explanation\": \"阿名词的前缀。姑丈夫的母亲。翁丈夫的父亲。指公公婆婆。\",\n" +
                "        \"pinyin\": \"ā gū ā wēng\",\n" +
                "        \"word\": \"阿姑阿翁\",\n" +
                "        \"abbreviation\": \"agaw\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"derivation\": \"唐·赵璾《因话录》卷一郭暖尝与升平公主琴瑟不调。尚父拘暖，自诣朝童结罪。上召而慰之曰‘谚云不痴不聋，不作阿家阿翁。’”\",\n" +
                "        \"example\": \"无\",\n" +
                "        \"explanation\": \"阿名词的前缀。家通姑”，丈夫的母亲。翁丈夫的父亲。指公公婆婆。\",\n" +
                "        \"pinyin\": \"ā gū ā wēng\",\n" +
                "        \"word\": \"阿家阿翁\",\n" +
                "        \"abbreviation\": \"agaw\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"derivation\": \"语出旧题汉·班固《汉武故事》汉武帝幼时曾对姑母长公主说若得阿娇(姑母之女)作妇，当以金屋贮之。”\",\n" +
                "        \"example\": \"～原相配，太其玉镜宁无时？★明·周履靖《锦笺记·诒婚》\",\n" +
                "        \"explanation\": \"阿娇指汉武帝刘彻的姑母（长公主刘娇）的女儿。原指汉武帝刘彻要用金屋接纳阿娇为妇。这里泛指美丽高贵的女子。\",\n" +
                "        \"pinyin\": \"ā jiāo jīn wū\",\n" +
                "        \"word\": \"阿娇金屋\",\n" +
                "        \"abbreviation\": \"ajjw\"\n" +
                "    }]"




        val type = object : TypeToken<List<Idiom>>() {}.type
        val items = Gson().fromJson<List<Idiom>>(json, type)


        Log.d(TAG, "insertItem: json解析成功 ")

        for (item in items) {
            Log.d(TAG, "insertItem: db写入数据  $item")
            DaoManager.idiomDao.insert(item)
        }


    }


    fun getAllItems(){
       val list= DaoManager.idiomDao.loadAll()

        val gson=Gson()
        var text=""
        for(i in list){
            text+="${gson.toJson(i)}"+"\n"
        }

        findViewById<TextView>(R.id.tv_result).text=text

    }


    fun initJson(){


        GlobalScope.launch(Dispatchers.IO) {


            val strBuilder=StringBuilder()
            try {
                val assetManager=App.context.assets
                val bf=BufferedReader(InputStreamReader(assetManager.open("idiom.json")))
                strBuilder.append(bf.readText())
                Log.d(TAG,"读取json文件完毕 字符串总长度 ${strBuilder.length}  ")

                val type = object : TypeToken<List<Idiom>>() {}.type
                val items = Gson().fromJson<List<Idiom>>(strBuilder.toString(), type)

                Log.d(TAG,"json转换完毕  lis总长度 ${items.size}   \n 数据样例：${items[0]}")

                for (item in items) {
                    Log.d(TAG, "insertItem: db写入数据 ${item.word}")
                    DaoManager.idiomDao.insert(item)
                }



            }catch (e:Exception){

            }
        }




    }


}







