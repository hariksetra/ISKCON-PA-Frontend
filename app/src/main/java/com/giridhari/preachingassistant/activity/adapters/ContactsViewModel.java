package com.giridhari.preachingassistant.activity.adapters;

/**
 * Created by SESA249880 on 12/18/2016.
 */

public class ContactsViewModel
{
    public String name;
    public String phoneNo;
    public String dateWhenUserWasAdded;


    public ContactsViewModel(String name, String phoneNo, String dateWhenUserWasAdded)
    {
        this.name = name;
        this.phoneNo = phoneNo;
        this.dateWhenUserWasAdded = dateWhenUserWasAdded;
    }

}
