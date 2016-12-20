package com.giridhari.preachingassistant.activity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.client.PreachingAssistantService;
import com.giridhari.preachingassistant.model.DevoteeCreateRequest;
import com.giridhari.preachingassistant.model.DevoteeDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SESA249880 on 12/18/2016.
 */

public class CaptureContactDialog extends Dialog
{

    private final Context mContext;
    EditText name, area, mobile, gender, language, feedbackEditTextBox;
    Button captureContact, fetchContact;
    private CaptureContactDialogCallback captureContactDialogCallback;
    String authToken;
    String capturedBy;
    ProgressBar progressBar;
    PreachingAssistantService preachingAssistantService;
    boolean genderSelected = false;
    boolean nameEntered = false;
    boolean mobileEntered = false;
    boolean areaEntered = false;
    boolean languageEntered = true;
    String genderChosen = "";


    public CaptureContactDialog(Context context, PreachingAssistantService preachingAssistantService, String authToken, String devotee)
    {
        super(context);
        mContext = context;
        this.preachingAssistantService = preachingAssistantService;
        this.authToken = authToken;
        this.capturedBy = devotee;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.capture_contact_dialog_layout);
        TextView saveButton = (TextView) findViewById(R.id.captureContact);

        name = (EditText) findViewById(R.id.userName);
        mobile = (EditText) findViewById(R.id.mobileNumber);


        Spinner genderSpinner = (Spinner) findViewById(R.id.genderValue);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        genderSpinner.setAdapter(adapter);
        genderSpinner.setSelection(0);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                genderSelected = true;
                genderChosen = adapterView.getItemAtPosition(i).toString();
                enableDisableSaveButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                genderSelected = false;
                enableDisableSaveButton();
            }
        });


        area = (EditText) findViewById(R.id.area);
        language = (EditText) findViewById(R.id.language);
        feedbackEditTextBox = (EditText) findViewById(R.id.feedbackEditTextBox);
        captureContact = (Button) findViewById(R.id.captureContact);
        progressBar = (ProgressBar) findViewById(R.id.progressBarInDialog);

        captureContact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DevoteeCreateRequest devoteeCreateRequest = new DevoteeCreateRequest();
                devoteeCreateRequest.setLegalName(name.getText().toString());
                devoteeCreateRequest.setArea(area.getText().toString());
                devoteeCreateRequest.setGender(genderChosen);
                devoteeCreateRequest.setSmsPhone(mobile.getText().toString());
                devoteeCreateRequest.setCapturedBy(capturedBy);
                devoteeCreateRequest.setPreferredLanguage(language.getText().toString());
                devoteeCreateRequest.setDescription(feedbackEditTextBox.getText().toString());

                progressBar.setVisibility(View.VISIBLE);

                Log.d("Token = ", authToken);
                preachingAssistantService.createDevotee(authToken, "application/json", "application/json", devoteeCreateRequest).enqueue(new Callback<DevoteeDetailsResponse>()
                {
                    @Override
                    public void onResponse(Call<DevoteeDetailsResponse> call, Response<DevoteeDetailsResponse> response)
                    {
                        if (response.isSuccessful())
                        {
                            Log.d("response", response.message());
                            Toast.makeText(mContext, "Capture Contact successful", Toast.LENGTH_SHORT).show();
                            Log.d("CaptureContactActivity", "Capture Contact Response = " + response);
                            dismiss();
                        }
                        else
                        {
                            Toast.makeText(mContext, "Capture Contact Failed, please retry!!", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<DevoteeDetailsResponse> call, Throwable t)
                    {
                        t.printStackTrace();
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(mContext, "Capture Contact Failed, please retry!!", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        area.setText("");
                        mobile.setText("");
                        gender.setText("");
                    }
                });
            }
        });


        setCancelable(true);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        manageTextWatchers();
    }

    private void manageTextWatchers()
    {
        name.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editable.length() > 0)
                {
                    nameEntered = true;
                }
                else
                {
                    nameEntered = false;
                }
                enableDisableSaveButton();
            }
        });

        mobile.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editable.length() == 10)
                {
                    mobileEntered = true;
                }
                else
                {
                    mobileEntered = false;
                }
                enableDisableSaveButton();
            }
        });

        area.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editable.length() >= 4)
                {
                    areaEntered = true;
                }
                else
                {
                    areaEntered = false;
                }
                enableDisableSaveButton();
            }
        });
    }

    public interface CaptureContactDialogCallback
    {
        void setDialogDisplayStatus(boolean isShowing);
    }

    public void enableDisableSaveButton()
    {
        if (nameEntered && mobileEntered && genderSelected && areaEntered && languageEntered)
        {
            captureContact.setEnabled(true);
        }
        else
        {
            captureContact.setEnabled(false);
        }
    }


}
