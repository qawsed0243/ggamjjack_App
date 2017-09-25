package com.ggam.jjac.voca.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ggam.jjac.voca.Vocacard.Voca;

import java.util.ArrayList;

/**
 * Created by YoungJung on 2017-06-21.
 * 참고 http://blog.naver.com/PostView.nhn?blogId=hee072794&logNo=220619425456
 */

public class DBHelper extends SQLiteOpenHelper {
    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE MYWORD (_id INTEGER PRIMARY KEY, english TEXT, hanguel TEXT, hanguel2 TEXT, hanguel3 TEXT, grammar TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(int _id, String english, String hanguel, String hanguel2, String hanguel3, String grammar) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO MYWORD VALUES("+_id+",'" + english + "', '" + hanguel+ "','"+hanguel2 +"','"+hanguel3+"','" + grammar+"');");
        db.close();
    }

    public void update(String english, String hanguel) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE MYWORD SET english=" + english + " WHERE hanguel='" + hanguel + "';");
        db.close();
    }

    public void delete(String english) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM MYWORD WHERE english='" + english + "';");
        db.close();
    }

    public int select(String english) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MYWORD WHERE english='" + english + "';", null);
        // 입력한 항목과 일치하는 검색
        if(cursor.moveToFirst()){
            cursor.close();
            return 100;
        }
        cursor.close();
        return 0;
    }

    public void drop(String tablename){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE MYWORD");
        db.close();
    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM MYWORD", null);
        while (cursor.moveToNext()) {
            result += cursor.getInt(0)
                    + " : "
                    + cursor.getString(1)
                    +" /"
                    + cursor.getString(2)
                    +" /"
                    + cursor.getString(3)
                    +" /"
                    + cursor.getString(4)
                    +" /"
                    + cursor.getString(5)
                    + "\n";
        }
        return result;
    }

    public ArrayList<Voca> getListWORD(){
        Voca word = null;
        ArrayList<Voca> WORDList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MYWORD",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            // _id INTEGER PRIMARY KEY, english TEXT, hanguel TEXT, hanguel2 TEXT, hanguel3 TEXT
            word = new Voca(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
            WORDList.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        return WORDList;
    }
}
