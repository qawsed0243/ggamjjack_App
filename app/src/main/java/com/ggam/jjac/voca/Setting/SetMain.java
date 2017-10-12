package com.ggam.jjac.voca.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Switch;
import android.widget.TextView;

import com.ggam.jjac.voca.ConfigAll;
import com.ggam.jjac.voca.Kaka5.LoginActivity;
import com.ggam.jjac.voca.R;
import com.ggam.jjac.voca.Setting.Notice.NoticeList;
import com.ggam.jjac.voca.webview;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import static com.ggam.jjac.voca.Fcm.FirebaseMessagingService.OnOffPush;

/**
 * Created by YoungJung on 2017-06-22.
 */

public class SetMain extends AppCompatActivity implements View.OnClickListener{
    TextView noti, use, lv, logout, alarm, center, indi, serviceuse, faq;
    Switch mswitch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        noti = (TextView) findViewById(R.id.tv_noti);
        logout = (TextView) findViewById(R.id.tv_logout);
        lv = (TextView) findViewById(R.id.tv_lv);
        alarm = (TextView) findViewById(R.id.tv_alarm);

        use = (TextView) findViewById(R.id.tv_use);
        center = (TextView) findViewById(R.id.tv_center);
        indi = (TextView) findViewById(R.id.tv_indi);
        serviceuse = (TextView) findViewById(R.id.tv_serviceuse);
        faq = (TextView) findViewById(R.id.tv_faq);


        //mswitch = (Switch) findViewById(R.id.mSwtich);
        noti.setOnClickListener(this);
        logout.setOnClickListener(this);
        lv.setOnClickListener(this);
        alarm.setOnClickListener(this);

        use.setOnClickListener(this);
        center.setOnClickListener(this);
        indi.setOnClickListener(this);
        serviceuse.setOnClickListener(this);
        faq.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_noti :
                startActivity(new Intent(this, NoticeList.class));
                break;
            case R.id.tv_logout:
                UserManagement.requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        final Intent intent = new Intent(SetMain.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }
                });
                break;
            case R.id.tv_lv :
                startActivity(new Intent(this, LevelActivity.class));
                break;
            case R.id.tv_alarm:
                if (OnOffPush == true){
                    alarm.setText("Off");
                    OnOffPush = false;
                }
                else {
                    alarm.setText("On");
                    OnOffPush = true;
                }
                break;
            case R.id.tv_use :
                Intent intent1 = new Intent(this, webview.class);
                intent1.putExtra("kind", ConfigAll.UsePage);
                startActivity(intent1);
                break;
            case R.id.tv_center :
                Intent intent2 = new Intent(this, webview.class);
                intent2.putExtra("kind", ConfigAll.QnaPage);
                startActivity(intent2);
                break;
            case R.id.tv_indi :
                Intent intent3 = new Intent(this, webview.class);
                intent3.putExtra("kind", ConfigAll.PrivatePage);
                startActivity(intent3);
                break;
            case R.id.tv_serviceuse :
                Intent intent4 = new Intent(this, webview.class);
                intent4.putExtra("kind", ConfigAll.UsePage);
                startActivity(intent4);
                break;
            case R.id.tv_faq :
                Intent intent5 = new Intent(this, webview.class);
                intent5.putExtra("kind", ConfigAll.FaqPage);
                startActivity(intent5);
                break;
        }
    }
}
