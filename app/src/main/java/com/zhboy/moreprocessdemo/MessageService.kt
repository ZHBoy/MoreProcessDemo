package com.zhboy.moreprocessdemo

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.zhboy.moreprocessdemo.data.Message

/**
 * @author zhou_hao
 * @date 2021/3/6
 * @description: 子进程的service
 */
class MessageService : Service() {

    private var mILoadDataListener: ILoadDataListener? = null

    private var binder: Binder = object : IMessageInterface.Stub() {

        override fun registerListener(listener: ILoadDataListener?) {
            if (listener != null) {
                mILoadDataListener = listener
            }
        }

        override fun unregisterListener(listener: ILoadDataListener?) {
            if (listener != null) {
                mILoadDataListener = null
            }
        }

        /**
         * 获取主进程发送过来的值（实际业务场景，根据发过来的信息做相应操作，比如缓存数据、静默下载apk、加载图片等等）
         */
        override fun loadData(message: Message?) {
            println("----子进程接收到主进程发送的 ${message?.title}的任务 - 开始加载数据...")

            MMKVUtil.mmkv?.encode("task", "子进程的task完成，这是返回的结果")

            Thread.sleep(2000)
            mILoadDataListener?.onLoadDataSuccess("")
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
    }
}