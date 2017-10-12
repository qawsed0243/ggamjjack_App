package com.ggam.jjac.voca.Exam;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ggam.jjac.voca.BaseActivity;
import com.ggam.jjac.voca.ConfigAll;
import com.ggam.jjac.voca.Exam_Stoarge.Storage_Activity;
import com.ggam.jjac.voca.Frag.FragMain;
import com.ggam.jjac.voca.Frag.TabFragment3;
import com.ggam.jjac.voca.R;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.util.helper.log.Logger;
import com.mocoplex.adlib.AdlibManager;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HANSUNG on 2017-06-20.
 */

public class ResultActivity extends AppCompatActivity{
    TextView correct, incorrect, point, tv_result;
    ImageView img_result;

    private AdlibManager adlibManager;
    int random=0;
    int correct_count;
    int incorrect_count;
    int point_count;
    boolean saving; //적립 여부 저장
    Context c;
    ProgressDialog dialog_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        correct = (TextView)findViewById(R.id.correct);
//        incorrect = (TextView)findViewById(R.id.incorrect);
//        point = (TextView)findViewById(R.id.point);
        img_result = (ImageView) findViewById(R.id.img_result);
        tv_result = (TextView) findViewById(R.id.tv_result);

        Button main_btn = (Button)findViewById(R.id.morestudy);
        Button addpoint = (Button)findViewById(R.id.addpoint);
        saving=false;
        c = this;

        Intent intent = getIntent();
        final TestResult Result = (TestResult) intent.getSerializableExtra("Result");
        correct_count = Result.getCorrect_int();  //맞은 갯수 넣음
        incorrect_count = Result.getTotal_int() - correct_count;  //총 갯수에서 맞은 갯수 빼기
        point_count = correct_count*1;  //포인트 정산. 지금은 *1로 계산

        if(correct_count ==10){
            img_result.setImageResource(R.drawable.point_back);
            tv_result.setText("포인트를 받을 수 있어요");

        }else if(correct_count>5){
            img_result.setImageResource(R.drawable.point_back_2);
            tv_result.setText("포인트를 받을 수 있어요");
        }else{
            img_result.setImageResource(R.drawable.point_back_3);
            tv_result.setText("아쉽게 포인트를 받을 수 없어요");
        }
        //화면 글씨 설정
        correct.setText(Integer.toString(correct_count));
//        incorrect.setText(Integer.toString(incorrect_count));
//        point.setText(Integer.toString(point_count));

        //메인화면 선택시
        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent intent = new Intent(getApplication(),FragMain.class);
              //  intent.putExtra("where",1);
              //   startActivity(intent);
                finish();
            }
        });

        addpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correct_count>5)
                {  //푸시시험 포인트정리
                    if(Storage_Activity.exam==100)
                    {
                        if(Storage_Activity.num==1||Storage_Activity.num==3) {
                            random =10;
                        }
                        else if(Storage_Activity.num==2||Storage_Activity.num==5) {
                            random = 15;
                        }
                        else if(Storage_Activity.num==3||Storage_Activity.num==6) {
                            random = 20;
                        }
                    }else {
                        //난이도에 따른 포인트 적립
                        if (TabFragment3.num == 1 || TabFragment3.num == 4) {
                            random = (int) ((Math.random() * 1000) + 1) % 10+1;
                        } else if (TabFragment3.num == 2 || TabFragment3.num == 5) {
                            random = (int) ((Math.random() * 121) + 1) % 11+5;
                        } else if (TabFragment3.num == 3 || TabFragment3.num == 6) {
                            random = (int) ((Math.random() * 121) + 1) % 11+10;
                        }
                    }

                    if(random!=0) {
                        if (!saving) {
                            dialog_ = ProgressDialog.show(c, "", "로딩 중입니다. 잠시 기다려주세요", true);
                            if(Storage_Activity.exam==100)
                            {
                                //동영상 광고 띄우기
                                Storage_Activity.exam=0;
                                requestAccessTokenInfo();
                            }
                            else {
                                //일반광고 띄우기
                                requestAccessTokenInfo();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "이미 포인트 적립을 하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "0 포인트가 적립되었습니다.", Toast.LENGTH_SHORT).show();
                        saving=true;
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "더 공부하세요!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*private void insertToDatabase(String id, String add_point){

        class InsertData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                try{
                    String id = (String)params[0];
                    String add_point = (String)params[1];
                    String link= ConfigAll.PointInsertAddr;
                    String data  = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data += "&" + URLEncoder.encode("add_point", "UTF-8") + "=" + URLEncoder.encode(add_point, "UTF-8");
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
        task.execute(id, add_point);
    }*/
    private void requestAccessTokenInfo()
    {
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
                Log.i("tokenid?????", Long.toString(userId));
                //HttpPostData();
                RequestQueue queue = Volley.newRequestQueue(c);

                String url = ConfigAll.PointInsert;


                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    dialog_.cancel();
                                    try {
                                        response = new String(response.getBytes(), Charset.forName("UTF-8"));
                                        response = response.replaceAll("\uFEFF", "");
                                        Log.d("Response ?]: success", response);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.d("fail ? : ", response);
                                    }
                                    if (response.equals("success")) //성공
                                    {
                                        Toast.makeText(getApplicationContext(), random +" 포인트가 적립되었습니다.", Toast.LENGTH_LONG).show();
                                        saving = true;
                                    } else {
                                        Toast.makeText(getApplicationContext(), "포인트가 적립이 실패하였습니다.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    dialog_.cancel();
                                    Toast.makeText(getApplicationContext(), "포인트가 적립이 실패하였습니다.", Toast.LENGTH_LONG).show();
                                    // error
                                    //Log.d("Error.Response", response);
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("id", Long.toString(userId));
                            params.put("why", ConfigAll.Study);
                            params.put("add_point", String.valueOf(random));
                            return params;
                        }
                    };
                    queue.add(postRequest);
            }
        });
    }
}
