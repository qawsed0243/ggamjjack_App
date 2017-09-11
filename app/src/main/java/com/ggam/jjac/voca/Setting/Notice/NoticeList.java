package com.ggam.jjac.voca.Setting.Notice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ggam.jjac.voca.ConfigAll;
import com.ggam.jjac.voca.R;
import com.github.aakira.expandablelayout.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NoticeList extends AppCompatActivity {

    ArrayList<NoticeItem> noticelist= new ArrayList<NoticeItem>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        recyclerView = (RecyclerView) findViewById(R.id.notirecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // RequestQueue를 새로 만들어준다.
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request를 요청 할 URL
        String url = ConfigAll.NoticeAddr;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String num, title, content, up_date;
                            JSONArray results = response.getJSONArray("result");
                            for(int i=0; i<results.length(); i++){
                                JSONObject jsonObject = (JSONObject) results.get(i);
                                num = jsonObject.getString("num");
                                title = jsonObject.getString("title");
                                content = jsonObject.getString("content");
                                up_date = jsonObject.getString("up_date");
                                noticelist.add(new NoticeItem(num, title, content, up_date, R.color.cardview_shadow_start_color,
                                        R.color.cardview_shadow_end_color, Utils.createInterpolator(Utils.FAST_OUT_LINEAR_IN_INTERPOLATOR)));
                                recyclerView.setAdapter(new nRecyclerViewRecyclerAdapter(noticelist));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // queue에 Request를 추가해준다.
        queue.add(jsonObjectRequest);
    }
}
