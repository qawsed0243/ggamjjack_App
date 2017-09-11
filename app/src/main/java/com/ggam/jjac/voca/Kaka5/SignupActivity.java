package com.ggam.jjac.voca.Kaka5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ggam.jjac.voca.Frag.FragMain;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

/**
 * 유효한 세션이 있다는 검증 후
 * me를 호출하여 가입 여부에 따라 가입 페이지를 그리던지 Main 페이지로 이동 시킨다.
 */

public class SignupActivity extends Activity {

    /**
     * Main으로 넘길지 로그인 페이지를 그릴지 판단하기 위해 me를 호출한다.
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Logger.d("UserProfile : " + userProfile);
                redirectMainActivity();
            }

            @Override
            public void onNotSignedUp() {
                requestSignUp();
            }
        });
    }

    private void requestSignUp() {
        UserManagement.requestSignup(new ApiResponseCallback<Long>() {
            @Override
            public void onNotSignedUp() {
            }

            @Override
            public void onSuccess(Long result) {
                requestMe();
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                final String message = "UsermgmtResponseCallback : failure : " + errorResult;
                Logger.w(message);
                finish();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
            }
        }, null);
    }

    protected void redirectMainActivity() {
        startActivity(new Intent(this, FragMain.class));
        finish();
    }
    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
