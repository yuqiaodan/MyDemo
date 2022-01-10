package com.yuqiaodan.mydemo.utils;

/**
 * Created by qiaodan on 2022/1/10
 * Description:
 */


import android.os.Build;
import android.os.Environment;


import com.yuqiaodan.mydemo.base.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Spring on 2015/11/7.
 */
public class FileUtil {
    private String APP_DATA_PATH;

    public FileUtil() {
        //内部存储
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            APP_DATA_PATH = App.context.getDataDir() + "/" ;
        }else{
            APP_DATA_PATH= Environment.getDataDirectory() + "/" ;
        }
    }

    public String getAppDataPath() {
        return APP_DATA_PATH;
    }



    /**
     * 在SD卡上创建文件
     * @param fileName
     * @return
     * @throws java.io.IOException
     */
    public File createSDFile(String fileName) throws IOException {
        File file = new File(APP_DATA_PATH + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     * @param dirName 目录名字
     * @return 文件目录
     */
    public File createDir(String dirName){
        File dir = new File(APP_DATA_PATH + dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 判断文件是否存在
     * @param fileName
     * @return
     */
    public boolean isFileExist(String fileName){
        File file = new File(APP_DATA_PATH + fileName);
        return file.exists();
    }

    public File write2SDFromInput(String path,String fileName,InputStream input){
        File file = null;
        OutputStream output = null;

        try {
            createDir(path);
            file =createSDFile(path + fileName);
            output = new FileOutputStream(file);
            byte [] buffer = new byte[4 * 1024];
            while(input.read(buffer) != -1){
                output.write(buffer);
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}