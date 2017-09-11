package com.ggam.jjac.voca.Setting.Notice;

import android.animation.TimeInterpolator;

/**
 * Created by julie on 2017-07-17.
 */

public class NoticeItem {
    public final String noticeNum;
    public final String title;
    public final String content;
    public final String timestamp;
    public final int colorId1;
    public final int colorId2;
    public final TimeInterpolator interpolator;


    public NoticeItem(String noticeNun, String title, String content, String timestamp, int colorId1, int colorId2, TimeInterpolator interpolator) {
        this.noticeNum = noticeNun;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
        this.colorId1 = colorId1;
        this.colorId2 = colorId2;
        this.interpolator = interpolator;
    }
}