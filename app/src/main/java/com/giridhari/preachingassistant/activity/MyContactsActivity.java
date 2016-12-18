package com.giridhari.preachingassistant.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.activity.adapters.ContactsViewAdapter;
import com.giridhari.preachingassistant.activity.adapters.ContactsViewModel;
import com.giridhari.preachingassistant.activity.dialog.CaptureContactDialog;

import java.util.ArrayList;

public class MyContactsActivity extends APIActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contacts);

        // Construct the data source
        ArrayList<ContactsViewModel> arrayOfUsers = new ArrayList<ContactsViewModel>();

        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));
        arrayOfUsers.add(new ContactsViewModel("Ajit", "+91-7348807791", "18/12/16"));


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
        CaptureContactDialog captureContactDialog = new CaptureContactDialog(MyContactsActivity.this, preachingAssistantService,
                getStringFromSharedPreferences(LoginActivity.AUTH_TOKEN), getStringFromSharedPreferences(LoginActivity.DEVOTEE_URL));
        captureContactDialog.show();
    }
}
