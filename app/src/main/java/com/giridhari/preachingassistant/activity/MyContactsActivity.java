package com.giridhari.preachingassistant.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.activity.adapters.ContactsViewAdapter;
import com.giridhari.preachingassistant.activity.adapters.ContactsViewModel;
import com.giridhari.preachingassistant.activity.dialog.CaptureContactDialog;
import com.giridhari.preachingassistant.model.DevoteeDetailsResponse;
import com.giridhari.preachingassistant.model.DevoteeListResponse;
import com.giridhari.preachingassistant.utility.ActivityManager;

import java.util.ArrayList;
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
        contactsViewAdapter = new ContactsViewAdapter(this, contacts);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.contacts_view);
        listView.setFastScrollEnabled(true);

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
                            progressBar.setVisibility(View.INVISIBLE);
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
    public void refreshContactsList()
    {
        FetchLatestContacts();
    }
}
