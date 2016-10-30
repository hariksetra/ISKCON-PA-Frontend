package com.giridhari.preachingassistant.activity;

import com.giridhari.preachingassistant.client.PreachingAssistantService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shyam on 24/9/16.
 */
public class APIActivity extends BaseActivity
{

    protected PreachingAssistantService preachingAssistantService;

    protected APIActivity()
    {
        super();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.77.165.53")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        preachingAssistantService = retrofit.create(PreachingAssistantService.class);
    }


}
