package com.ucb.dcm.list;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;
import com.ucb.dcm.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kurtguenther on 6/20/13.
 */
public class FavoritesListAdapter extends CursorAdapter implements StickyListHeadersAdapter {

    private Cursor cursor;
    private Activity activity;

    public FavoritesListAdapter(Cursor cursor, Activity activity){
        super(activity, cursor);
        this.cursor = cursor;
        this.activity = activity;
    }

    private class ScheduleHolder{
        TextView time;
        TextView name;
        TextView venue;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View retVal = activity.getLayoutInflater().inflate(R.layout.list_schedule, parent, false);

        ScheduleHolder sh = new ScheduleHolder();
        sh.time = (TextView) retVal.findViewById(R.id.schedule_time);
        sh.name = (TextView) retVal.findViewById(R.id.schedule_show_name);
        sh.venue = (TextView) retVal.findViewById(R.id.schedule_venue);
        retVal.setTag(sh);

        bindView(retVal, context, cursor);

        return retVal;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int start_date = cursor.getInt(cursor.getColumnIndex("start_date"));
        String name = cursor.getString(cursor.getColumnIndex("name"));

        ScheduleHolder sh = (ScheduleHolder) view.getTag();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        sh.time.setText(sdf.format(new Date(((long)start_date) * 1000)));
        sh.name.setText(name);

        sh.venue.setText(cursor.getString(cursor.getColumnIndex("short_name")));
        sh.venue.setVisibility(View.VISIBLE);
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.list_show_header, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.header_title);

        Cursor c = (Cursor) getItem(position);

        int start_date = cursor.getInt(cursor.getColumnIndex("start_date"));
        Date date = new Date(((long)start_date) * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String headerTitle = sdf.format(date);

        title.setText(headerTitle);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        Cursor c = (Cursor) getItem(position);
        int start_date = cursor.getInt(cursor.getColumnIndex("start_date"));
        Date date = new Date(((long)start_date) * 1000);
        return date.getDay();
    }
}
