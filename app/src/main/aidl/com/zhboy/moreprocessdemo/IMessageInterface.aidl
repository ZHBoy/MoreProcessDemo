// IMessageInterface.aidl
package com.zhboy.moreprocessdemo;
import com.zhboy.moreprocessdemo.data.Message;
import com.zhboy.moreprocessdemo.ILoadDataListener;
/**
 * @author zhou_hao
 * @date 2021/3/6
 * @description: 主进程给子进程发送的消息
 */
interface IMessageInterface {

    void loadData(in Message message);//发送消息，通知子进程加载数据

    void registerListener(in ILoadDataListener listener); //注册接口

    void unregisterListener(in ILoadDataListener listener); //解注册接口

}