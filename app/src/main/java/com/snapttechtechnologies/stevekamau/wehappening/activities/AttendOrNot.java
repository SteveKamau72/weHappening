package com.snapttechtechnologies.stevekamau.wehappening.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.snapttechtechnologies.stevekamau.wehappening.R;
import com.snapttechtechnologies.stevekamau.wehappening.adapter.AttendingOrNotListAdapter;
import com.snapttechtechnologies.stevekamau.wehappening.helper.DataBaseHandler;
import com.snapttechtechnologies.stevekamau.wehappening.model.AttendingListItems;

import java.util.ArrayList;
import java.util.List;

public class AttendOrNot extends AppCompatActivity {
    ArrayList<AttendingListItems> imageArry = new ArrayList<AttendingListItems>();
    AttendingOrNotListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_or_not);
        DataBaseHandler db = new DataBaseHandler(this);
// display main List view bcard and contact name

// Reading all attendingListItemses from database
        List<AttendingListItems> attendingListItemses = db.getSingleContacts();
        for (AttendingListItems cn : attendingListItemses) {
            String log = "ID:" + cn.getID() + " Name: " + cn.getName()
                    + " ,Image: " + cn.getImage();

// Writing Contacts to log
            Log.d("Result: ", log);
//add attendingListItemses data in arrayList
            imageArry.add(cn);

        }
        adapter = new AttendingOrNotListAdapter(this, R.layout.attend_or_not_list,
                imageArry);
        ListView dataList = (ListView) findViewById(R.id.list);
        dataList.setAdapter(adapter);

        (findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        (findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attend_or_not, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
