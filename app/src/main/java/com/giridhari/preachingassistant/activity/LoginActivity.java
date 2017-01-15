package com.giridhari.preachingassistant.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
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
import com.giridhari.preachingassistant.model.DevoteeDetailsResponse;
import com.giridhari.preachingassistant.model.UserAccountDetailResponse;
import com.giridhari.preachingassistant.utility.ActivityManager;
import com.giridhari.preachingassistant.utility.CustomTabActivityHelper;
import com.giridhari.preachingassistant.utility.HelperUtility;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends APIActivity implements View.OnClickListener,
        CheckBox.OnCheckedChangeListener
{
    public static final String AUTH_TOKEN = "authToken";
    public static final String DEVOTEE_URL = "devoteeUrl";
    private static final String USER_ACCOUNT_URL = "userAccountUrl";

    private ProgressBar progressBar;
    private String username;
    private TextView forgetBtn;
    private Button loginButton;
    private Button signUpButton;
    private TextView usernameEntered;
    private EditText passwordField;
    private TextView emailFieldError;
    private TextView passwordFieldError;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getStringFromSharedPreferences(AUTH_TOKEN) != null)
        {
            ActivityManager.launchMyContactsActivity(LoginActivity.this);
        }
        setContentView(R.layout.activity_login);

        assert findViewById(R.id.privacy_policy) != null;
        findViewById(R.id.privacy_policy).setOnClickListener(this);

        assert findViewById(R.id.passwordEye) != null;
        findViewById(R.id.passwordEye).setOnClickListener(this);
        assert findViewById(R.id.loginButton) != null;
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        loginButton.setAlpha(1.0f);

        signUpButton = (Button) findViewById(R.id.signupBtn);
        signUpButton.setOnClickListener(this);


        assert findViewById(R.id.rememberme) != null;
        ((CheckBox) findViewById(R.id.rememberme)).setOnCheckedChangeListener(this);

        forgetBtn = (TextView) findViewById(R.id.forgetpassword);
        forgetBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(LoginActivity.this, "Coming soon!!", Toast.LENGTH_SHORT).show();
            }
        });

        TextView privacyPolicy = (TextView) findViewById(R.id.privacy_policy);
        privacyPolicy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(LoginActivity.this, "A product of ISKCON AECS Layout, reach us at 8880076000", Toast.LENGTH_SHORT).show();
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        NetworkDialog.networkDialogListener networkDialogListener = new NetworkDialog.networkDialogListener()
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
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.splashscreen);
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
                    saveToSharedPreferences(AUTH_TOKEN, "Basic " + authToken);
                    saveToSharedPreferences(USER_ACCOUNT_URL, response.body().get_links().get("userAccount").get("href"));
                    preachingAssistantService.getDevoteeDetails(getStringFromSharedPreferences(AUTH_TOKEN), response.body().get_links().get("profile").get("href")).enqueue(
                            new Callback<DevoteeDetailsResponse>()
                            {
                                @Override
                                public void onResponse(Call<DevoteeDetailsResponse> call, Response<DevoteeDetailsResponse> response)
                                {
                                    if (response.isSuccessful())
                                    {
                                        saveToSharedPreferences(DEVOTEE_URL, response.body().get_links().get("self").get("href"));
                                        ActivityManager.launchMyContactsActivity(LoginActivity.this);
                                    }
                                    else
                                    {
//                                        Toast.makeText(this, "You don't have a profile yet. Please contact the admin.", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<DevoteeDetailsResponse> call, Throwable t)
                                {

                                }
                            }
                    );

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

            case R.id.signupBtn:
                launchSignUpCustomChromeTab();

                break;
        }

    }

    private void launchSignUpCustomChromeTab()
    {
        String url = getString(R.string.signup_link);

        // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

        // set toolbar color
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.Life_Brown));

        // add share action to menu list
        builder.addDefaultShareMenuItem();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_share);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, url);
        int requestCode = 100;
        PendingIntent pendingIntent = PendingIntent.getActivity(LoginActivity.this,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // Map the bitmap, text, and pending intent to this icon
        // Set tint to be true so it matches the toolbar color
        builder.setActionButton(bitmap, getString(R.string.share_link), pendingIntent, true);

        // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
        CustomTabsIntent customTabsIntent = builder.build();
        // and launch the desired Url with CustomTabsIntent.launchUrl()
        //customTabsIntent.launchUrl(this, Uri.parse(url));
        CustomTabActivityHelper.openCustomTab(this, customTabsIntent, Uri.parse(url),
                new CustomTabActivityHelper.CustomTabFallback()
                {
                    @Override
                    public void openUri(Activity activity, Uri uri)
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        activity.startActivity(intent);
                    }
                });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {

    }
}
