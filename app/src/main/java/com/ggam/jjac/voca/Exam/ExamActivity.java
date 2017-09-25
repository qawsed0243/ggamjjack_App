package com.ggam.jjac.voca.Exam;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ggam.jjac.voca.BaseActivity;
import com.ggam.jjac.voca.ConfigAll;
import com.ggam.jjac.voca.DB.DBHelper;
import com.ggam.jjac.voca.Exam_Stoarge.Storage_Activity;
import com.ggam.jjac.voca.Frag.FragMain;
import com.ggam.jjac.voca.Frag.TabFragment3;
import com.ggam.jjac.voca.R;
import com.ggam.jjac.voca.Vocacard.Toe_low_Activity;
import com.ggam.jjac.voca.Vocacard.Voca;

import com.ggam.jjac.voca.Vocacard.Su_high_Activity;
import com.ggam.jjac.voca.Vocacard.Su_low_Activity;
import com.ggam.jjac.voca.Vocacard.Su_mid_Activity;
import com.ggam.jjac.voca.Vocacard.Toe_high_Activity;
import com.ggam.jjac.voca.Vocacard.Toe_mid_Activity;
import com.ggam.jjac.voca.Vocacard.Toe_low_Activity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by YoungJung on 2017-06-20.
 */

public class ExamActivity extends AppCompatActivity{
    private TextView txtview, 보기1, 보기2, 보기3, 보기4, now, pic_time;
    String[] random_cho = new String[5];
    String[] query = null; // 나머지 오답 보기
    private Handler mHandler;
    private Runnable mRunnable;
    ImageView correct_Img, incorrect_lmg;
    ImageView pic_timer;
    int Incorrect_Point =0;
    int Correct_Point =0;
    int now_count = 0;
    int amount =10;
    Button NEXT;
    TextView add_btn;
    ArrayList<Voca> listItem = new ArrayList<>();
    CountDownTimer countDownTimer;
    int random =0;
    static Voca item;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("시험보기");

        NEXT = (Button) findViewById(R.id.button);

        txtview = (TextView) findViewById(R.id.textView);
        보기1 = (TextView) findViewById(R.id.textView1);
        보기2 = (TextView) findViewById(R.id.textView2);
        보기3 = (TextView) findViewById(R.id.textView3);
        보기4 = (TextView) findViewById(R.id.textView4);
        now = (TextView) findViewById(R.id.now);
        pic_timer = (ImageView) findViewById(R.id.pic_timer);
        보기1.setTextColor(Color.BLACK);
        보기2.setTextColor(Color.BLACK);
        보기3.setTextColor(Color.BLACK);
        보기4.setTextColor(Color.BLACK);
        dbHelper = new DBHelper(getApplicationContext(), "MYWORD.db", null, 1);

        if(TabFragment3.num == 1){
            listItem = Su_low_Activity.Voca_card;
            setTitle("수능초급 시험보기");
        }else if(TabFragment3.num == 2){
            listItem = Su_mid_Activity.Voca_card2;
            setTitle("수능중급 시험보기");
        }else if(TabFragment3.num == 3){
            listItem = Su_high_Activity.Voca_card3;
            setTitle("수능고급 시험보기");
        }else if(TabFragment3.num == 4){
            listItem = Toe_low_Activity.Voca_card4;
            setTitle("토익초급 시험보기");
        }else if(TabFragment3.num == 5){
            listItem = Toe_mid_Activity.Voca_card5;
            setTitle("토익중급 시험보기");
        }else if(TabFragment3.num == 6) {
            listItem = Toe_high_Activity.Voca_card6;
            setTitle("토익고급 시험보기");
        }else if(Storage_Activity.num==1){
            listItem = Su_low_Activity.Voca_card;
            setTitle("수능초급 푸시 시험보기");
        }
        else if(Storage_Activity.num==2){
            listItem = Su_mid_Activity.Voca_card2;
            setTitle("수능중급 푸시 시험보기");
        }
        else if(Storage_Activity.num==3){
            listItem = Su_high_Activity.Voca_card3;
            setTitle("수능고급 푸시 시험보기");
        }
        else if(Storage_Activity.num==4){
            listItem = Toe_low_Activity.Voca_card4;
            setTitle("토익초급 푸시 시험보기");
        }
        else if(Storage_Activity.num==5){
            listItem = Toe_mid_Activity.Voca_card5;
            setTitle("토익중급 푸시 시험보기");
        }
        else if(Storage_Activity.num==6){
            listItem = Toe_high_Activity.Voca_card6;
            setTitle("토익고급 푸시 시험보기");
        }

