package com.ggam.jjac.voca.Exam_Stoarge;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ggam.jjac.voca.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by HANSUNG on 2017-06-21.
 */

public class StorageAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<Storage> storage_item = new ArrayList<>() ;
    // ListViewAdapter의 생성자
    public StorageAdapter() { }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return storage_item.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.stoarge_card, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        final TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        final TextView tv_sentence = (TextView) convertView.findViewById(R.id.tv_sentence);
        final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
        // Data Set(storage_item)에서 position에 위치한 데이터 참조 획득
        final Storage listViewItem = storage_item.get(position);

        // 푸시 시험지 받은 시간을 받아옴(시작 시간)
        int start = listViewItem.getTime();
        // 현재 시간을 받아옴
        long now = System.currentTimeMillis();

        // 날짜 형태로 바꿈
        Date datenow = new Date(now);
        Date datestart = new Date(start);
        SimpleDateFormat sdfNow = new SimpleDateFormat("MM/dd HH:mm");
        // nowDate 변수에 값을 저장한다.
        String formatDate = sdfNow.format(datenow);

        tv_time.setText(formatDate);
        // 남은 시간 = (12시간) - 현재 시각 - 시작 시간
        //int diff =  (12*60*60*1000) - (int)(datenow.getTime() - datestart.getTime());
        int diff =  (1*60*1000) - (int)(datenow.getTime() - datestart.getTime());
        StorageDBHelper ExdbManager = new StorageDBHelper(context.getApplicationContext(), "EXAM.db", null, 1);
        // 시간이 만료되면(남은 시간이 0이하이면)
        if (diff <= 0){
            // 데이터 베이스에서 지운다.
            ExdbManager.delete(listViewItem.getId());
            tv_sentence.setText("기한이 만료되었습니다.");
            progressBar.setProgress(0);
            return convertView;
        }
        // 시간 출력 형태
        final String FORMAT = "%02d:%02d:%02d";


        // 카운터 타이머를 이용하여 list item의 시간을 출력
        new CountDownTimer(diff, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
                String time_pic = String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                tv_sentence.setText(""+time_pic);
                int time2 = (int)(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                Log.d("pic_time", " " + Integer.toString(time2));
//                int CalPer = (int)TimeUnit.MICROSECONDS.toMinutes(millisUntilFinished);
//                int CalPer = time2;
                progressBar.setMax(60);
                progressBar.setProgress(time2);
            }
            public void onFinish() {
                // 데이터 베이스에서 지운다.
                StorageDBHelper ExdbManager = new StorageDBHelper(context.getApplicationContext(), "EXAM.db", null, 1);
                ExdbManager.delete(listViewItem.getId());
                // 클릭 불가능
                tv_sentence.setText("기한이 만료되었습니다.");
            }
        }.start();
        return convertView;
    }
    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return storage_item.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String id, int time) {
        Storage item = new Storage();

        item.setId(id);
        item.setTime(time);

        storage_item.add(item);
    }

    public String getDeleteItem(int position){
        return storage_item.get(position).getId();
    }

}
