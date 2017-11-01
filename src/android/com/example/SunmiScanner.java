/**
 */
package com.example;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class SunmiScanner extends CordovaPlugin {
    private static final String TAG = "SunmiScanner";
    public static final int REQUEST_CODE = 1;
    CallbackContext callbackContext = null;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        Log.d(TAG, "Initializing SunmiScanner");
    }

    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;

        if (action.equals("echo")) {
            String phrase = args.getString(0);
            // Echo back the first argument
            Log.d(TAG, phrase);
        } else if (action.equals("getDate")) {
            // An example of returning data back to the web layer
            final PluginResult result = new PluginResult(PluginResult.Status.OK, (new Date()).toString());
            callbackContext.sendPluginResult(result);
        } else if (action.equals("scan")) {
            scan();
        }
        return true;
    }

    public void scan() {
        /**

         * 外部应用在自己的业务代码需要启动扫码的地方使用下面的方式创建Intent，

         * 然后使用startActivityForResult()调用起商米的扫码模块;

         */

        Intent intent = new Intent("com.summi.scan");

        intent.setPackage("com.sunmi.sunmiqrcodescanner");

        intent.putExtra("CURRENT_PPI", 0X0003);//当前分辨率
        intent.putExtra("IS_SHOW_ALBUM", false);// 是否显示从相册选择图片按钮，默认true

        /**

         * 使用该方式也可以调用扫码模块

         *Intent intent = new Intent("com.summi.scan");

         *intent.setClassName("com.sunmi.sunmiqrcodescanner",

         "com.sunmi.sunmiqrcodescanner.activity.ScanActivity");

         */

        /**

         //扫码模块有一些功能选项，开发者可以通过传递参数控制这些参数，

         //所有参数都有一个默认值，开发者只要在需要的时候添加这些配置就可以。

         intent.putExtra("CURRENT_PPI", 0X0003);//当前分辨率

         //M1和V1的最佳是800*480,PPI_1920_1080 = 0X0001;PPI_1280_720 =

         //0X0002;PPI_BEST = 0X0003;

         intent.putExtra("PLAY_SOUND", true);// 扫描完成声音提示  默认true

         intent.putExtra("PLAY_VIBRATE", false);

         //扫描完成震动,默认false，目前M1硬件支持震动可用该配置，V1不支持

         intent.putExtra("IDENTIFY_INVERSE_QR_CODE", true);// 识别反色二维码，默认true

         intent.putExtra("IDENTIFY_MORE_CODE", false);// 识别画面中多个二维码，默认false

         intent.putExtra("IS_SHOW_SETTING", true);// 是否显示右上角设置按钮，默认true

         intent.putExtra("IS_SHOW_ALBUM", true);// 是否显示从相册选择图片按钮，默认true

         */
        final CordovaPlugin that = this;
        that.cordova.startActivityForResult(that, intent, REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String scanResult = null;
        if (requestCode == 1 && data != null) {
            Bundle bundle = data.getExtras();
            ArrayList<HashMap<String, String>> result = (ArrayList<HashMap<String, String>>) bundle.getSerializable("data");
            Iterator<HashMap<String, String>> it = result.iterator();

            while (it.hasNext()) {
                HashMap<String, String> hashMap = it.next();
                Log.i("sunmi", hashMap.get("TYPE"));//这个是扫码的类型
                Log.i("sunmi", hashMap.get("VALUE"));//这个是扫码的结果
                scanResult = hashMap.get("VALUE");
            }
        }

        if(scanResult!=null)
            callbackContext.success(scanResult);
        else
            callbackContext.error("Fail");

        super.onActivityResult(requestCode, resultCode, data);

    } 

}
