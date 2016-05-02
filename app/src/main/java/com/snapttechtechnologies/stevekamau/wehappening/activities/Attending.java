package com.snapttechtechnologies.stevekamau.wehappening.activities;

/**
 * Created by steve on 10/25/15.
 */

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.snapttechtechnologies.stevekamau.wehappening.R;
import com.snapttechtechnologies.stevekamau.wehappening.adapter.AttendingListAdapter;
import com.snapttechtechnologies.stevekamau.wehappening.helper.DataBaseHandler;
import com.snapttechtechnologies.stevekamau.wehappening.model.AttendingListItems;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Attending extends Fragment {
    ArrayList<AttendingListItems> imageArry = new ArrayList<AttendingListItems>();
    AttendingListAdapter adapter;
    DataBaseHandler db;

    /**
     * Called when the activity is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.events_attending, container, false);

        db = new DataBaseHandler(getContext());
// display main List view bcard and contact name
        if (db.checkForTables()) {
            List<AttendingListItems> attendingListItemses = db.getAllContacts();
            for (AttendingListItems cn : attendingListItemses) {
                String log = "ID:" + cn.getID() + " Name: " + cn.getName()
                        + " ,Image: " + cn.getImage();

// Writing Contacts to log
                Log.d("Result: ", log);
//add attendingListItemses data in arrayList
                imageArry.add(cn);

            }
            adapter = new AttendingListAdapter(getContext(), R.layout.screen_list,
                    imageArry);
            ListView dataList = (ListView) rootView.findViewById(R.id.list);
            dataList.setAdapter(adapter);

        } else {

            NoAttendList newFragment = new NoAttendList();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.container_body, newFragment);
            // Commit the transaction
            transaction.commit();

        }
// Reading all attendingListItemses from database
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_attending, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all) {
            deleteAllDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllDialog() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Delete all?")
                .setContentText("Won't be able to recover this list")
                .setCancelText("No, cancel")
                .setConfirmText("Yes, delete")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        sDialog.setTitleText("Cancelled!")
                                .setContentText("Your list is safe :)")
                                .setConfirmText("Okay")
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.setTitleText("Deleted!")
                                .setContentText("Your list has been deleted!")
                                .setConfirmText("Okay")
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                        db.deleteAll();
                        refreshFragment();
                    }

                })
                .show();

    }

    private void refreshFragment() {
        Attending renewFragment = new Attending();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container_body, renewFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}



