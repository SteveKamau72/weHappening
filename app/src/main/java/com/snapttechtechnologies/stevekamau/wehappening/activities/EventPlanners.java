package com.snapttechtechnologies.stevekamau.wehappening.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snapttechtechnologies.stevekamau.wehappening.R;
import com.snapttechtechnologies.stevekamau.wehappening.helper.DataBaseHandler;

/**
 * Created by steve on 9/9/15.
 */
public class EventPlanners extends Fragment {
    SharedPreferences firstRun;

    public EventPlanners() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.event_planners, container, false);
        (rootView.findViewById(R.id.r1)).setVisibility(View.GONE);
        firstRun = getActivity().getSharedPreferences("MyPrefs2", 0);

        if (firstRun.getBoolean("is_first", true)) {
            (rootView.findViewById(R.id.r1)).setVisibility(View.VISIBLE);
        }

        (rootView.findViewById(R.id.btnHide)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstRun.edit().putBoolean("is_first", false).commit();
                (rootView.findViewById(R.id.r1)).setVisibility(View.GONE);
            }
        });
        // Inflate the layout for this fragment
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
}

