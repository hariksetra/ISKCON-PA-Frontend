package com.giridhari.preachingassistant.client;

import com.giridhari.preachingassistant.model.DevoteeCreateRequest;
import com.giridhari.preachingassistant.model.DevoteeDetailsResponse;
import com.giridhari.preachingassistant.model.DevoteeListResponse;
import com.giridhari.preachingassistant.model.UserAccountDetailResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by shyam on 18/9/16.
 */
public interface PreachingAssistantService
{

    @GET("/userAccounts/search/findByUsername")
    public Call<UserAccountDetailResponse> getUserAccount(@Header(value = "Authorization") String authKey, @Query("username") String username);

    @GET("/devotees")
    public Call<DevoteeListResponse> getDevoteeList(@Header(value = "Authorization") String authKey);

    @POST("/devotees")
    public Call<DevoteeDetailsResponse> createDevotee(@Header(value = "Authorization") String authKey, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body DevoteeCreateRequest devoteeCreateRequest);

}
