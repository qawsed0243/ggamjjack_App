package com.ggam.jjac.voca.Frag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ggam.jjac.voca.BaseActivity;
import com.ggam.jjac.voca.ConfigAll;
import com.ggam.jjac.voca.DB.DBHelper;
import com.ggam.jjac.voca.Exam_Stoarge.Storage_Activity;
import com.ggam.jjac.voca.Point.PointActivity;
import com.ggam.jjac.voca.R;
import com.ggam.jjac.voca.Setting.SetMain;
import com.ggam.jjac.voca.Vocacard.Su_high_Activity;
import com.ggam.jjac.voca.Vocacard.Su_low_Activity;
import com.ggam.jjac.voca.Vocacard.Su_mid_Activity;
import com.ggam.jjac.voca.Vocacard.Toe_high_Activity;
import com.ggam.jjac.voca.Vocacard.Toe_low_Activity;
import com.ggam.jjac.voca.Vocacard.Toe_mid_Activity;
import com.ggam.jjac.voca.Vocacard.Voca;
import com.ggam.jjac.voca.Vocacard.myvoca;
import com.ggam.jjac.voca.phpDown;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kakao.kinsight.sdk.android.KinsightSession;

import java.util.ArrayList;

public class FragMain extends AppCompatActivity {
    phpDown task;
    public static KinsightSession kinsightSession;
    public static int num=1;

    // --------- 5fragment 메인화면 ---------

    //http://www.androidhive.info/2015/09/android-material-design-working-with-tabs/

    private TabLayout tabLayout;
    private ViewPager viewPager;
    DBHelper dbHelper;

    Toast toast; //화면에 특정 메세지 출력
    long backKeyPressedTime = 0; //시간

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_main);
        kinsightSession = new KinsightSession(getApplicationContext());
        kinsightSession.open();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("오늘또VOCA");



        //푸시시험 난이도 받아오기

        /*
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        */

        // Initializing the TabLayout
        tabLayout = (TabLayout)findViewById(R.id.tablayout);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.wordcard));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.test));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.watch));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.shopping));
        //tabLayout.setTabTextColors(R.color.black,R.color.black);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        task = new phpDown();
        task.execute(ConfigAll.WordToeicAddr);

        //fcm 구현
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();

        /*
        // android badge 구현중
        if(FirebaseInstanceId.getInstance().getToken() !=null){
            boolean success = ShortcutBadger.applyCount(Main2Activity.this, alarm_num);
            Toast.makeText(getApplicationContext(), "Set count=" + alarm_num + ", success=" + success, Toast.LENGTH_SHORT).show();
        }else{
            boolean success = ShortcutBadger.removeCount(Main2Activity.this);
            Toast.makeText(getApplicationContext(), "success=" + success, Toast.LENGTH_SHORT).show();
        }
        */

        //find the home launcher Package
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        //ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);

        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        // Creating TabPagerAdapter adapter
        TabFragAdapter pagerAdapter = new TabFragAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                FragMain.kinsightSession.tagScreen(String.format("tab%s", tab.getPosition()));
                int num = tab.getPosition();
                ActionBar actionBar = getSupportActionBar();
                switch(num) {
                    case 1:
                        actionBar.setTitle("학습하기");
                        break;
                    case 2:
                        actionBar.setTitle("시험보기");
                        break;
                    case 3:
                        actionBar.setTitle("쇼핑하기");
                        break;
                    case 4:
                        actionBar.setTitle("설정");
                        break;
                    default :
                        actionBar.setTitle("오늘또VOCA");
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_examstore :
                Intent intent1 = new Intent(this, Storage_Activity.class);
                startActivity(intent1);
                return true;
            case R.id.action_me :
                Intent intent2 = new Intent(this, myvoca.class);
                startActivity(intent2);
                return true;
            case R.id.action_setting :
                Intent intent3 = new Intent(this, SetMain.class);
                startActivity(intent3);
                //Toast.makeText(this, "셋팅 화면으로 이동", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        // 두번 뒤로 가기 했을 때 발생 되는 이벤트
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            toast.cancel();
            /*
            // 쉘위애드 후면광고
            final Intent intent = new Intent(Main2Activity.this, ExitDailogAdActivity.class);
            startActivity(intent);
            */
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        FragMain.kinsightSession.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragMain.kinsightSession.open();
    }

    void showGuide() {
        // 뒤로가기 한번 눌렀을때 나타나는 toast
        toast = Toast.makeText(this, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",
                Toast.LENGTH_SHORT);
        toast.show();
    }
}
