package com.ggam.jjac.voca.Point;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.ggam.jjac.voca.R;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.util.helper.log.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by HANSUNG on 2017-06-22.
 */

public class PointActivity extends BaseActivity {
    TextView tv_point;
    ProgressBar pro_point;
    Button btnRefund; //환급버튼
    ListView listView;
    Context c;
    private ArrayList<String> date_list; //적립 내역 리스트
    private ArrayList<String> why_list; //적립 내역 리스트
    private ArrayList<String> point_list; //적립 내역 리스트


    ProgressDialog dialog_;

   List_Adapter add_adapter;
   List_Adapter refund_adapter;
    String POINT=""; //현재 포인트

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("마이 포인트");

        date_list = new ArrayList<String>();
        why_list = new ArrayList<String>();
        point_list = new ArrayList<String>();
        btnRefund=(Button)findViewById(R.id.btn_refund);
        tv_point = (TextView) findViewById(R.id.tv_point);
        pro_point = (ProgressBar) findViewById(R.id.probar_point);
        listView=(ListView) findViewById(R.id.list_point);

        c=PointActivity.this;
        pro_point.setProgress(30);
        btnRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRefund(btnRefund);
            }
        });

    }
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class List_Adapter extends BaseAdapter
    {
        private LayoutInflater inflater;
        private ArrayList<String> date;
        private ArrayList<String> why;
        private ArrayList<String> point;
        private int layout;

        public List_Adapter(Context context, int layout, ArrayList<String> date, ArrayList<String> why,ArrayList<String> point ) {
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.date = date;
            this.why = why;
            this.point = point;

            this.layout = layout;
        }

        @Override
        public int getCount() {
            return date.size();
        }

        @Override
        public String getItem(int position) {
            return date.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = inflater.inflate(layout, parent, false);
            }

//textViewDay , textViewWhy , textViewPoint


            String date_s = date.get(position);
            String why_s = why.get(position);
            String point_s = point.get(position);

            ImageView icon = (ImageView) convertView.findViewById(R.id.imageview);
            //  icon.setImageResource(listviewitem.getIcon());

            TextView textViewDay = (TextView) convertView.findViewById(R.id.textViewDay);
            TextView textViewWhy = (TextView) convertView.findViewById(R.id.textViewWhy);
            TextView textViewPoint = (TextView) convertView.findViewById(R.id.textViewPoint);

            if(!why_s.equals("학습하기"))
                icon.setImageResource(R.drawable.getpoint_icon);

                textViewDay.setText(date_s.substring(0, 10));
                textViewWhy.setText(why_s);
                textViewPoint.setText(point_s);

            return convertView;
        }
    }


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

                String url = ConfigAll.PointInfoAddr;

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response


                                String utf="";

                                try
                                {
                                    //  response =  new String(response.getBytes(), "utf-8");
                                    //   response.replace("\uFFFD","");//오류 해결 , 인코딩 문제

                                    //  response= response.substring(3);
                                    //utf = URLEncoder.encode(response,"UTF-8");

                                    response = new String(response.getBytes() , Charset.forName("UTF-8"));


                                    Log.d("Response ? : ", response);

                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                    Log.d("fail ? : ", response);

                                }


                                try {

                                    JSONObject root = new JSONObject(response);

                                    JSONArray point = root.getJSONArray("point");
                                    JSONArray add_point = root.getJSONArray("add_result");
                                    JSONArray refund_point = root.getJSONArray("refund_result");



                                    for (int i = 0; i < point.length(); i++)
                                    {
                                        JSONObject jo = point.getJSONObject(i);
                                        POINT = jo.getString("point");

                                    }
                                    String time="";
                                    String why="";
                                    String points="";
                                    for (int i=0;i <add_point.length();i++)
                                    {
                                        JSONObject jo = add_point.getJSONObject(i);

                                        time = jo.getString("time");
                                        why = jo.getString("why");
                                        points = jo.getString("add_point");
                                        points +="P";

                                        if(!why.equals("학습하기")) {
                                            date_list.add(time);
                                            why_list.add(why);
                                            point_list.add(points);
                                        }
                                        // Log.d("time ? : ", time);
                                    }


                                    for (int i=0;i <refund_point.length();i++)
                                    {
                                        JSONObject jo = refund_point.getJSONObject(i);

                                        time = jo.getString("time");
                                        why = jo.getString("errCode");
                                        points = jo.getString("refund_point");
                                        points +="P";

                                        if(!why.equals("학습하기")) {
                                            date_list.add(time);
                                            why_list.add(why);
                                            point_list.add(points);
                                        }
                                        // Log.d("time ? : ", time);
                                    }


                                    add_adapter = new List_Adapter(c,R.layout.point_item,date_list,why_list,point_list);
                                    listView.setAdapter(add_adapter);
                                    refund_adapter = new List_Adapter(c,R.layout.point_item,date_list,why_list,point_list);
                                    listView.setAdapter(refund_adapter);


                                    tv_point.setText(POINT+"P");
                                    int x = Integer.parseInt(POINT)/100;
                                    pro_point.setProgress(x);

                                    dialog_.cancel();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog_.cancel();
                                Toast.makeText(getApplicationContext(), "서버 연결 실패", Toast.LENGTH_LONG).show();
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
    public void onClickRefund(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_refund: //환급버튼
                Intent intent = new Intent(this, RefundActivity.class);
                intent.putExtra("POINT", POINT);
                startActivity(intent);
                //finish();
                break;
        }
    }


    @Override
    public void onResume()
    {
        Log.d(this.getClass().getSimpleName(), "onResume()");
        super.onResume();
        requestAccessTokenInfo();

        date_list.clear();
        why_list.clear();
        point_list.clear();
    }
}
