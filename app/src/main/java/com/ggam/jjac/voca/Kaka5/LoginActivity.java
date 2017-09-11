package com.ggam.jjac.voca.Kaka5;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ggam.jjac.voca.BaseActivity;
import com.ggam.jjac.voca.ConfigAll;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;

import com.ggam.jjac.voca.R;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity {

    private SessionCallback callback;
    boolean check_input_INFO = false;     // 로그인 기록이 있으면

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getAppKeyHash();
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

        TextView tvLinkify = (TextView) findViewById(R.id.tv_custom_terms);
        String text = "로그인을 함으로써 깜짝시험영단어의 개인정보취급방침과 서비스이용약관에 동의하시게됩니다.";
        tvLinkify.setText(text); Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
            @Override public String transformUrl(Matcher match, String url) {
                return "";
            }
        };
        Pattern pattern1 = Pattern.compile("개인정보취급방침"); Pattern pattern2 = Pattern.compile("서비스이용약관");
        Linkify.addLinks(tvLinkify, pattern1, "https://play.google.com/intl/ko_kr/about/play-terms.html",null,mTransform);
        Linkify.addLinks(tvLinkify, pattern2, "https://www.apple.com/legal/internet-services/itunes/appstore/kr/terms.html",null,mTransform);
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            redirectSignupActivity();
            if (!check_input_INFO){
                AuthService.requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        //redirectLoginActivity(self);
                    }
                    @Override
                    public void onNotSignedUp() {
                        // not happened
                    }
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Logger.e("failed to get access token info. msg=" + errorResult);
                    }
                    @Override
                    public void onSuccess(AccessTokenInfoResponse accessTokenInfoResponse) {
                        final long userId = accessTokenInfoResponse.getUserId();
                        Log.i("tokenid!!!!!!!!!!!", Long.toString(userId));
                        insertToDatabase(Long.toString(userId));
                        check_input_INFO = true;
                    }
                });

            }
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
        }
    }

    //사용자 정보 DB 전달
    private void insertToDatabase(String id){
        class InsertData extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                try{
                    String id = (String)params[0];
                    String link= ConfigAll.InfoInsertAddr;
                    String data  = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write( data );
                    wr.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void redirectSignupActivity() {
        final Intent intent = new Intent(this, SignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(),PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.d("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }

}
