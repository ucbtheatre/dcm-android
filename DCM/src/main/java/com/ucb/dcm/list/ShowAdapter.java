package com.ucb.dcm.list;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ucb.dcm.R;
import com.ucb.dcm.data.Performance;
import com.ucb.dcm.data.Show;
import com.ucb.dcm.data.Venue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kurtguenther on 6/9/13.
 */
public class ShowAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    ArrayList<DataAdapter> items;
    Show mShow;

    public ShowAdapter(LayoutInflater inflater, Show show){
        mInflater = inflater;
        mShow = show;

        //Hook up the show data
        items = new ArrayList<DataAdapter>();
        items.add(new SummaryAdapter(mShow));

        //Showtimes
        items.add(new HeaderAdapter("Showtimes"));
        Cursor c = show.getPerformances();
        while(c.moveToNext()){
            Performance p = new Performance(c);
            Venue v = new Venue(c);
            items.add(new PerformanceAdapter(p,v));
        }

        //Cast
        items.add(new HeaderAdapter("Cast"));
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        //TODO ahoy hoy?
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        return items.get(i).getView(i, view, parent);
    }

    public interface DataAdapter {
        View getView(int i, View view, ViewGroup parent);
    }

    private class SummaryAdapter implements DataAdapter{
        Show mShow;

        public SummaryAdapter(Show show){
            this.mShow = show;
        }

        public View getView(int i, View view, ViewGroup parent) {
            View retVal = mInflater.inflate(R.layout.list_show_summary, parent, false);

            TextView title = (TextView) retVal.findViewById(R.id.show_title);
            title.setText(mShow.name);
            TextView city = (TextView) retVal.findViewById(R.id.show_city);
            city.setText(mShow.city);
            TextView promo = (TextView) retVal.findViewById(R.id.show_promo);
            promo.setText(mShow.promo);

            return retVal;
        }
    }

    private class HeaderAdapter implements DataAdapter{
        String headerText;

        public HeaderAdapter(String text){
            headerText = text;
        }

        public View getView(int i, View view, ViewGroup parent) {
            View retVal = mInflater.inflate(R.layout.list_show_header, parent, false);
            TextView title = (TextView) retVal.findViewById(R.id.header_title);
            title.setText(headerText);
            return retVal;
        }
    }

    private class PerformanceAdapter implements DataAdapter{
        Performance p;
        Venue v;

        public PerformanceAdapter(Performance performance, Venue venue){
            p = performance;
            v = venue;
        }

        public View getView(int i, View view, ViewGroup parent) {
            View retVal = mInflater.inflate(R.layout.list_show_performance, parent, false);
            TextView time = (TextView) retVal.findViewById(R.id.show_performance_time);

            Date date = new Date(((long)p.start_date) * 1000);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E hh:mm a");
            String display = simpleDateFormat.format(date);
            time.setText(display);

            TextView venue = (TextView) retVal.findViewById(R.id.show_performance_venue);
            venue.setText(v.short_name);
            return retVal;
        }
    }
}
