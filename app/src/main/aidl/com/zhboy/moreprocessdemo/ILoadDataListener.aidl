// ILoadDataListener.aidl
package com.zhboy.moreprocessdemo;

/**
 * @author zhou_hao
 * @date 2021/3/6
 * @description: 子进程做完任务，回调给主进程
 */
interface ILoadDataListener {

     void onLoadDataSuccess(String msg);

     void onLoadDataFail(String msg);
}