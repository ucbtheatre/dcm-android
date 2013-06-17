package com.ucb.dcm;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
import com.ucb.dcm.data.*;
import com.ucb.dcm.list.NowListAdapter;
import com.ucb.dcm.list.VenueScheduleAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

/**
 * Created by kurtguenther on 6/5/13.
 */



public class NowActivity extends SherlockFragment {

    //Remember, months are 0 indexed for some reason.
    public static final Date MARATHON_START_DATE = new Date(2013 - 1900, 5, 28, 16, 30, 0);

    private Handler clock;
    private Runnable updateClockRunnable = new Runnable() {
        @Override
        public void run() {
            updateClock();
            clock.postDelayed(this, 1000);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_now, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        Date now = new Date();

        View countdown = getView().findViewById(R.id.countdown);
        View upcoming = getView().findViewById(R.id.now_list);


        if(now.before(MARATHON_START_DATE)) {
            countdown.setVisibility(View.VISIBLE);
            upcoming.setVisibility(View.GONE);

            updateClock();

            clock = new Handler();
            updateClockRunnable.run();

        }
        else {
            countdown.setVisibility(View.GONE);
            upcoming.setVisibility(View.VISIBLE);

            //Load up some data for the upcoming.
            ArrayList<NowPerformance> perfs = Performance.getUpcomingShows(now);
            NowListAdapter mAdpt = new NowListAdapter(getActivity(), R.layout.list_schedule, R.id.schedule_show_name, perfs);

            StickyListHeadersListView list = (StickyListHeadersListView) getView().findViewById(R.id.now_list);
            list.setAreHeadersSticky(false);
            list.setAdapter(mAdpt);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent displayShow = new Intent(getActivity(), ViewShowFragment.class);
                    NowPerformance np= (NowPerformance) adapterView.getAdapter().getItem(i);
                    displayShow.putExtra(ViewShowFragment.SHOW_KEY, np.show_id);
                    startActivity(displayShow);
                }
            });
        }
    }

    private void updateClock(){
        Date now = new Date();

        int secondsBetween = (int) ((MARATHON_START_DATE.getTime() - now.getTime()) / 1000);

        if(secondsBetween < 0){
            clock.removeCallbacks(updateClockRunnable);
            updateUI();
        }

        TextView days = (TextView) getView().findViewById(R.id.countdown_days);
        int daysVal = (secondsBetween / (60 * 60 * 24));
        days.setText(Integer.toString(daysVal));

        TextView hours = (TextView) getView().findViewById(R.id.countdown_hours);
        int hoursVal = (secondsBetween - daysVal * 60 * 60 * 24) / (60 * 60);
        hours.setText(Integer.toString(hoursVal));

        TextView minutes = (TextView) getView().findViewById(R.id.countdown_minutes);
        int minutesVal = (secondsBetween - daysVal * 60 * 60 * 24 - hoursVal * 60 * 60) / 60;
        minutes.setText(Integer.toString(minutesVal));

        TextView seconds = (TextView) getView().findViewById(R.id.countdown_seconds);
        int secondsVal = (secondsBetween - daysVal * 60 * 60 * 24 - hoursVal * 60 * 60 - minutesVal * 60);
        seconds.setText(Integer.toString(secondsVal));
    }

    @Override
    public void onPause() {
        //Stop the clock when we navigate away (otherwise it fires and gets a null pointer error)
        if(clock != null){
            clock.removeCallbacks(updateClockRunnable);
        }
        super.onPause();
    }

}