package com.giridhari.preachingassistant.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.activity.adapters.ContactsViewAdapter;
import com.giridhari.preachingassistant.activity.adapters.ContactsViewModel;
import com.giridhari.preachingassistant.activity.dialog.CaptureContactDialog;
import com.giridhari.preachingassistant.model.DevoteeDetailsResponse;
import com.giridhari.preachingassistant.model.DevoteeListResponse;
import com.giridhari.preachingassistant.utility.ActivityManager;
import com.giridhari.preachingassistant.utility.HelperUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyContactsActivity extends APIActivity implements CaptureContactDialog.CaptureContactDialogCallback
{

    private ArrayList<ContactsViewModel> contacts;
    private ContactsViewAdapter contactsViewAdapter;
    private ListView listView;
    private ProgressBar progressBar;
    private int screenHeight;
    private Intent callIntent;
    private boolean showRationale = false;
    private final int callRequestCode = 1;
    private final int smsRequestCode = 2;
    private String latestCapturedContactNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contacts = new ArrayList<>();
        progressBar = (ProgressBar) findViewById(R.id.progressBarCaptureContact);

        // Create the adapter to convert the array to views
        sortContactsByDate();
        contactsViewAdapter = new ContactsViewAdapter(this, contacts);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.contacts_view);

        adjustListViewHeight();
        listView.setFastScrollEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //now call this number
                /*callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + selectedContactNumber));
                if (ActivityCompat.checkSelfPermission(MyContactsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MyContactsActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            callRequestCode);
                }
                else
                {
                    MyContactsActivity.this.startActivity(callIntent);
                }*/

                ActivityManager.launchDevoteeDetailViewActivity(MyContactsActivity.this, contacts.get(position).getProfileUrl());
            }
        });

        ImageView captureContact = (ImageView) findViewById(R.id.add_contact);
        captureContact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showCaptureContactDialog();
            }
        });

        ImageView selectDate = (ImageView) findViewById(R.id.date);
        selectDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(MyContactsActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void adjustListViewHeight()
    {
        screenHeight = HelperUtility.getScreenHeight();
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        float density = getResources().getDisplayMetrics().density;
        double reductionConstant = 0.65;
        if (density == 0.75f)
        {
            // LDPI
            reductionConstant = 0.6;
        }
        else if (density >= 1.0f && density < 1.5f)
        {
            // MDPI
            reductionConstant = 0.65;
        }
        else if (density == 1.5f)
        {
            // HDPI
        }
        else if (density > 1.5f && density <= 2.0f)
        {
            // XHDPI
            reductionConstant = 0.65;
        }
        else if (density > 2.0f && density <= 3.0f)
        {
            // XXHDPI
            reductionConstant = 0.7;
        }
        else
        {
            // XXXHDPI
            reductionConstant = 0.7;
        }
        params.height = (int) (reductionConstant * screenHeight);
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void getDevoteeDetails()
    {
        progressBar.setVisibility(View.VISIBLE);
        preachingAssistantService.getDevoteeDetails(
                getStringFromSharedPreferences(LoginActivity.AUTH_TOKEN),
                getStringFromSharedPreferences(LoginActivity.DEVOTEE_URL))
                .enqueue(new Callback<DevoteeDetailsResponse>()
                {
                    @Override
                    public void onResponse(Call<DevoteeDetailsResponse> call, Response<DevoteeDetailsResponse> response)
                    {
                        if (response.isSuccessful())
                        {
                            Map<String, Map<String, String>> links = response.body().get_links();
                            if (links != null && links.containsKey("capturedDevotees"))
                            {
                                getCapturedContacts(links.get("capturedDevotees").get("href"));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DevoteeDetailsResponse> call, Throwable t)
                    {

                    }
                });
    }

    private void getCapturedContacts(String url)
    {
        preachingAssistantService.getCapturedDevotees(getStringFromSharedPreferences(LoginActivity.AUTH_TOKEN), url)
                .enqueue(new Callback<DevoteeListResponse>()
                {
                    @Override
                    public void onResponse(Call<DevoteeListResponse> call, Response<DevoteeListResponse> response)
                    {
                        if (response.isSuccessful())
                        {
                            MyContactsActivity.this.contacts.clear();
                            List<DevoteeDetailsResponse> capturedDevotees = response.body().get_embedded().get("devotees");
                            for (DevoteeDetailsResponse devotee : capturedDevotees)
                            {
                                MyContactsActivity.this.contacts.add(devotee.toContactsViewModel());
                                Log.d("MyContactsActivity", "test");
                            }

                            sortContactsByDate();
                            progressBar.setVisibility(View.INVISIBLE);
                            sortContactsByDate();
                            contactsViewAdapter = new ContactsViewAdapter(MyContactsActivity.this, contacts);
                            listView.setAdapter(contactsViewAdapter);
                            contactsViewAdapter.notifyDataSetInvalidated();
                        }
                    }

                    @Override
                    public void onFailure(Call<DevoteeListResponse> call, Throwable t)
                    {

                    }
                });
    }

    private void sortContactsByDate()
    {
        if (contacts.size() >= 2)
        {
            Collections.sort(contacts, new CustomComparator());
        }
    }

    private void showCaptureContactDialog()
    {
        CaptureContactDialog captureContactDialog = new CaptureContactDialog(MyContactsActivity.this, preachingAssistantService,
                getStringFromSharedPreferences(LoginActivity.AUTH_TOKEN), getStringFromSharedPreferences(LoginActivity.DEVOTEE_URL));
        captureContactDialog.setCaptureContactDialogCallback(MyContactsActivity.this);
        captureContactDialog.show();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        FetchLatestContacts();
    }

    private void FetchLatestContacts()
    {
        if (listView != null)
        {
            listView.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    getDevoteeDetails();
                }
            }, 100);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout)
        {
            finish();
            ActivityManager.logout(MyContactsActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void refreshContactsList(String mobileNumberOfCapturedContact)
    {
        FetchLatestContacts();
        this.latestCapturedContactNumber = mobileNumberOfCapturedContact;
        if (ActivityCompat.checkSelfPermission(MyContactsActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MyContactsActivity.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    smsRequestCode);
        }
        else
        {
            SendSMSToCapturedContact(this.latestCapturedContactNumber);
        }
    }

    private void SendSMSToCapturedContact(String mobileNumberOfCapturedContact)
    {
        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mobileNumberOfCapturedContact, null, getString(R.string.welcome_message), null, null);

        } catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == callRequestCode)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Log.d("PreachingAssistant", "Permission: " + permissions[0] + "was " + grantResults[0]);
                if (callIntent != null)
                {
                    MyContactsActivity.this.startActivity(callIntent);
                }
            }
            else
            {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                {
                    // user rejected the permission
                    String permission = permissions[0];
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
                    {
                        showRationale = shouldShowRequestPermissionRationale(permission);
                    }
                    if (!showRationale)
                    {

                    }
                    else
                    {
                        Toast.makeText(MyContactsActivity.this, R.string.give_calling_permission, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        else if (requestCode == smsRequestCode)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Log.d("PreachingAssistant", "Permission: " + permissions[0] + "was " + grantResults[0]);
                SendSMSToCapturedContact(this.latestCapturedContactNumber);
            }
            else
            {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                {
                    // user rejected the permission
                    String permission = permissions[0];
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
                    {
                        showRationale = shouldShowRequestPermissionRationale(permission);
                    }
                    if (!showRationale)
                    {

                    }
                    else
                    {
                        Toast.makeText(MyContactsActivity.this, R.string.give_sms_permission, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    //sort by date added then by name.
    private class CustomComparator implements Comparator<ContactsViewModel>
    {

        @Override
        public int compare(ContactsViewModel o1, ContactsViewModel o2)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            try
            {
                int dateComparison = dateFormat.parse(o1.getDateWhenUserWasAdded()).compareTo(dateFormat.parse(o2.getDateWhenUserWasAdded()));
                if (dateComparison == 0)
                {
                    return o1.getName().compareTo(o2.getName());
                }
                else
                {
                    return dateComparison;
                }
            } catch (ParseException e)
            {
                e.printStackTrace();
                return -1; //by default
            }
        }
    }

}
