package com.ggam.jjac.voca.Vocacard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.ggam.jjac.voca.R;
import java.util.ArrayList;
/**
 * Created by HANSUNG on 2017-06-19.
 */

public class DicActivity extends Activity {
    private WebView mWebView;
    private WebSettings mWebSettings;
    ArrayList<Voca> listItem= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dic_view);

        mWebView = (WebView)findViewById(R.id.web);
        mWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        mWebSettings = mWebView.getSettings(); // 세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true); //자바스트립트 사용 허용
        get_begin();
        Intent intent = getIntent();
        String dic = intent.getExtras().getString("dic");
        Log.d("dic",dic);
        mWebView.loadUrl("http://m.endic.naver.com/search.nhn?searchOption=all&query="+dic+"&="); //원하는 URL 입력
    }

    public void get_begin(){
        for(int i = 0; i< Toe_low_Activity.Voca_card4.size(); i++){
            listItem.add(i, Toe_low_Activity.Voca_card4.get(i));
        }
    }
}
