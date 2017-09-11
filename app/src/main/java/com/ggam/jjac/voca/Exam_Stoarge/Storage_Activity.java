package com.ggam.jjac.voca.Exam_Stoarge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ggam.jjac.voca.AdlibTestProjectConstants;
import com.ggam.jjac.voca.BaseActivity;
import com.ggam.jjac.voca.Exam.ExamActivity;
import com.ggam.jjac.voca.R;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.mocoplex.adlib.AdlibAdViewContainer;
import com.mocoplex.adlib.AdlibManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import android.os.Handler;
import android.widget.Toast;

import static com.ggam.jjac.voca.R.id.tv_sentence;


/**
 * Created by HANSUNG on 2017-06-21.
 */

public class Storage_Activity extends BaseActivity {
    ListView listView;
    StorageAdapter storageAdapter = new StorageAdapter();
    private AdView adView = null;
    public static int exam=0;
    public static int num=0;

    // 일반 Activity 에서의 adlib 연동
    private AdlibManager adlibManager;

    // 리스트뷰 업데이트
    @Override
    public void onStop() {
        super.onStop();
        recreate();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        SharedPreferences level=getSharedPreferences("levelchoice", 0);
        num = level.getInt("level", 1);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("시험지보관함");

        // 홈 아이콘 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // db불러오기
        final StorageDBHelper ExdbManager = new StorageDBHelper(getApplicationContext(), "EXAM.db", null, 1);
        HashMap<String , String> storelist = ExdbManager.getResult();

        // HashMap에 포함된 Key , Value를 Set에 담고 iterator에 값을 Set 정보를 담아 준다.
        // Key = 시험지 번호 , Value = 시험지를 받은 시간
        Set<Map.Entry<String, String>> set = storelist.entrySet();
        Iterator<Map.Entry<String, String>> it = set.iterator();

        // HashMap에 포함된 key, value 값을 호출 한다.
        while (it.hasNext()) {
            // 값을 받아와서 adapter에 추가
            Map.Entry<String, String> e = (Map.Entry<String, String>)it.next();
            storageAdapter.addItem(e.getKey().toString(), Integer.parseInt(e.getValue()));
        }

        // 리스트뷰 참조 및 Adapter달기
        listView = (ListView) findViewById(R.id.storage_view);
        listView.setAdapter(storageAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.e("test", "" + Integer.toString(position));
                ExdbManager.delete(storageAdapter.getDeleteItem(position));
                // 클릭 불가능
                exam=100;
                Intent i = new Intent(Storage_Activity.this, ExamActivity.class);
                startActivity(i);
                finish();
            }
        });

        // 페이스북 광고 테스트
        List<String> testDevices = new ArrayList<>();
        testDevices.add("oL8x4+xuI/sZE8FPmg3F31ghC+E=");
        AdSettings.addTestDevices(testDevices);


        // 페이스북 배너광고
        RelativeLayout adViewContainer = (RelativeLayout) findViewById(R.id.adViewContainer);
        adView = new AdView(this, "252798468547112_252809075212718", AdSize.BANNER_320_50);
        adViewContainer.addView(adView);
        adView.loadAd();

        // 애드립 배너광고
        // 각 애드립 액티비티에 애드립 앱 키값을 필수로 넣어주어야 합니다.
        adlibManager = new AdlibManager(AdlibTestProjectConstants.ADLIB_API_KEY);
        adlibManager.onCreate(this);
        // 테스트 광고 노출로, 상용일 경우 꼭 제거해야 합니다.
        adlibManager.setAdlibTestMode(AdlibTestProjectConstants.ADLIB_TEST_MODE);
        // 배너 스케쥴에 등록된 광고 모두 광고 요청 실패 시 대기 시간 설정(단위:초, 기본:10초, 최소:1초)
        // adlibManager.setBannerFailDelayTime(10);

        // 배너 스케쥴 요청 실패 시 대기 시간동안 노출되는 View 설정
        // View backFill = new View(this);
        // backFill.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // adlibManager.setBannerBackfillView(backFill);

        adlibManager.setAdsHandler(new Handler() {
            public void handleMessage(Message message) {
                try {
                    switch (message.what) {
                        case AdlibManager.DID_SUCCEED:
                            Log.d("ADLIBr", "[Banner] onReceiveAd " + (String) message.obj);
                            break;
                        case AdlibManager.DID_ERROR:
                            Log.d("ADLIBr", "[Banner] onFailedToReceiveAd " + (String) message.obj);
                            break;
                        case AdlibManager.BANNER_FAILED:
                            Log.d("ADLIBr", "[Banner] All Failed.");
                            Toast.makeText(Storage_Activity.this, "광고수신 실패 :)", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (Exception e) {

                }
            }
        });

        // 띠배너 미디에이션 뷰 설정 및 시작
        adlibManager.setAdsContainer(R.id.ads);

        //애드립 클릭 광고


    }
    private AdlibAdViewContainer getAdlibAdViewContainer() {
        // 액티비티에 AdlibAdViewContainer 는 하나만 허용합니다.
        // 두개 이상 로드될 경우 문제가 발생할 수 있으며
        // 새로 로드하는 경우 반드시 기존 광고뷰를 삭제하고 다시 바인드 해주세요.

        // 동적으로 adview 를 생성합니다.
        AdlibAdViewContainer avc = new com.mocoplex.adlib.AdlibAdViewContainer(this);
        ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        avc.setLayoutParams(p);

        return avc;
    }

    // 동적으로 Container 를 생성하여, 그 객체를 통하여 BIND 하는 경우
    private void bindAdsContainer(AdlibAdViewContainer a) {
        adlibManager.bindAdsContainer(a);
    }

    // AD 영역을 동적으로 삭제할때 호출하는 메소드
    private void destroyAdsContainer() {
        adlibManager.destroyAdsContainer();
    }






    //애드립 클릭광고
    public void onClick(View v) {

        // 전면광고를 호출합니다. (미디에이션 전면배너 요청)
        //adlibManager.loadFullInterstitialAd(this);

        // optional : 전면광고의 수신 성공, 실패 이벤트 처리가 필요한 경우엔 handler를 이용하실 수 있습니다. (미디에이션 전면배너 요청)
        adlibManager.loadFullInterstitialAd(this, new Handler() {
            public void handleMessage(Message message) {
                try {
                    switch (message.what) {
                        case AdlibManager.DID_SUCCEED:
                            Log.d("ADLIBr", "[Interstitial] onReceiveAd " + (String) message.obj);
                            break;
                        // 전면배너 스케줄링 사용시, 각각의 플랫폼의 수신 실패 이벤트를 받습니다.
                        case AdlibManager.DID_ERROR:
                            Log.d("ADLIBr", "[Interstitial] onFailedToReceiveAd " + (String) message.obj);
                            break;
                        // 전면배너 스케줄로 설정되어있는 모든 플랫폼의 수신이 실패했을 경우 이벤트를 받습니다.
                        case AdlibManager.INTERSTITIAL_FAILED:
                            Toast.makeText(Storage_Activity.this, "광고수신 실패 :)", Toast.LENGTH_SHORT).show();
                            Log.d("ADLIBr", "[Interstitial] All Failed.");
                            break;
                        case AdlibManager.INTERSTITIAL_CLOSED:
                            Log.d("ADLIBr", "[Interstitial] onClosedAd " + (String) message.obj);
                            break;
                    }
                } catch (Exception e) {
                }
            }
        });
    }



    @Override
    protected void onResume() {
        adlibManager.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        adlibManager.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        adlibManager.onDestroy(this);
        super.onDestroy();
    }
    //액션바 뒤로가기 이벤트 등록
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
