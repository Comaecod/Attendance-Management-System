package com.example.vishnu.typroject;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AllUsersAdapter extends ArrayAdapter<UserAllInstantiation> {

    private Activity context;
    private List<UserAllInstantiation> userList;

    public AllUsersAdapter(Activity context,List<UserAllInstantiation> userList) {
        super(context,R.layout.alldetails_cardview_custom,userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.alldetails_cardview_custom,null,true);

        TextView name = listViewItem.findViewById(R.id.text_view_name);
        TextView email = listViewItem.findViewById(R.id.text_view_email);
        TextView phone = listViewItem.findViewById(R.id.text_view_phone);
        TextView role = listViewItem.findViewById(R.id.text_view_role);
        ImageView ams = listViewItem.findViewById(R.id.image_view_upload_allusers);
        TextView course = listViewItem.findViewById(R.id.text_view_course);


        UserAllInstantiation user = userList.get(position);

        name.setText(user.getNameUser());
        email.setText(user.getUsernameUser());
        phone.setText(user.getPhoneUser());
        ams.setImageResource(R.drawable.ams_icon);
        role.setText(user.getRole());
        course.setText(user.getCourse());
        if(user.getRole().equals("HOD") || user.getRole().equals("Faculty")) {
            //phone.setVisibility(View.GONE);
            course.setVisibility(View.GONE);
            role.setTextColor(Color.rgb(232,141,103));
            role.setTextSize(20);
        }
        else {
            //phone.setVisibility(View.VISIBLE);
            course.setVisibility(View.VISIBLE);
        }

        return listViewItem;

    }
}
