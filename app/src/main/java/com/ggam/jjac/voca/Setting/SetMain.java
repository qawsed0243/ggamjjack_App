package com.ggam.jjac.voca.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.ggam.jjac.voca.Kaka5.LoginActivity;
import com.ggam.jjac.voca.R;
import com.ggam.jjac.voca.Setting.Notice.NoticeList;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

/**
 * Created by YoungJung on 2017-06-22.
 */

public class SetMain extends AppCompatActivity implements View.OnClickListener{
    TextView noti, logout, lv, alarm;
    Switch mswitch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        noti = (TextView) findViewById(R.id.tv_noti);
        logout = (TextView) findViewById(R.id.tv_logout);
        lv = (TextView) findViewById(R.id.tv_lv);
        alarm = (TextView) findViewById(R.id.tv_alarm);
        mswitch = (Switch) findViewById(R.id.mSwtich);
        noti.setOnClickListener(this);
        logout.setOnClickListener(this);
        lv.setOnClickListener(this);
        alarm.setOnClickListener(this);
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
            case R.id.tv_alarm :
                break;
        }
    }
}
