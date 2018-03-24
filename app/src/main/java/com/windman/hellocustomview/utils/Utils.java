package com.windman.hellocustomview.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.widget.ImageView;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static Bitmap getLoacalBitmap(String url) {
        if (url == null) {
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String compressAndSaveBitmapToSDCard(Context context,
                                                       Bitmap rawBitmap, String fileName, int quality) {
        String saveFilePath = "";

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            String sdpath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + context.getPackageName();
            saveFilePath = sdpath + File.separator + fileName;

            File file = new File(sdpath);
            if (!file.exists()) {
                file.mkdir();
            }

            File saveFile = new File(saveFilePath);
            if (!saveFile.exists()) {
                try {
                    saveFile.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            saveFile);
                    if (fileOutputStream != null) {
                        rawBitmap.compress(Bitmap.CompressFormat.JPEG, quality,
                                fileOutputStream);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
        return saveFilePath;
    }

    public static int getAcceleratorParamByModel() {
        int value = 0;
        if (Build.MODEL != null
                && ((Build.MODEL).contains("I9308") || (Build.MODEL).contains("I939") || (Build.MODEL)
                .contains("I9300"))) {
            // 注意：三星 i9250怎么晃都不会超过20
            value = 14;
        } else {
            value = 19;
        }

        return value;
    }

    public static boolean isChinese(Context context) {
        return context.getResources().getConfiguration().locale.getCountry().equals("CN");
    }

    /**
     * 手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 手机版本
     *
     * @return
     */
    public static String getMobileVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * app版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * 固件版本号
     *
     * @return
     */
    public static String getHardwareVersion() {
        String hw = "";
        return hw;
    }

    public boolean isAppOnForeground(Activity a, String packageName) {
        ActivityManager activityManager = (ActivityManager) a
                .getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return false;
        }
        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * ��������Ƿ����
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    /**
     * convert string to MD5 value.
     *
     * @param string
     * @return
     */
    public static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float sp2px(Context context, float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, spValue,
                context.getResources().getDisplayMetrics());
    }

    public static float px2sp(Context context, float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
                context.getResources().getDisplayMetrics());
    }

    public static int dip2px(Context context,float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 方法名称:transMapToString
     * 传入参数:map
     * 返回值:String 形如 username'xmc^passwmcord'1234
     */
    public static String transMapToString(Map map) {
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append("'").append(null == entry.getValue() ? "" :
                    entry.getValue().toString()).append(iterator.hasNext() ? "^" : "");
        }
        return sb.toString();
    }

    /**
     * @param mapString <username,xmc><password,1234>
     * @return map
     */
    public static Map transStringToMap(String mapString) {
        Map map = new HashMap();
        StringTokenizer items;
        for (StringTokenizer entrys = new StringTokenizer(mapString, "^"); entrys.hasMoreTokens();
             map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
            items = new StringTokenizer(entrys.nextToken(), "'");
        return map;
    }

    /**
     * 获取SD卡根目录
     *
     * @return
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        return sdDir.toString();
    }

    /**
     * 判断Email格式是否正确
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailAddressValid(String email) {
        boolean isEmailValid = false;
        String strExpression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern objPattern = Pattern.compile(strExpression, Pattern.CASE_INSENSITIVE);
        Matcher objMatcher = objPattern.matcher(inputStr);
        if (objMatcher.matches()) {
            isEmailValid = true;
        }
        return isEmailValid;
    }

    /**
     * DeviceID + 时间戳
     *
     * @return
     */
    /*public static String generateUuuId() {
        TelephonyManager tm = (TelephonyManager) AppContextUtil.getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        //非手机设备用Serial Number替代
        if (imei == null) {
            imei = Build.SERIAL;
        }
        String long_Id = imei + String.valueOf(Calendar.getInstance().getTimeInMillis())
                + "abcdefg";
        return Utils.stringToMD5(long_Id);
    }*/


    public static String savePhotoToSDCard(Bitmap photoBitmap, String path,
                                           String photoName) {
        String p = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File photoFile = new File(path, photoName + ".png");
            p = photoFile.getAbsolutePath();
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
                            fileOutputStream)) {
                        fileOutputStream.flush();
                        // fileOutputStream.close();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return p;
    }

    public static void loadPicFromSDcard(String filepath, ImageView imageView) {
        File file = new File(filepath);
        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(filepath);
            // 将图片显示到ImageView中
            imageView.setImageBitmap(bm);
        }
    }

    public static boolean isInInerval(float cur, float min, float max) {
        return Math.max(min, cur) == Math.min(cur, max);
    }

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
}
