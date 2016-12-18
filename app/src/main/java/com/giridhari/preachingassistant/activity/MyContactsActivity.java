package com.giridhari.preachingassistant.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.activity.adapters.ContactsViewAdapter;
import com.giridhari.preachingassistant.activity.adapters.ContactsViewModel;

import java.util.ArrayList;

public class MyContactsActivity extends AppCompatActivity
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
    }
}
