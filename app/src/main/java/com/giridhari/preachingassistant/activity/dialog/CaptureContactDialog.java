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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.client.PreachingAssistantService;
import com.giridhari.preachingassistant.model.DevoteeCreateRequest;
import com.giridhari.preachingassistant.model.DevoteeDetailsResponse;
import com.giridhari.preachingassistant.utility.HelperUtility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SESA249880 on 12/18/2016.
 */

public class CaptureContactDialog extends Dialog
{

    private final Context mContext;
    private final String authToken;
    private final String capturedBy;
    private final PreachingAssistantService preachingAssistantService;
    private final boolean languageEntered = true;
    private EditText name;
    private EditText area;
    private EditText mobile;
    private EditText gender;
    private EditText language;
    private EditText feedbackEditTextBox;
    private ImageView captureContact;
    private CaptureContactDialogCallback captureContactDialogCallback;
    private ProgressBar progressBar;
    private boolean genderSelected = false;
    private boolean nameEntered = false;
    private boolean mobileEntered = false;
    private boolean areaEntered = false;
    private String genderChosen = "";


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
        name = (EditText) findViewById(R.id.legalNameEditText);
        mobile = (EditText) findViewById(R.id.mobileEditText);


        Spinner genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.gender_array, R.layout.custom_spinner_item);
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


        area = (EditText) findViewById(R.id.areaEditText);
        language = (EditText) findViewById(R.id.language);
        feedbackEditTextBox = (EditText) findViewById(R.id.feedbackEditTextBox);
        captureContact = (ImageView) findViewById(R.id.captureContact);
        progressBar = (ProgressBar) findViewById(R.id.progressBarInDialog);

        captureContact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (HelperUtility.hasNetworkConnection(mContext))
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
                                captureContactDialogCallback.refreshContactsList(mobile.getText().toString());
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
                else
                {
                    Toast.makeText(mContext, R.string.check_internet, Toast.LENGTH_SHORT).show();
                }
            }
        });


        setCancelable(true);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

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
                nameEntered = editable.length() > 0;
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
                mobileEntered = editable.length() == 10;
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
                areaEntered = editable.length() >= 4;
                enableDisableSaveButton();
            }
        });
    }

    private void enableDisableSaveButton()
    {
        if (nameEntered && mobileEntered && genderSelected && areaEntered && languageEntered)
        {
            captureContact.setEnabled(true);
            captureContact.setAlpha(1.0f);
        }
        else
        {
            captureContact.setEnabled(false);
            captureContact.setAlpha(0.5f);
        }
    }

    public CaptureContactDialogCallback getCaptureContactDialogCallback()
    {
        return captureContactDialogCallback;
    }

    public void setCaptureContactDialogCallback(CaptureContactDialogCallback captureContactDialogCallback)
    {
        this.captureContactDialogCallback = captureContactDialogCallback;
    }

    public interface CaptureContactDialogCallback
    {
        void refreshContactsList(String mobileNumberOfCapturedContact);
    }


}
