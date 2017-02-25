package com.giridhari.preachingassistant.activity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.giridhari.preachingassistant.R;

import java.util.ArrayList;

/**
 * Created by SESA249880 on 12/18/2016.
 */


public class ContactsViewAdapter extends ArrayAdapter<ContactsViewModel>
{
    public ContactsViewAdapter(Context context, ArrayList<ContactsViewModel> users)
    {
        super(context, R.layout.contacts_view_adapter_layout, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        ContactsViewModel user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null)
        {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.contacts_view_adapter_layout, parent, false);

            viewHolder.name = (TextView) convertView.findViewById(R.id.contact);
            viewHolder.number = (TextView) convertView.findViewById(R.id.mobileEditText);
            viewHolder.date = (TextView) convertView.findViewById(R.id.dateWhenUserWasAdded);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }
        else
        {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name.setText(user != null ? user.getName(): null);
        viewHolder.number.setText(user != null ? user.getPhoneNo() : null);
        viewHolder.date.setText(user != null ? user.getDateWhenUserWasAdded() : null);
        // Return the completed view to render on screen

        return convertView;
    }

    // View lookup cache
    private static class ViewHolder
    {
        TextView name;
        TextView number;
        TextView date;
    }
}

