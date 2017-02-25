package com.giridhari.preachingassistant.activity.adapters;

/**
 * Created by SESA249880 on 12/18/2016.
 */

public class ContactsViewModel
{
    private final String name;
    private final String phoneNo;
    private final String dateWhenUserWasAdded;
    private String profileUrl;


    public ContactsViewModel(String name, String phoneNo, String dateWhenUserWasAdded, String profileUrl)
    {
        this.name = name;
        this.phoneNo = phoneNo;
        this.dateWhenUserWasAdded = dateWhenUserWasAdded;
        this.profileUrl = profileUrl;
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

    public String getProfileUrl() {
        return profileUrl;
    }
}
