package com.ggam.jjac.voca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.ggam.jjac.voca.Kaka5.LoginActivity;

public class Splash extends AppCompatActivity {

    private static final int DELAY_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dispatch();
        startActivity(new Intent(this, LoginActivity.class));
        try{
            Thread.sleep(DELAY_TIME);
        }catch(Exception e){};
        this.finish();
    }
}
