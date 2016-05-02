package com.snapttechtechnologies.stevekamau.wehappening.activities;

/**
 * Created by steve on 9/8/15.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.snapttechtechnologies.stevekamau.wehappening.R;
import com.snapttechtechnologies.stevekamau.wehappening.adapter.TestAdapter;
import com.snapttechtechnologies.stevekamau.wehappening.adapter.UpcomingAdapter;
import com.snapttechtechnologies.stevekamau.wehappening.app.AppController;
import com.snapttechtechnologies.stevekamau.wehappening.model.Model;
import com.snapttechtechnologies.stevekamau.wehappening.model.ModelTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class UpcomingEvents extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    // Movies json url
    private static final String url = "http://10.0.3.2/wehappening/events2.js";
    private static String Title = "title";
    private static String Time = "time";
    private static String Details = "details";
    private static String Where = "where";
    private static String Date = "date";
    private static String bitmap = "thumbnailUrl";
    TextView txt1;
    SearchView search_view;
    Bundle savedInstanceState;
    CircularProgressView progressView;
    private ProgressDialog pDialog;
    private List<ModelTest> modelList = new ArrayList<ModelTest>();
    private ListView listView;
    private TestAdapter adapter;


    public UpcomingEvents() {
        // Required empty public constructor
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.upcoming_events, container, false);

        View header = getLayoutInflater(savedInstanceState).inflate(R.layout.header, null);

        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new TestAdapter(getActivity(), modelList);
        listView.setAdapter(adapter);
        progressView = (CircularProgressView) rootView.findViewById(R.id.progress_view);
        progressView.startAnimation();
        listView.setVisibility(View.GONE);
        requesting();

        Button btn1 = (Button) rootView.findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        txt1 = (TextView) header.findViewById(R.id.txt1);
        String fontPath = "fonts/abeatbyKaiRegular.ttf";
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        txt1.setTypeface(tf);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = ((TextView) view.findViewById(R.id.title))
                        .getText().toString();
                bitmap = ((ModelTest) modelList.get(position - 1)).getThumbnailUrl();
                String time = ((TextView) view.findViewById(R.id.time))
                        .getText().toString();
                String date = ((TextView) view.findViewById(R.id.date))
                        .getText().toString();
                String details = ((TextView) view.findViewById(R.id.details))
                        .getText().toString();
                String where = ((TextView) view.findViewById(R.id.where))
                        .getText().toString();


                Intent intent = new Intent(getActivity(), Detail.class);
                intent.putExtra(Title, name);
                intent.putExtra("images", bitmap);
                intent.putExtra(Time, time);
                intent.putExtra(Details, details);
                intent.putExtra(Date, date);
                intent.putExtra(Where, where);
                startActivity(intent);
            }
        });
        return rootView;
    }

    private void requesting() {
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        listView.setVisibility(View.VISIBLE);
                        progressView.setVisibility(View.GONE);

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                ModelTest model = new ModelTest();
                                model.setTitle(obj.getString("title"));
                                model.setThumbnailUrl(obj.getString("image"));
                                model.setTime(obj.getString("time"));
                                model.setDate(obj.getInt("date"));
                                model.setWhere(obj.getString("where"));
                                model.setDetails(obj.getString("details"));

                                // adding model to movies array
                                modelList.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                progressView.setVisibility(View.GONE);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
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
