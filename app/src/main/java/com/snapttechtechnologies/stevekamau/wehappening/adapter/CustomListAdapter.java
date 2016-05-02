package com.snapttechtechnologies.stevekamau.wehappening.adapter;

/**
 * Created by steve on 9/20/15.
 */

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.snapttechtechnologies.stevekamau.wehappening.R;
import com.snapttechtechnologies.stevekamau.wehappening.app.AppController;
import com.snapttechtechnologies.stevekamau.wehappening.model.Model;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CustomListAdapter extends BaseAdapter {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Activity activity;
    private LayoutInflater inflater;
    private List<Model> modelItems;

    public CustomListAdapter(Activity activity, List<Model> modelItems) {
        this.activity = activity;
        this.modelItems = modelItems;
    }

    @Override
    public int getCount() {
        return modelItems.size() - 1;
    }

    @Override
    public Object getItem(int location) {
        return modelItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position - 1;
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
        Model m = modelItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getTitle());
        where.setText(m.getWhere());
        time.setText(m.getTime());

        // genre
        String detailsStr = "";
        for (String str : m.getDetails()) {
            detailsStr += str + ", ";
        }
        detailsStr = detailsStr.length() > 0 ? detailsStr.substring(0,
                detailsStr.length() - 2) : detailsStr;
        details.setText(detailsStr);
        details.setVisibility(View.GONE);

        // release year
        date.setText(String.valueOf(m.getDate()));
        date.setVisibility(View.GONE);
        return convertView;
    }

}