package com.giridhari.preachingassistant.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.activity.APIActivity;
import com.giridhari.preachingassistant.model.DevoteeDetailsResponse;
import com.giridhari.preachingassistant.model.UserAccountDetailResponse;
import com.giridhari.preachingassistant.model.UserAccountListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickFollowUpMentorActivity extends APIActivity {

    private Toolbar toolbar;
    private ListViewCompat listViewDevotees;
    private Button buttonAssign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_follow_up_mentor);
        init();

        populateDevotees();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buttonAssign = (Button) findViewById(R.id.button_assign);
        listViewDevotees = (ListViewCompat) findViewById(R.id.list_view_devotees);
    }

    private void populateDevotees() {
        preachingAssistantService.getUserAccounts(getStringFromSharedPreferences(LoginActivity.AUTH_TOKEN)).enqueue(new Callback<UserAccountListResponse>() {
            @Override
            public void onResponse(Call<UserAccountListResponse> call, Response<UserAccountListResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body() != null && response.body().get_embedded() != null && response.body().get_embedded().containsKey("userAccounts")) {
                        List<UserAccountDetailResponse> userAccountList = response.body().get_embedded().get("userAccounts");
                        List<DevoteeDetailsResponse> devoteeList = new ArrayList<DevoteeDetailsResponse>();
                        for(UserAccountDetailResponse userAccount: userAccountList) {
                            if(userAccount.get_links() != null && userAccount.get_links().containsKey("profile")) {
                                preachingAssistantService.getDevoteeDetails(getStringFromSharedPreferences(LoginActivity.AUTH_TOKEN), userAccount.get_links().get("profile").get("href"))
                                        .enqueue(new Callback<DevoteeDetailsResponse>() {
                                    @Override
                                    public void onResponse(Call<DevoteeDetailsResponse> call, Response<DevoteeDetailsResponse> response) {
                                        // TODO set list view
                                    }

                                    @Override
                                    public void onFailure(Call<DevoteeDetailsResponse> call, Throwable t) {

                                    }
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserAccountListResponse> call, Throwable t) {

            }
        });
    }

}
