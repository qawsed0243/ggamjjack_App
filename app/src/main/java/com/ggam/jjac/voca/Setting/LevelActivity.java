package com.ggam.jjac.voca.Setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ggam.jjac.voca.BaseActivity;
import com.ggam.jjac.voca.R;

/**
 * Created by HANSUNG on 2017-06-22.
 */

public class LevelActivity extends BaseActivity {
    public static int num=1;
    public static int lvnum=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        setTitle("푸시시험 난이도 선택");
        final RadioButton rd1=(RadioButton)findViewById(R.id.radioButton1);
        final RadioButton rd2=(RadioButton)findViewById(R.id.radioButton2);
        final RadioButton rd3=(RadioButton)findViewById(R.id.radioButton3);
        final RadioButton rd4=(RadioButton)findViewById(R.id.radioButton4);
        final RadioButton rd5=(RadioButton)findViewById(R.id.radioButton5);
        final RadioButton rd6=(RadioButton)findViewById(R.id.radioButton6);

        SharedPreferences level=getSharedPreferences("levelchoice", 0);
        lvnum=level.getInt("level",1);

        //라디오 버튼 중복선택제거

        if(lvnum==1)
            rd1.setChecked(true);
        else if(lvnum==2)
            rd2.setChecked(true);
        else if(lvnum==3)
            rd3.setChecked(true);
        else if(lvnum==4)
            rd4.setChecked(true);
        else if(lvnum==5)
            rd5.setChecked(true);
        else
            rd6.setChecked(true);

        rd1.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                num=1;
                rd1.setChecked(true);
                rd2.setChecked(false);
                rd3.setChecked(false);
                rd4.setChecked(false);
                rd5.setChecked(false);
                rd6.setChecked(false);
                savelevel();
            }
        });
        rd2.setOnClickListener(new RadioButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                num=2;
                rd2.setChecked(true);
                rd1.setChecked(false);
                rd3.setChecked(false);
                rd4.setChecked(false);
                rd5.setChecked(false);
                rd6.setChecked(false);
                savelevel();
            }
        });
        rd3.setOnClickListener(new RadioButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                num=3;
                rd3.setChecked(true);
                rd2.setChecked(false);
                rd1.setChecked(false);
                rd4.setChecked(false);
                rd5.setChecked(false);
                rd6.setChecked(false);
                savelevel();
            }
        });

        rd4.setOnClickListener(new RadioButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                num=4;
                rd4.setChecked(true);
                rd2.setChecked(false);
                rd3.setChecked(false);
                rd1.setChecked(false);
                rd5.setChecked(false);
                rd6.setChecked(false);
                savelevel();
            }
        });

        rd5.setOnClickListener(new RadioButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                num=5;
                rd5.setChecked(true);
                rd2.setChecked(false);
                rd3.setChecked(false);
                rd4.setChecked(false);
                rd1.setChecked(false);
                rd6.setChecked(false);
                savelevel();
            }
        });

        rd6.setOnClickListener(new RadioButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                num=6;
                rd6.setChecked(true);
                rd2.setChecked(false);
                rd3.setChecked(false);
                rd4.setChecked(false);
                rd5.setChecked(false);
                rd1.setChecked(false);
                savelevel();
            }
        });


        Button exam_btn = (Button)findViewById(R.id.Exam_btn);
        //버튼 이벤트
        exam_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(!rd1.isChecked()&&!rd2.isChecked()&&!rd3.isChecked()&&!rd4.isChecked()&&!rd5.isChecked()&&!rd6.isChecked())
                    Toast.makeText(getApplicationContext(), "난이도를 선택해주세요!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "푸시시험 난이도가 설정되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void savelevel(){
        SharedPreferences level=getSharedPreferences("levelchoice",0);
        SharedPreferences.Editor editor=level.edit();
        editor.putInt("level",num);
        editor.commit();
    }
}
