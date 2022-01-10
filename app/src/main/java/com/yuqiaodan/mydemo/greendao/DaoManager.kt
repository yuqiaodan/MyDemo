package com.yuqiaodan.mydemo.greendao

import android.content.Context
import android.database.sqlite.SQLiteDatabase

/**
 * Created by qiaodan on 2022/1/10
 * Description:
 */
object DaoManager {
    //整个数据库对象
    lateinit var db: SQLiteDatabase
    //成语表 管理
    lateinit var idiomDao: IdiomDao

    fun initGreenDao(context: Context) {
        val helper = DaoMaster.DevOpenHelper(context, "idiom-db")
        db = helper.writableDatabase
        val session = DaoMaster(db).newSession()
        idiomDao = session.idiomDao
    }



}