package keeplive.zune.com.keeplivedemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by USER_CC on 2018/11/28.
 */

public class TxtLog {
    private static final String TAG = "CrashHandler";
    public static final boolean DEBUG = true;
    /**
     * 异常日志 存储位置为根目录下的 Crash文件夹
     */
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() +
            "/ugm/log/crash/";
    /**
     * 文件名后缀
     */
    private static final String FILE_NAME_SUFFIX = "xx.txt";

    private static TxtLog sInstance = new TxtLog();

    private TxtLog() {

    }

    public static TxtLog getInstance() {
        return sInstance;
    }


    private static void writeInFileByfb(String data) { //如果SD卡不存在或无法使用，则无法将异常信息写入SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.w(TAG, "sdcard unmounted,skip dump exception");
                return;
            }

        }
        File targetDir = new File(PATH);
        File f = new File(targetDir, FILE_NAME_SUFFIX);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f.getAbsoluteFile(), true);  //true表示可以追加新内容
            //fw=new FileWriter(f.getAbsoluteFile()); //表示不追加
            bw = new BufferedWriter(fw);
            //得到当前年月日时分秒
            long current = System.currentTimeMillis();
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
            bw.write(time + "\n");
            bw.write(data + "\n");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 将异常信息写入SD卡
     */
    public static void writeDataToSDCard(final String data) {
        new Thread() {
            @Override
            public void run() {
                writeInFileByfb(data);
            }
        }.start();
    }

    /**
     * 获取手机各项信息
     *
     * @param pw
     */
    private static void dumpPhoneInfo(Context context,PrintWriter pw) throws PackageManager.NameNotFoundException {
        //得到包管理器
        PackageManager pm = context.getPackageManager();
        //得到包对象
        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        //写入APP版本号
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print("_");
        pw.println(pi.versionCode);
        //写入 Android 版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        //手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        //手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
        //CPU架构
        pw.print("CPU ABI: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pw.println(Build.SUPPORTED_ABIS);
        } else {
            pw.println(Build.CPU_ABI);
        }
    }


}