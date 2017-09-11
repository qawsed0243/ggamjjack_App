package com.ggam.jjac.voca;

import android.os.AsyncTask;

import com.ggam.jjac.voca.Vocacard.Voca;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.ggam.jjac.voca.Vocacard.Su_low_Activity.Voca_card;
import static com.ggam.jjac.voca.Vocacard.Su_mid_Activity.Voca_card2;
import static com.ggam.jjac.voca.Vocacard.Su_high_Activity.Voca_card3;
import static com.ggam.jjac.voca.Vocacard.Toe_low_Activity.Voca_card4;
import static com.ggam.jjac.voca.Vocacard.Toe_mid_Activity.Voca_card5;
import static com.ggam.jjac.voca.Vocacard.Toe_high_Activity.Voca_card6;
/**
 * Created by YoungJung on 2017-03-03.
 */
// PHP <-> DB 단어 받아오기
public class phpDown extends AsyncTask<String, Integer,String> {

    @Override
    protected String doInBackground(String... urls) {
        StringBuilder jsonHtml = new StringBuilder();
        try{
            // 연결 url 설정
            URL url = new URL(urls[0]);
            // 커넥션 객체 생성
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            // 연결되었으면.
            if(conn != null)
            {
                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);
                // 연결되었음 코드가 리턴되면.
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    for(;;)
                    {
                        // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                        String line = br.readLine();
                        if(line == null) break;
                        // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                        jsonHtml.append(line + "\n");
                    }
                    br.close();
                }
                conn.disconnect();
            }
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return jsonHtml.toString();
    }
    protected void onPostExecute(String str) {
        try {
          //  Log.d("eng4"," " + str.length());
            JSONObject root = new JSONObject(str);
            JSONArray low_level = root.getJSONArray("low");
            JSONArray mid_level = root.getJSONArray("mid");
            JSONArray high_level = root.getJSONArray("high");
            JSONArray su_low = root.getJSONArray("su_low");
            JSONArray su_mid = root.getJSONArray("su_mid");
            JSONArray su_high = root.getJSONArray("su_high");

            lv_word(low_level, Voca_card4);
            lv_word(mid_level, Voca_card5);
            lv_word(high_level, Voca_card6);
            lv_word(su_low, Voca_card);
            lv_word(su_mid, Voca_card2);
            lv_word(su_high, Voca_card3);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void lv_word(JSONArray Jarray, ArrayList<Voca> Aarray) {
        try {
            for (int i = 0; i < Jarray.length(); i++) {
                JSONObject jo = Jarray.getJSONObject(i);
                if (!(jo.getString("english").equals(""))) {
                    String english = jo.getString("english");
                    String hanguel = jo.getString("hanguel");
                    String hanguel2 = jo.getString("hanguel2");
                    String hanguel3 = jo.getString("hanguel3");
                    String grammar = jo.getString("grammar");
                    int index = jo.getInt("d");
                    Aarray.add(new Voca(index, english, hanguel, hanguel2, hanguel3, grammar));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}