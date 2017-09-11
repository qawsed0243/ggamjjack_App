package com.ggam.jjac.voca.Point;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WBHS on 2017-08-14.
 */

public class RefundActivity extends BaseActivity {

    String POINT;

    EditText eName; //이름
    EditText eNo; //계좌번호
    EditText eMoney; // 환급 금액
    Spinner sBank;        //공기질 Spinner

    Context c;

    String name =""; //이름
    String money = ""; //환급금액
    String no =""; // 계좌번호
    String bank = "";

    AlertDialog.Builder alertDialogBuilder;

    AlertDialog alertDialog;

    ProgressDialog dialog_;

    TextView textViewMyPoint2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("환급하기");

        POINT= getIntent().getStringExtra("POINT");

        textViewMyPoint2=(TextView)findViewById(R.id.textViewMyPoint2);

        textViewMyPoint2.setText(POINT + "P");

        c=RefundActivity.this;

        eName = (EditText)findViewById(R.id.EditTextName);
        eNo = (EditText)findViewById(R.id.EditAccountNo);
        eMoney = (EditText)findViewById(R.id.EditTextMoney);

        sBank = (Spinner)findViewById(R.id.sbank);

        alertDialogBuilder  = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("환급받겠습니까?");

        alertDialogBuilder
                .setMessage("환급받겠습니까?")
                .setCancelable(false)
                .setPositiveButton("종료",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id)
                            {
                            }
                        })
                .setNegativeButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id)
                            {
                                dialog_ = ProgressDialog.show(c, "", "로딩 중입니다. 잠시 기다려주세요", true);
                                requestAccessTokenInfo();
                            }
                        });
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

    public void onClickPointOut(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonPointOut: //환급버튼

                name = eName.getText().toString();
                Editable et = eNo.getText();
                no=et.toString();
                et = eMoney.getText();
                money = et.toString();
                bank = sBank.getSelectedItem().toString();


                int out_point=0;
                int now_point=0;

                if(name.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "예금주를 입력 하세요", Toast.LENGTH_LONG).show();
                }
                else if(no.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "계좌번호를 입력 하세요", Toast.LENGTH_LONG).show();
                }
                else if(money.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "금액을 입력 하세요", Toast.LENGTH_LONG).show();
                }
                else {
                    out_point = Integer.parseInt(money);
                    now_point = Integer.parseInt(POINT);
                    if (now_point < out_point) {
                        Toast.makeText(getApplicationContext(), "잔여 포인트를 확인하세요", Toast.LENGTH_LONG).show();
                    } else if (out_point < 10000) {
                        Toast.makeText(getApplicationContext(), "최소 환전 금액은 10000포인트 입니다.", Toast.LENGTH_LONG).show();
                    } else {
                        Log.i("editText ? :", name + " , " + no + " ," + money + ", " + bank);

                        alertDialogBuilder.setMessage("예금주와 계좌번호가 일치하는지 확인하세요"
                                + "\n\n예금주 : " + name +
                                "\n은행 : " + bank +
                                "\n계좌번호 : " + no +
                                "\n금액 : " + money);
                        alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                    break;

                }

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

                String url = ConfigAll.PointOutAddr;

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response

                                dialog_.cancel();
                                String utf="";

                                try
                                {

                                    response = new String(response.getBytes() , Charset.forName("UTF-8"));
                                    response = response.replaceAll("\uFEFF", "");
                                    Log.d("Response ?]: success", response);

                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                    Log.d("fail ? : ", response);

                                }

                                if(response.equals("success")) //성공
                                {
                                    Toast.makeText(getApplicationContext(), "환전이 성공되었습니다. \n환전 요청으로 부터 약 3~4일 후 입금", Toast.LENGTH_LONG).show();
                                    finish();

                                }
                                else if(response.equals("noMoney")) //실패
                                {
                                    Toast.makeText(getApplicationContext(), "환전 금액이 부족합니다.", Toast.LENGTH_LONG).show();
                                }
                                else//실패
                                {
                                    Toast.makeText(getApplicationContext(), "환전을 실패하였습니다", Toast.LENGTH_LONG).show();
                                }

                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog_.cancel();
                                Toast.makeText(getApplicationContext(), "환전을 실패하였습니다", Toast.LENGTH_LONG).show();
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
                        params.put("out_point", money);
                        params.put("account_name",name);
                        params.put("account_no",no);
                        params.put("bank",bank);

                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
    }
}
