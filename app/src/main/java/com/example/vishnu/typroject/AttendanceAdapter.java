package com.example.vishnu.typroject;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class AttendanceAdapter extends ArrayAdapter<String> {

    AttendanceAdapter(@NonNull Context context, String[] names) {
        super(context, R.layout.attendance_list_view, names);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View view = li.inflate(R.layout.attendance_list_view, parent, false);
        String singleName = getItem(position);
        final TextView names = view.findViewById(R.id.namess);
        final TextView colors = view.findViewById(R.id.colors);

        names.setText(singleName);
        colors.setText("P");

        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gets = colors.getText().toString();

                switch (gets) {

                    case "P":
                        colors.setText("A");
                        colors.setTextColor(Color.WHITE);
                        colors.setBackgroundColor(Color.rgb(255, 0, 0));
                        break;

                    case "A":
                        colors.setText("L");
                        colors.setTextColor(Color.WHITE);
                        colors.setBackgroundColor(Color.rgb(0, 0, 255));
                        break;

                    default:
                        colors.setText("P");
                        colors.setTextColor(Color.BLACK);
                        colors.setBackgroundColor(Color.rgb(152, 251, 152));
                        break;
                }
//                if (colors.getText().toString().equals("P")) {
//                    colors.setText("A");
//                    colors.setTextColor(Color.WHITE);
//                    colors.setBackgroundColor(Color.rgb(255, 0, 0));
//                } else if (colors.getText().toString().equals("A")) {
//                    colors.setText("L");
//                    colors.setTextColor(Color.WHITE);
//                    colors.setBackgroundColor(Color.rgb(0, 0, 255));
//                } else {
//                    colors.setText("P");
//                    colors.setTextColor(Color.BLACK);
//                    colors.setBackgroundColor(Color.rgb(152, 251, 152));
//                }
            }
        });

        return view;
    }
}
