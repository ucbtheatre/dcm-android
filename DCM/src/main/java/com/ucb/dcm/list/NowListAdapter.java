package com.ucb.dcm.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;
import com.ucb.dcm.R;
import com.ucb.dcm.data.NowPerformance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by kurtguenther on 6/16/13.
 */
public class NowListAdapter extends ArrayAdapter<NowPerformance> implements StickyListHeadersAdapter {

    public NowListAdapter(Context context, int resource, int textViewResourceId, List<NowPerformance> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    private class ScheduleHolder{
        TextView time;
        TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater lf = LayoutInflater.from(getContext());
            convertView = lf.inflate(R.layout.list_schedule, parent, false);
            ScheduleHolder holder = new ScheduleHolder();
            holder.name = (TextView) convertView.findViewById(R.id.schedule_show_name);
            holder.time = (TextView) convertView.findViewById(R.id.schedule_time);
            convertView.setTag(holder);
        }
        NowPerformance np = getItem(position);

        ScheduleHolder sh = (ScheduleHolder) convertView.getTag();
        sh.name.setText(np.show_name);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        sh.time.setText(sdf.format(new Date(((long)np.start_date) * 1000)));

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater lf = LayoutInflater.from(getContext());
            convertView = lf.inflate(R.layout.list_show_header, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.header_title);

        NowPerformance np = getItem(position);
        title.setText(np.venue_name);

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        NowPerformance np = getItem(position);
        int venue_id = np.venue_name.hashCode();
        return venue_id;
    }
}
