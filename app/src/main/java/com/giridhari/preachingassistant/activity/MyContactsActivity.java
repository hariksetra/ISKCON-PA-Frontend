package com.giridhari.preachingassistant.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.activity.adapters.ContactsViewAdapter;
import com.giridhari.preachingassistant.activity.adapters.ContactsViewModel;
import com.giridhari.preachingassistant.activity.dialog.CaptureContactDialog;
import com.giridhari.preachingassistant.utility.ActivityManager;

import java.util.ArrayList;

public class MyContactsActivity extends APIActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Construct the data source
        ArrayList<ContactsViewModel> arrayOfUsers = new ArrayList<ContactsViewModel>();

        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+917348807791", "18/12/16"));


        // Create the adapter to convert the array to views
        ContactsViewAdapter contactsViewAdapter = new ContactsViewAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.contacts_view);
        listView.setAdapter(contactsViewAdapter);

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

    private void showCaptureContactDialog()
    {
        CaptureContactDialog captureContactDialog = new CaptureContactDialog(MyContactsActivity.this);
        captureContactDialog.show();
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
            //TODO : clear shared prefs
            finish();
            ActivityManager.launchLogin(MyContactsActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
