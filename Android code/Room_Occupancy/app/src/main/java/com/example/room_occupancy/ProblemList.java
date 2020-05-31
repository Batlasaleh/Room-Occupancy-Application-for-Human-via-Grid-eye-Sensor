package com.example.room_occupancy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;
import java.util.List;

public class ProblemList extends ArrayAdapter <sendproblemDB> {
    private Activity context;
    List <sendproblemDB> problemList;
    Button delete;

    public ProblemList (Activity context, List<sendproblemDB> problemList){
        super(context, R.layout.activity_alert,problemList);
        this.context=context;
        this.problemList=problemList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View ListViewItem = inflater.inflate(R.layout.activity_alert,null,true);

        TextView roomnum=ListViewItem.findViewById(R.id.textView47);
        TextView type=ListViewItem.findViewById(R.id.textView49);
        TextView message=ListViewItem.findViewById(R.id.textView48);
        TextView datetxt=ListViewItem.findViewById(R.id.date);
        delete=ListViewItem.findViewById(R.id.button);

        Calendar cc = Calendar.getInstance();
        int year=cc.get(Calendar.YEAR);
        int month=cc.get(Calendar.MONTH);
        int mDay = cc.get(Calendar.DAY_OF_MONTH);
        datetxt.setText( year + ":" + month + ":" + mDay);

        final sendproblemDB problem = problemList.get(position);

        roomnum.setText("Room number: "+problem.getRoomnum());
        type.setText("Type: "+problem.getType());
        message.setText("Problem: "+problem.getProblem());
        final String id=problem.getId();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(id);
            }
        });

        return ListViewItem;

    }

    private void delete(String id) {
        DatabaseReference dproblem=FirebaseDatabase.getInstance().getReference("problem").child(id);
        dproblem.removeValue();
    }


}
