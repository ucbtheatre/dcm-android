package com.ucb.dcm;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.ucb.dcm.data.DataService;
import com.ucb.dcm.data.Show;
import com.ucb.dcm.data.Venue;
import com.ucb.dcm.list.ShowsListAdapter;
import com.ucb.dcm.list.VenueScheduleAdapter;
import com.ucb.dcm.util.ImageUtil;

import java.util.ArrayList;

/**
 * Created by kurtguenther on 6/9/13.
 */
public class DisplayVenueActivity extends SherlockListActivity {

    public static final String VENUE_KEY = "venue_key";

    private Venue mVenue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);


        //Hook up the data
        mVenue = (Venue) getIntent().getSerializableExtra(VENUE_KEY);

        setTitle(mVenue.name);

        View venueHeader = getVenueHeaderView();
        if(getListView().getHeaderViewsCount() == 0){
            getListView().addHeaderView(venueHeader, null, false);
        }

        Cursor c = mVenue.getSchedule();
        VenueScheduleAdapter mAdpt = new VenueScheduleAdapter(this, c);
        setListAdapter(mAdpt);
    }

    protected View getVenueHeaderView() {
        View retVal = this.getLayoutInflater().inflate(R.layout.list_venue_header, null, false);

        TextView address = (TextView) retVal.findViewById(R.id.venue_address);
        address.setText("Address: " + mVenue.address);

        ImageView venueImage = (ImageView) retVal.findViewById(R.id.venue_image);
        if(mVenue.image != null && !mVenue.image.equals("")){
            ImageUtil.setImageFromUrl(mVenue.image, venueImage);
            venueImage.setVisibility(View.VISIBLE);
        }
        else {
            venueImage.setVisibility(View.GONE);
        }

        return retVal;
    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        getSupportMenuInflater().inflate(R.menu.venue, menu);
        MenuItem map = menu.findItem(R.id.venue_map);
        map.setVisible(mVenue.gmaps != null && !mVenue.gmaps.equals(""));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.venue_map:
                String uri = mVenue.gmaps;
                Intent map = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(map);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //We do this because of the manual header we add (for the Venue details)
        int newPosition = position - 1;
        Cursor c = (Cursor) getListAdapter().getItem(newPosition);
        Show show = new Show(c);

        Intent displayShow = new Intent(this, ViewShowFragment.class);
        displayShow.putExtra(ViewShowFragment.SHOW_KEY, show.ID);
        startActivity(displayShow);
    }
}