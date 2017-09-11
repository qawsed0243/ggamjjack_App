package com.ggam.jjac.voca.Exam_Stoarge;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by HANSUNG on 2017-06-21.
 */

public class StorageDBHelper extends SQLiteOpenHelper {
    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public StorageDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 EXAM이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE EXAM (_id INTEGER PRIMARY KEY AUTOINCREMENT, create_at INTEGER);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void insert(int create_at) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO EXAM VALUES(null, '" + create_at + "');");
        db.close();
    }

    public void delete(String id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM EXAM WHERE _id='" + id + "';");
        db.close();
    }

    public HashMap<String , String> getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        HashMap<String , String> result = new HashMap<String , String>();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM EXAM", null);
        while (cursor.moveToNext()) {
            result.put(cursor.getString(0), cursor.getString(1));
        }
        return result;
    }
}
