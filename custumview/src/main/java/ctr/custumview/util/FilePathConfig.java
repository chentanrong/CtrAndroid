package ctr.custumview.util;

import android.os.Environment;

import java.io.File;

public class FilePathConfig {


    private static String com="yulintu";
    private static String DCIM="DCIM";
    private static String Camera="Camera";

    //获取SD卡根path
    public static String getRoot() {
        return Environment.getExternalStorageState();
    }

    //创建
    private static File mkdir(File file){
       if(!file.exists()){
           file.mkdir();
       }
        return file;
    }

    //获取/公司path
    public static File getCom() {
        File file = new File(getRoot() ,com);
        return mkdir(file);
    }
    //获取/相册
    public static File getDCIM() {
        File file = new File(getRoot() ,DCIM);
        return mkdir(file);
    }
    //获取/相册/Camera
    public static File getCamera() {
        File file = new File(getDCIM() ,Camera);
        return mkdir(file);
    }
}
