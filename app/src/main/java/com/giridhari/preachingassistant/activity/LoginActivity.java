package com.giridhari.preachingassistant.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.components.NetworkDialog;
import com.giridhari.preachingassistant.model.UserAccountDetailResponse;
import com.giridhari.preachingassistant.utility.ActivityManager;
import com.giridhari.preachingassistant.utility.HelperUtility;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends APIActivity implements View.OnClickListener,
        CheckBox.OnCheckedChangeListener
{

    ProgressBar progressBar;
    String username;
    private TextView forgetBtn;
    private NetworkDialog.networkDialogListener networkDialogListener;
    private Button loginButton;
    private TextView usernameEntered;
    private EditText passwordField;
    private TextView emailFieldError;
    private TextView passwordFieldError;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        assert findViewById(R.id.privacy_policy) != null;
        findViewById(R.id.privacy_policy).setOnClickListener(this);

        assert findViewById(R.id.passwordEye) != null;
        findViewById(R.id.passwordEye).setOnClickListener(this);
        assert findViewById(R.id.loginButton) != null;
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        loginButton.setAlpha(1.0f);

        assert findViewById(R.id.rememberme) != null;
        ((CheckBox) findViewById(R.id.rememberme)).setOnCheckedChangeListener(this);

        forgetBtn = (TextView) findViewById(R.id.forgetpassword);
        forgetBtn.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        networkDialogListener = new NetworkDialog.networkDialogListener()
        {
            @Override
            public void onButtonClick()
            {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        };


        emailFieldError = (TextView) findViewById(R.id.emailFieldStatus);
        usernameEntered = (TextView) findViewById(R.id.email);
        passwordFieldError = (TextView) findViewById(R.id.passwordFieldStatus);
        passwordField = (EditText) findViewById(R.id.password);


        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        //Setting the blur bg
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.splashscreen_blur);
        Bitmap blurredBitmap = BlurBuilder.blur(this.getApplicationContext(), originalBitmap);
        assert findViewById(R.id.landingScreenBg_id) != null;
        findViewById(R.id.landingScreenBg_id).setBackground(new BitmapDrawable(getResources(), blurredBitmap));

    }


    private void doLogin(final String authToken, String username)
    {

        UserAccountDetailResponse userAccountDetailResponse = new UserAccountDetailResponse();

        Log.d("Token = ", "Basic " + authToken);
        preachingAssistantService.getUserAccount("Basic " + authToken, username).enqueue(new Callback<UserAccountDetailResponse>()
        {
            @Override
            public void onResponse(Call<UserAccountDetailResponse> call, Response<UserAccountDetailResponse> response)
            {
                if (response.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Log.d("LoginActivity", "Login Response = " + response);
                    progressBar.setVisibility(View.INVISIBLE);
                    //ActivityManager.launchCaptureContact(LoginActivity.this,authToken);
                    ActivityManager.launchMyContactsActivity(LoginActivity.this, authToken);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Login failure", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    emailFieldError.setVisibility(View.VISIBLE);
                    passwordFieldError.setVisibility(View.VISIBLE);
                    usernameEntered.setText("");
                    passwordField.setText("");
                }

            }

            @Override
            public void onFailure(Call<UserAccountDetailResponse> call, Throwable t)
            {
                Toast.makeText(LoginActivity.this, "Login failure", Toast.LENGTH_SHORT).show();
                Log.e("LoginActivity", "Login Response = " + t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();

    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.privacy_policy:
                break;

            case R.id.passwordEye:
                //hide and show the text functionality
                if ((findViewById(R.id.password) != null ? ((EditText) findViewById(R.id.password)).getInputType() : 0) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                {
                    ((EditText) findViewById(R.id.password)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    (findViewById(R.id.passwordEye)).setAlpha((float) 1.0);
                }
                else
                {
                    ((EditText) findViewById(R.id.password)).setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    (findViewById(R.id.passwordEye)).setAlpha((float) 0.5);
                }
                break;

            case R.id.loginButton:

                if (HelperUtility.hasNetworkConnection(LoginActivity.this))
                {
                    progressBar.setVisibility(View.VISIBLE);
                    username = usernameEntered.getText().toString();
                    String password = passwordField.getText().toString();
                    String authToken = null;
                    try
                    {
                        authToken = Base64.encodeToString((username + ":" + password).getBytes("UTF-8"), Base64.NO_WRAP);
                    } catch (UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                    }
                    doLogin(authToken, username);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.forgetpassword:
                //ActivityManager.ForgetPasswordView(this);
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {

    }
}
