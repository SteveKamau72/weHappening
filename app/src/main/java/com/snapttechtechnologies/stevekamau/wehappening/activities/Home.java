package com.snapttechtechnologies.stevekamau.wehappening.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.snapttechtechnologies.stevekamau.wehappening.R;
import com.snapttechtechnologies.stevekamau.wehappening.adapter.CustomListAdapter;
import com.snapttechtechnologies.stevekamau.wehappening.app.AppController;
import com.snapttechtechnologies.stevekamau.wehappening.model.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 9/9/15.
 */
public class Home extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    // Movies json url
    private static final String url = "http://snapt.t15.org/wehappening/events.js";
    private static String Title = "title";
    private static String Time = "time";
    private static String Details = "details";
    private static String Where = "where";
    private static String Date = "date";
    private static String bitmap = "thumbnailUrl";
    TextView txt1;
    Bundle savedInstanceState;
    CircularProgressView progressView;
    private ProgressDialog pDialog;
    private List<Model> modelList = new ArrayList<Model>();
    private ListView listView;
    private CustomListAdapter adapter;


    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home, container, false);

        View header = getLayoutInflater(savedInstanceState).inflate(R.layout.header, null);

        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new CustomListAdapter(getActivity(), modelList);
        listView.addHeaderView(header);
        listView.setAdapter(adapter);
        progressView = (CircularProgressView) rootView.findViewById(R.id.progress_view);
        progressView.startAnimation();
        listView.setVisibility(View.GONE);
        requesting();
        setHasOptionsMenu(true);
        txt1 = (TextView) header.findViewById(R.id.txt1);
        String fontPath = "fonts/abeatbyKaiRegular.ttf";
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        txt1.setTypeface(tf);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = ((TextView) view.findViewById(R.id.title))
                        .getText().toString();
                bitmap = ((Model) modelList.get(position - 1)).getThumbnailUrl();
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
                                Model model = new Model();
                                model.setTitle(obj.getString("title"));
                                model.setThumbnailUrl(obj.getString("image"));
                                model.setTime(obj.getString("time"));
                                model.setDate(obj.getInt("date"));
                                model.setWhere(obj.getString("where"));
                                // Genre is json array
                                JSONArray genreArry = obj.getJSONArray("details");
                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    genre.add((String) genreArry.get(j));
                                }
                                model.setDetails(genre);

                                // adding model to movies array
                                modelList.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        //
                        //adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                       // showAlertDialog();
                    progressView.setVisibility(View.GONE);
                    Context context = getContext();
                } else if (error instanceof AuthFailureError) {

                        serverDialog();

                    progressView.setVisibility(View.GONE);
                    Context context = getContext();
                } else if (error instanceof ServerError) {

                        serverDialog();

                    progressView.setVisibility(View.GONE);
                    Context context = getContext();
                } else if (error instanceof NetworkError) {

                       // showAlertDialog();

                    progressView.setVisibility(View.GONE);
                    Context context = getContext();
                } else if (error instanceof ParseError) {

                        serverDialog();

                    progressView.setVisibility(View.GONE);
                    Context context = getContext();
                }

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
        if (id == R.id.action_location) {
            Intent intent = new Intent(getContext(), LocationActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_night_mode) {
            Intent intent = new Intent(getContext(), NightModeActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setCancelable(false);

        // Setting Dialog Message
        alertDialog.setMessage("Network error! Check your connection and retry.");

        // Setting Positive "Retry" Button
        alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Setting Negative "Quit" Button
        alertDialog.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void serverDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setCancelable(true);

        // Setting Dialog Message
        alertDialog.setMessage("There was a problem completing your request. Please try again later.");

        // Setting Positive "Retry" Button
        alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Setting Negative "Quit" Button
        alertDialog.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
