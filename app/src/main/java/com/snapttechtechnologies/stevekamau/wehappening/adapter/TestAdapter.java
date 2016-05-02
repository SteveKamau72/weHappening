package com.snapttechtechnologies.stevekamau.wehappening.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.snapttechtechnologies.stevekamau.wehappening.R;
import com.snapttechtechnologies.stevekamau.wehappening.app.AppController;
import com.snapttechtechnologies.stevekamau.wehappening.model.Model;
import com.snapttechtechnologies.stevekamau.wehappening.model.ModelTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by steve on 10/21/15.
 */
public class TestAdapter extends BaseAdapter  {
    List<ModelTest> mStringFilterList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelTest> modelItems;
    private int mDefaultImageId;

    public TestAdapter(Activity activity, List<ModelTest> modelItems) {
        this.activity = activity;
        this.modelItems = modelItems;
        mStringFilterList = modelItems;
    }


    public void setDefaultImageResId(int defaultImage) {
        mDefaultImageId = defaultImage;
    }

    @Override
    public int getCount() {
        return modelItems.size();
    }

    @Override
    public Object getItem(int location) {
        return modelItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView details = (TextView) convertView.findViewById(R.id.details);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView where = (TextView) convertView.findViewById(R.id.where);
        // getting movie data for the row
        ModelTest m = modelItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getTitle());
        where.setText(m.getWhere());


        time.setText(m.getTime());


        details.setText(m.getDetails());

        // release year
        date.setText(String.valueOf(m.getDate()));
        return convertView;
    }



}


