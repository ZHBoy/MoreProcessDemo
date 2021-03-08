package com.zhboy.moreprocessdemo

import android.app.Application
import android.os.Build
import android.webkit.WebView
import com.tencent.mmkv.MMKV
import com.zhboy.moreprocessdemo.process.ProcessUtil


/**
 * @author: zhou_hao
 * @date: 2021/3/6
 * @description:
 **/
class ProcessApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val processName = ProcessUtil.getCurrentProcessName(this)

        if ("com.zhboy.moreprocessdemo:appMainProcess" == processName) {
            //表示是主进程，初始化一些东西
            println("----Application: $processName    是主进程，进行初始化")
        } else {
            println("----Application: $processName    不是主进程")
            //9.0要为工程进程配置webview的数据目录
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                if (processName != null) {
                    WebView.setDataDirectorySuffix(processName)
                }
            }
        }

        //初始化mmkv(当多进程的时候，不能只在主进程初始化)
        val rootDir = MMKV.initialize(this)
        println("mmkv root: $rootDir")
    }
}