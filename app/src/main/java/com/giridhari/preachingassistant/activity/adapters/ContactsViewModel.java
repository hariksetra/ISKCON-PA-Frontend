package com.giridhari.preachingassistant.activity.adapters;

/**
 * Created by SESA249880 on 12/18/2016.
 */

public class ContactsViewModel
{
    public final String name;
    public final String phoneNo;
    public final String dateWhenUserWasAdded;


    public ContactsViewModel(String name, String phoneNo, String dateWhenUserWasAdded)
    {
        this.name = name;
        this.phoneNo = phoneNo;
        this.dateWhenUserWasAdded = dateWhenUserWasAdded;
    }
    public String getPhoneNo()
    {
        return phoneNo;
    }

    public String getName()
    {
        return name;
    }

    public String getDateWhenUserWasAdded()
    {
        return dateWhenUserWasAdded;
    }

}
