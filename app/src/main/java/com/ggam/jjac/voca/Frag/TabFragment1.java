package com.ggam.jjac.voca.Frag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ggam.jjac.voca.ConfigAll;
import com.ggam.jjac.voca.Point.PointActivity;
import com.ggam.jjac.voca.R;
import com.ggam.jjac.voca.Vocacard.Su_high_Activity;
import com.ggam.jjac.voca.Vocacard.Su_low_Activity;
import com.ggam.jjac.voca.Vocacard.Su_mid_Activity;
import com.ggam.jjac.voca.Vocacard.Toe_high_Activity;
import com.ggam.jjac.voca.Vocacard.Toe_low_Activity;
import com.ggam.jjac.voca.Vocacard.Toe_mid_Activity;
import com.ggam.jjac.voca.Vocacard.Voca;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.util.helper.log.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TabFragment1 extends Fragment {
    // 홈

    Context c;
    LinearLayout point_linear;
    TextView point_cur;
    TextView voca,mean1,mean2,mean3, gram;
//    LinearLayout layout2;
//    FrameLayout layout1;
    public static int count=0;
    public static int random=0;
    public static int num=0;
    public static ArrayList<Voca> mainvoca = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);
        point_linear=(LinearLayout) view.findViewById(R.id.linear);
        point_cur=(TextView)view.findViewById(R.id.pointcur);
        c = getContext();
        voca=(TextView)view.findViewById(R.id.mainvoca);
        mean1=(TextView)view.findViewById(R.id.mainvocamean1);
        mean2=(TextView)view.findViewById(R.id.mainvocamean2);
        mean3=(TextView)view.findViewById(R.id.mainvocamean3);
        gram = (TextView)view.findViewById(R.id.gram);
//        layout1=(FrameLayout)view.findViewById(R.id.layout1);
//        layout2=(LinearLayout)view.findViewById(R.id.layout2);
        //point_cur=(TextView) view.findViewById(R.id.pointcur);


        //포인트 버튼 눌렀을 때 포인트 사용내역 관련 액티비티실행
        point_linear.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity(), PointActivity.class);
                startActivity(intent);
            }
        });

        /*
        c = getContext();
        //requestAccessTokenInfo();
        point_cur = (TextView) view.findViewById(R.id.point_current);
        point_ac = (TextView) view.findViewById(R.id.point_ac);

        ImageButton ex_store = (ImageButton) view.findViewById(R.id.imgbtn);
        //포인트 버튼 눌렀을 때 포인트 사용내역 관련 정보 받아 올 것.
        ex_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetMain.class);
                startActivity(intent);
            }
        });
        */


        //메인 단어 표시
        SharedPreferences level=getContext().getSharedPreferences("levelchoice", 0);
        num = level.getInt("level", 1);
        if(num==1)
            mainvoca= Su_low_Activity.Voca_card;
        else if(num==2)
            mainvoca= Su_mid_Activity.Voca_card2;
        else if(num==3)
            mainvoca= Su_high_Activity.Voca_card3;
        else if(num==4)
            mainvoca= Toe_low_Activity.Voca_card4;
        else if(num==5)
            mainvoca= Toe_mid_Activity.Voca_card5;
        else if(num==6)
            mainvoca= Toe_high_Activity.Voca_card6;

            count = mainvoca.size();

        if(count!=0) {
//            layout2.setVisibility(View.GONE);
//            layout1.setVisibility(View.VISIBLE);
            random = (int) (Math.random() * count);
        voca.setText(mainvoca.get(random).getWord());
        mean1.setText(mainvoca.get(random).getMean1());
        mean2.setText(mainvoca.get(random).getMean2());
        mean3.setText(mainvoca.get(random).getMean3());
        gram.setText(mainvoca.get(random).getGrammar());
        }
        else {
//            layout1.setVisibility(View.GONE);
//            layout2.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void requestAccessTokenInfo() {
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
                //HttpPostData();
                RequestQueue queue = Volley.newRequestQueue(c);

                String url = ConfigAll.UserInfoAddr;

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response#!@#!@#", response);
                                try {
                                    JSONObject root = new JSONObject(response);
                                    JSONArray ja = root.getJSONArray("result");
                                    for (int i = 0; i < ja.length(); i++)
                                    {
                                        JSONObject jo = ja.getJSONObject(i);
                                        String POINT = jo.getString("point");
                                        String TPOINT = jo.getString("total_point");

                                        point_cur.setText(POINT+"P");
                                        //point_ac.setText("누적("+TPOINT+"P)");
                                        //int x = Integer.parseInt(POINT)/1000;
                                        //progressBar.setProgress(x);
                                    }



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                //Log.d("Error.Response", response);
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("id", Long.toString(userId));
                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
    }

    @Override
    public void onResume()
    {
        Log.d(this.getClass().getSimpleName(), "onResume()");
        super.onResume();
        requestAccessTokenInfo();
    }

}
