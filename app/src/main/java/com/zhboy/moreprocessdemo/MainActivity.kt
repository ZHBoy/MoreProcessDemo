package com.zhboy.moreprocessdemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import com.zhboy.moreprocessdemo.data.Message
import com.zhboy.moreprocessdemo.data.WebBean

class MainActivity : AppCompatActivity() {

    /**
     * 子进程给主进程回调任务完成状态
     */
    private val mILoadDataListener = object : ILoadDataListener.Stub() {
        override fun onLoadDataFail(msg: String?) {
            println("----子进程任务失败了")
        }

        override fun onLoadDataSuccess(msg: String?) {
            println("----子进程任务完成，这次可能需要从mmkv里取数据啦")
            // TODO: 2021/3/6 加入mmkv替换sp
        }
    }

    private var iBookAidlInterface: IMessageInterface? = null

    private var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            println("----onServiceDisconnected: AIDL断开链接了")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iBookAidlInterface = IMessageInterface.Stub.asInterface(service)
            iBookAidlInterface?.registerListener(mILoadDataListener)
            println("----onServiceConnected: 主进程和子进程通过AIDL链接了")

            //发送给子进程一个静默下载apk的任务
            val mes = Message()
            mes.title = "静默下载apk"
            iBookAidlInterface?.loadData(mes)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //通过Bundle传值（Bundle本身实现了Parcelable）
        findViewById<Button>(R.id.bt01).setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            val bundle = Bundle()
            val wb = WebBean()
            wb.title = "网页"
            wb.url = "https://www.jianshu.com/u/60a521101bac"
            bundle.putSerializable("INTENT_SIMPLE_WEB_URL", wb)
            intent.putExtra("bundleData", bundle)
            startActivity(intent)
        }

        //AIDL处理跨进程,可以实现并发的需求，（Messenger的底层也是基于AIDL，只能单独的在消息队列一个个处理）
        findViewById<Button>(R.id.bt02).setOnClickListener {
            //绑定服务
            val intent = Intent(this, MessageService::class.java)
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }
}