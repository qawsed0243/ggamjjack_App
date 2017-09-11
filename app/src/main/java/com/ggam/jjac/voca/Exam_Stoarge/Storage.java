package com.ggam.jjac.voca.Exam_Stoarge;

/**
 * Created by HANSUNG on 2017-06-21.
 */

public class Storage {
    // 저장되는 푸시 시험지
    // id = 시험지 번호
    // time = 푸시를 받은 시간(시작 시간)
    private String id ;
    private int time ;

    public void setId(String id) { this.id = id ; }
    public void setTime(int time) { this.time = time ; }

    public String getId() { return this.id; }
    public int getTime() { return this.time; }
}
