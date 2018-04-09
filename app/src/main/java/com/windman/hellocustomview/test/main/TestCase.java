package com.windman.hellocustomview.test.main;

import java.io.IOException;

/**
 * Created by zaratustra on 2017/10/24.
 */
public class TestCase {

    public static void main(String[] args){
        WXAppParser wxAppParser = new WXAppParser();

        try {
            wxAppParser.unzipWXAppPackage("/Users/Administrator/Desktop/f2/test.wxapkg", "/Users/Administrator/Desktop/f2/test/");
//            wxAppParser.unzipWXAppPackage("/sdcard/test.wxapkg", "/sdcard/test/");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidWXPackageException e) {
            e.printStackTrace();
        }
    }
}

