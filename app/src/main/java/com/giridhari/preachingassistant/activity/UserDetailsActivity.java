package com.giridhari.preachingassistant.activity;

import android.os.Bundle;
import android.util.Log;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.model.UserAccountDetailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsActivity extends APIActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        UserAccountDetailResponse userAccountDetailResponse = new UserAccountDetailResponse();

        preachingAssistantService.getUserAccounts("Basic YWRtaW46YWRtaW4=", "admin").enqueue(new Callback<UserAccountDetailResponse>() {
            @Override
            public void onResponse(Call<UserAccountDetailResponse> call, Response<UserAccountDetailResponse> response) {
                setView(response.body());
            }

            @Override
            public void onFailure(Call<UserAccountDetailResponse> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
        setView(userAccountDetailResponse);
    }

    private void setView(UserAccountDetailResponse userAccountDetailResponse) {
        Log.d("UserAccountRespomse", userAccountDetailResponse.toString());
    }
}
;