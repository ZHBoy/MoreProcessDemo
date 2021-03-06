package com.zhboy.moreprocessdemo.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: zhou_hao
 * @date: 2021/3/6
 * @description:
 **/
public class Message implements Parcelable {
    String title = null;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Message() {
    }

    protected Message(Parcel in) {
        title = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }
}
