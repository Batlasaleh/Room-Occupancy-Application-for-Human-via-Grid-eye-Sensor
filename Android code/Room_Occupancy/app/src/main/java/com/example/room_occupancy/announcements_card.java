package com.example.room_occupancy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

public class announcements_card extends ArrayAdapter<AddAnnouncementsDB> {

    private Activity context;
    List<AddAnnouncementsDB> announcementsList;
    Button delete;

    announcements_card(@NonNull Activity context, List<AddAnnouncementsDB> announcementsList) {
        super(context, R.layout.activity_announcements_card,announcementsList);
        this.context = context;
        this.announcementsList = announcementsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.activity_announcements_card,null,true);

        TextView title=ListViewItem.findViewById(R.id.textView47);
        TextView type=ListViewItem.findViewById(R.id.textView49);
        TextView message=ListViewItem.findViewById(R.id.textView48);
        TextView datetxt=ListViewItem.findViewById(R.id.date);
        delete=ListViewItem.findViewById(R.id.button);

        Calendar cc = Calendar.getInstance();
        int year=cc.get(Calendar.YEAR);
        int month=cc.get(Calendar.MONTH);
        int mDay = cc.get(Calendar.DAY_OF_MONTH);
        datetxt.setText( year + ":" + month + ":" + mDay);

        final AddAnnouncementsDB announcements= announcementsList.get(position);
        title.setText("Title: "+announcements.getTitle());
        type.setText("Type: "+announcements.getAnnouncementType());
        message.setText(announcements.getMessage());
        final String id=announcements.getId();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(id);
            }
        });

        return ListViewItem;
    }

    private void delete(String id) {
        DatabaseReference dannoun= FirebaseDatabase.getInstance().getReference("announcements").child(id);
        dannoun.removeValue();
    }
}
