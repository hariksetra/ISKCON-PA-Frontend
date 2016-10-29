package com.giridhari.preachingassistant.client;

import com.giridhari.preachingassistant.model.UserAccountDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by shyam on 18/9/16.
 */
public interface PreachingAssistantService
{

    @GET("/userAccounts/search/findByUsername")
    public Call<UserAccountDetailResponse> getUserAccounts(@Header(value = "Authorization") String authKey, @Query("username") String username);

}