        add_btn = (TextView) findViewById(R.id.add_button);
        pic_time = (TextView) findViewById(R.id.pic_time);
//        random_cho = new String[5];
        correct_Img = (ImageView) findViewById(R.id.imageView_O);
        incorrect_lmg = (ImageView) findViewById(R.id.imageView_X);

        random_answer(); //뜻 랜덤하게 나오게 만들기.
        txtview.setText(random_cho[4]); //단어 넣기
        TextView total = (TextView)findViewById(R.id.amount);
        int total_count = amount; //총 개수
        total.setText(Integer.toString(total_count)); //총 개수로 화면에 설정
        now.setText(Integer.toString(now_count+1));     //현재 개수로 화면에 설정

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbHelper.getListWORD().size() < item.getIndex()){
                    if(dbHelper.select(item.getWord())==0) {
                        dbHelper.insert(item.getIndex(), item.getWord(), item.getMean1(), item.getMean2(), item.getMean3(), item.getGrammar());
                        Toast.makeText(getApplicationContext(), "내 단어장에 추가되었습니다.", Toast.LENGTH_LONG).show();
                    }
                    else if(dbHelper.select(item.getWord())==100)
                        Toast.makeText(getApplicationContext(), "이미 내 단어장에 존재합니다.", Toast.LENGTH_LONG).show();
                }else if(dbHelper.getListWORD().size() ==item.getIndex()){
                }else{
                    if(dbHelper.getListWORD().get(item.getIndex()).getIndex() == item.getIndex() ){
                        dbHelper.delete(item.getWord());
                    }
                }
            }
        });
        //화면 설정
        try {
            //다음 버튼 누르면
            NEXT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //next 버튼을 누르면 맞고틀린표시 제거
                    correct_Img.setVisibility(View.INVISIBLE);
                    incorrect_lmg.setVisibility(View.INVISIBLE);
                    //함수로 빼줌.
                    ClickNext();
                    Log.d("now"," " + now.getText());
                }
            });
        }catch (Exception e) {
        }
    }

    //다음 버튼 눌렀을 때 나올 함수
    public void ClickNext () {
        countDownTimer.cancel();
        if (now.getText()==Integer.toString(10))
        {
            //지금이 마지막이라면
            TestResult Result = new TestResult(Correct_Point,amount);
            Intent intent = new Intent(this,ResultActivity.class);
            intent.putExtra("Result",Result); //맞은 갯수 보냄
            startActivity(intent);
            finish();
        }
        else
        {
            보기_setClickable_T();
            보기1.setBackgroundResource(R.drawable.word_button);
            보기2.setBackgroundResource(R.drawable.word_button);
            보기3.setBackgroundResource(R.drawable.word_button);
            보기4.setBackgroundResource(R.drawable.word_button);
            보기1.setTextColor(Color.BLACK);
            보기2.setTextColor(Color.BLACK);
            보기3.setTextColor(Color.BLACK);
            보기4.setTextColor(Color.BLACK);

            random_answer();
            txtview.setText(random_cho[4]);

            if(txtview.getText().length()>12)
                txtview.setTextSize(30);
            else
                txtview.setTextSize((float) 41.5);

            now_count++; //카운트 증가.
            now.setText(Integer.toString(now_count+1));
        }
    }

    // 1. 해당 영어와 맞는 한글을 여러 textView중 하나에 랜덤하게 뿌려줌
    // 2. 4개의 textView 중 하나를 click 시 그에 맞는 이벤트 발생
    public void random_answer() {

        random_cho = random_보기();

        for (int i = 0; i < 4; i++) {
            //1 ~ 4 까지 랜덤 숫자 구하기
            random = (int) (Math.random() * 4) + 1;
        }
        //시험 시간제
        countDownTimer();
        countDownTimer.start();

        if (random == 1) {
            보기1.setText(random_cho[0]); //답
            보기2.setText(random_cho[1]); //오답1
            보기3.setText(random_cho[2]); //오답2
            보기4.setText(random_cho[3]); //오답3

            //답을 클릭시
            보기1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    correct_event(); //이벤트 발생
                    보기1.setTextColor(Color.WHITE); //색 변경
                    보기1.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();
                }
            });

            보기2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    incorrect_event();
                    보기2.setTextColor(Color.WHITE);
                    보기2.setBackgroundResource(R.drawable.wrong_btn);
                    보기1.setTextColor(Color.WHITE);
                    보기1.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();
                }
            });

            보기3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    incorrect_event();
                    보기3.setTextColor(Color.WHITE);
                    보기3.setBackgroundResource(R.drawable.wrong_btn);
                    보기1.setTextColor(Color.WHITE);
                    보기1.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();
                }
            });

            보기4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    incorrect_event();
                    보기4.setTextColor(Color.WHITE);
                    보기4.setBackgroundResource(R.drawable.wrong_btn);
                    보기1.setTextColor(Color.WHITE);
                    보기1.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();
                }
            });
        } else if (random == 2) {
            보기2.setText(random_cho[0]);
            보기1.setText(random_cho[1]);
            보기3.setText(random_cho[2]);
            보기4.setText(random_cho[3]);
            보기1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    incorrect_event();
                    보기1.setTextColor(Color.WHITE);
                    보기1.setBackgroundResource(R.drawable.wrong_btn);
                    보기2.setTextColor(Color.WHITE);
                    보기2.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();
                }
            });

            보기2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    correct_event();
                    보기2.setTextColor(Color.WHITE);
                    보기2.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();

                }
            });

            보기3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    incorrect_event();
                    보기3.setTextColor(Color.WHITE);
                    보기3.setBackgroundResource(R.drawable.wrong_btn);
                    보기2.setTextColor(Color.WHITE);
                    보기2.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();
                }
            });

            보기4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    incorrect_event();
                    보기4.setTextColor(Color.WHITE);
                    보기4.setBackgroundResource(R.drawable.wrong_btn);
                    보기2.setTextColor(Color.WHITE);
                    보기2.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();
                }
            });

        } else if (random == 3) {
            보기3.setText(random_cho[0]);

            보기2.setText(random_cho[1]);
            보기1.setText(random_cho[2]);
            보기4.setText(random_cho[3]);

            보기1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    incorrect_event();
                    보기1.setTextColor(Color.WHITE);
                    보기1.setBackgroundResource(R.drawable.wrong_btn);
                    보기3.setTextColor(Color.WHITE);
                    보기3.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();
                }
            });

            보기2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    incorrect_event();
                    보기2.setTextColor(Color.WHITE);
                    보기3.setTextColor(Color.WHITE);
                    보기2.setBackgroundResource(R.drawable.wrong_btn);
                    보기3.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();
                }
            });

            보기3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    correct_event();
                    보기3.setTextColor(Color.WHITE);
                    보기3.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();

                }
            });

            보기4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    incorrect_event();
                    보기4.setTextColor(Color.WHITE);
                    보기3.setTextColor(Color.WHITE);
                    보기4.setBackgroundResource(R.drawable.wrong_btn);
                    보기3.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();
                }
            });
        } else {
            보기4.setText(random_cho[0]);

            보기2.setText(random_cho[1]);
            보기3.setText(random_cho[2]);
            보기1.setText(random_cho[3]);

            보기1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    incorrect_event();
                    보기1.setTextColor(Color.WHITE);
                    보기4.setTextColor(Color.WHITE);
                    보기1.setBackgroundResource(R.drawable.wrong_btn);
                    보기4.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();
                }
            });

            보기2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    incorrect_event();
                    보기2.setTextColor(Color.WHITE);
                    보기4.setTextColor(Color.WHITE);
                    보기2.setBackgroundResource(R.drawable.wrong_btn);
                    보기4.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();
                }
            });

            보기3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    incorrect_event();
                    보기3.setTextColor(Color.WHITE);
                    보기4.setTextColor(Color.WHITE);
                    보기3.setBackgroundResource(R.drawable.wrong_btn);
                    보기4.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();
                }
            });

            보기4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    correct_event();
                    보기4.setTextColor(Color.WHITE);
                    보기4.setBackgroundResource(R.drawable.word_button_choose);
                    보기_setClickable_F();
                }
            });
        }
    }

    //맞았을 때 이벤트.
    public void correct_event()
    {
        correct_Img.setVisibility(View.VISIBLE); //이미지 보여지게.

        mRunnable = new Runnable() {
            @Override
            public void run() {
                correct_Img.setVisibility(View.INVISIBLE);
            }
        };
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1000); //자동으로 사라짐.
        countDownTimer.cancel();
        Correct_Point++; //포인트 증가
        //NEXT버튼 자동클릭됨
        //NEXT.performClick();
    }

    //틀렸을 경우 이벤트
    public void incorrect_event()
    {
        incorrect_lmg.setVisibility(View.VISIBLE);
        mRunnable = new Runnable()
        {
            @Override
            public void run() {
                incorrect_lmg.setVisibility(View.INVISIBLE);
            }
        };
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1000); //1초후 mRunnable실행
        countDownTimer.cancel();
        Incorrect_Point++;
    }

    //170329 수정부분 정답, 오답 같이 나오게 합침, 보기들중 앞글자가 같으면 다시 생성하게 만듬.
    public String[] random_보기(){
        // 정답 생성
        int answer = (int) (Math.random() * listItem.size())+1;
        int no_answer[] = new int[4];
        no_answer[0] = answer;
        Log.d("no_answer", Integer.toString(answer));
        query = new String[5];
        //query[0] = listItem.get(no_answer[0]).getMean(); //정답 보기 뜻
        query[0] = "";
        while (query[0].equals("")){ //query[0]이 공백이 아닐때까지 반복
            int ranMean = (int) (Math.random() * 3) + 1;
            Log.i("이것", Integer.toString(ranMean));

            if (ranMean == 1){
                query[0] = listItem.get(no_answer[0]).getMean1();
            }else if (ranMean == 2){
                query[0] = listItem.get(no_answer[0]).getMean2();
            }else {
                query[0] = listItem.get(no_answer[0]).getMean3();
            }
            Log.i("이것", query[0]);
        }

        query[4] = listItem.get(no_answer[0]).getWord(); //정답 보기 영어
        item = listItem.get(no_answer[0]);
        Log.d("no_answer2", query[0].toString());
        Log.d("no_answer3", query[4].toString());

        int ran;
        for (int i = 1; i < 4; i++) {          //amount 만큼의 랜덤 숫자 구하기
            ran = (int) (Math.random() * listItem.size()) + 2; //전체 단어 갯수
            no_answer[i] = ran;
            if(no_answer[i] == answer){ //정답과 오답보기가 같을 경우
                i--;
            }else{ //다를 경우
                query[i] = listItem.get(no_answer[i]).getMean1();
            }
        }
        if(query[1].charAt(0) == query[2].charAt(0) || query[1].charAt(0) == query[3].charAt(0) || query[2].charAt(0) == query[3].charAt(0)){
            for (int i = 1; i < 4; i++) {          //amount 만큼의 랜덤 숫자 구하기
                ran = (int) (Math.random() * listItem.size()) + 1; //전체 단어 갯수
                no_answer[i] = ran;
                if(no_answer[i] == answer){ //정답과 오답보기가 같을 경우
                    i--;
                }else{ //다를 경우
                    query[i] = listItem.get(no_answer[i]).getMean1();
                }
            }
        }
        return query;
    }

    //액션바 뒤로가기 이벤트 등록
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, FragMain.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //보기 중복 클릭 불가하게 만들기.
    public void 보기_setClickable_F()
    {
        보기1.setClickable(false);
        보기2.setClickable(false);
        보기3.setClickable(false);
        보기4.setClickable(false);
    }

    public void 보기_setClickable_T()
    {
        보기1.setClickable(true);
        보기2.setClickable(true);
        보기3.setClickable(true);
        보기4.setClickable(true);
    }

    public void countDownTimer(){
        countDownTimer = new CountDownTimer(ConfigAll.ExamTimerAll_ms,ConfigAll.ExamTimerCount_ms )
        {
            public void onTick(long millisUntilFinished)
            {
                int time = (int)(millisUntilFinished/ConfigAll.ExamTimerCount_ms);
//                Log.d("Timer : ", " " + Integer.toString(time));
                switch(time){
                    case 4 :
                        pic_time.setText("4");
                        break;
                    case 3 :
                        pic_time.setText("3");
                        break;
                    case 2 :
                        pic_time.setText("2");
                        break;
                    case 1 :
                        pic_time.setText("1");
                        break;
                }
            }
            public void onFinish() {
                pic_time.setText("0");
                보기_setClickable_F();
                incorrect_event();

                //시간 초과후 정답표시
                if(random==1){
                    보기1.setTextColor(Color.WHITE);
                    보기1.setBackgroundResource(R.drawable.word_button_choose);
                }
                else if(random==2){
                    보기2.setTextColor(Color.WHITE);
                    보기2.setBackgroundResource(R.drawable.word_button_choose);
                }
                else if(random==3){
                    보기3.setTextColor(Color.WHITE);
                    보기3.setBackgroundResource(R.drawable.word_button_choose);
                }
                else {
                    보기4.setTextColor(Color.WHITE);
                    보기4.setBackgroundResource(R.drawable.word_button_choose);
                }
            }
        };
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            countDownTimer.cancel();
        } catch (Exception e) {}
        countDownTimer=null;
    }
}

//결과 화면으로 갈 때 넘길 값.
class TestResult implements Serializable {
    int correct_int;
    int total_int;

    public TestResult (int correct, int total) {
        super();
        this.correct_int = correct;
        this.total_int = total;
    }
    public int getCorrect_int() {
        return correct_int;
    }
    public int getTotal_int() {
        return total_int;
    }
}
