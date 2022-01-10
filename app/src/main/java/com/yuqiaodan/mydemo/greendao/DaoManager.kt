package com.yuqiaodan.mydemo.greendao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.util.Log
import com.yuqiaodan.mydemo.greendao.DaoMaster.OpenHelper
import org.greenrobot.greendao.database.Database

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
        // Log.d(TAG, "insertItem: db写入数据  $item")
        val helper = ReleaseOpenHelper(context, "idiom-db.db")
        db = helper.writableDatabase
        val session = DaoMaster(db).newSession()
        idiomDao = session.idiomDao
    }


    class ReleaseOpenHelper : OpenHelper {
        constructor(context: Context?, name: String?) : super(context, name)
        constructor(context: Context?, name: String?, factory: CursorFactory?) : super(context, name, factory)
        override fun onUpgrade(db: Database, oldVersion: Int, newVersion: Int) {
            Log.i("greenDAO", "Upgrading schema from version $oldVersion to $newVersion by dropping all tables")
            onCreate(db)
        }
    }


}