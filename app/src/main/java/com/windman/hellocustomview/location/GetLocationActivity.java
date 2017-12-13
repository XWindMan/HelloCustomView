package com.windman.hellocustomview.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.LocationRequest;
import com.orhanobut.logger.Logger;
import com.patloew.rxlocation.RxLocation;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.windman.hellocustomview.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GetLocationActivity extends AppCompatActivity {
    private LocationManager myLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        String serviceName = Context.LOCATION_SERVICE;
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
    }

    private Location getLocation() {
        //获取位置管理服务

        //查找服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //定位精度: 最高
        criteria.setAltitudeRequired(false); //海拔信息：不需要
        criteria.setBearingRequired(false); //方位信息: 不需要
        criteria.setCostAllowed(true);  //是否允许付费
        criteria.setPowerRequirement(Criteria.POWER_LOW); //耗电量: 低功耗
//        String provider = myLocationManager.getBestProvider(criteria, true); //获取GPS信息
//        myLocationManager.requestLocationUpdates(provider,2000,5,locationListener);
//        Log.e("provider", provider);
//        List<String> list = myLocationManager.getAllProviders();
//        Log.e("provider", list.toString());
//
        Location gpsLocation = null;
        Location netLocation = null;
//        myLocationManager.addGpsStatusListener(myListener);
        if (netWorkIsOpen()) {
            //2000代表每2000毫秒更新一次，5代表每5秒更新一次
//            myLocationManager.requestLocationUpdates("network", 2000, 5, locationListener);
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            netLocation = myLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if (gpsIsOpen()) {
//            myLocationManager.requestLocationUpdates("gps", 2000, 5, locationListener);
            gpsLocation = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if (gpsLocation == null && netLocation == null) {
            return null;
        }
        if (gpsLocation != null && netLocation != null) {
            if (gpsLocation.getTime() < netLocation.getTime()) {
                gpsLocation = null;
                return netLocation;
            } else {
                netLocation = null;
                return gpsLocation;
            }
        }
        if (gpsLocation == null) {
            return netLocation;
        } else {
            return gpsLocation;
        }
    }

    private boolean gpsIsOpen() {
        boolean isOpen = true;
        if (!myLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {//没有开启GPS
            isOpen = false;
        }
        return isOpen;
    }

    private boolean netWorkIsOpen() {
        boolean netIsOpen = true;
        if (!myLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //没有开启网络定位
            netIsOpen = false;
        }
        return netIsOpen;
    }

    //监听GPS位置改变后得到新的经纬度
    private LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.e("location", location.toString() + "....");
            // TODO Auto-generated method stub
            if (location != null) {
                //获取国家，省份，城市的名称
                Log.e("location", location.toString());
                List<Address> m_list = getAddress(location);
//                new MyAsyncExtue().execute(location);

//                Log.e("str", m_list.toString());
                String city = "";
                if (m_list != null && m_list.size() > 0) {
                    city = m_list.get(0).getLocality();//获取城市
                }
                Logger.d("location:" + m_list.toString() + "\n"
                        + "城市:" + city + "\n精度:"
                        + location.getLongitude() + "\n纬度:"
                        + location.getLatitude() + "\n定位方式:"
                        + location.getProvider());
//                show_GPS.setText("location:" + m_list.toString() + "\n" + "城市:" + city + "\n精度:" + location.getLongitude() + "\n纬度:" + location.getLatitude() + "\n定位方式:" + location.getProvider());
            } else {
//                show_GPS.setText("获取不到数据");
                Logger.d("no data");
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };


    // 获取地址信息
    private List<Address> getAddress(Location location) {
        List<Address> result = null;
        try {
            if (location != null) {
                Geocoder gc = new Geocoder(this, Locale.getDefault());
                result = gc.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
